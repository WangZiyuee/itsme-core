package me.topits.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.topits.configuration.SysSecretProperties;
import me.topits.enums.BaseResponseStatusEnum;
import me.topits.model.SysConstants;
import me.topits.service.IAccessTokenService;
import me.topits.service.IRedisCache;
import me.topits.service.model.AccessTokenCheckModel;
import me.topits.service.model.AccessTokenModel;
import me.topits.utils.AesUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * @author QingKe
 * @date 2020-09-14 17:21
 **/
@Slf4j
@Service
public class AccessTokenServiceImpl implements IAccessTokenService {

    private static final String USER_LOGIN_TOKEN_FORMAT = "user:login:token:%s";

    final
    IRedisCache redisCache;

    public AccessTokenServiceImpl(IRedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public String createAccessToken(Long userId, LocalDateTime timeout) {
        Assert.state(userId != null && timeout != null,
                "Invalid Access Token Args");
        Assert.state(timeout.compareTo(LocalDateTime.now()) > 0,
                "Invalid Access Token Timeout");

        AccessTokenModel cacheAccessToken = new AccessTokenModel()
                .setUserId(userId)
                .setTimestamp(System.currentTimeMillis());
        redisCache.setString(String.format(USER_LOGIN_TOKEN_FORMAT, userId),
                cacheAccessToken.getTokenInfo(), timeout);

        cacheAccessToken.setMd5(DigestUtils.md5Hex(cacheAccessToken.getTokenInfo() + SysConstants.BASE_MD5_SALT));
        return AesUtil.encrypt(JSONObject.toJSONString(cacheAccessToken), SysSecretProperties.BASE_REQUEST_SECRET);
    }

    @Override
    public AccessTokenCheckModel checkAccessToken(String accessTokenStr) {
        AccessTokenCheckModel check = new AccessTokenCheckModel();
        if (Strings.isBlank(accessTokenStr)) {
            return check.setErrorMessage(BaseResponseStatusEnum.ACCESS_TOKEN_INVALID.getMessage());
        }
        String decryptData = AesUtil.decrypt(accessTokenStr, SysSecretProperties.BASE_REQUEST_SECRET);

        AccessTokenModel accessToken = JSONObject.parseObject(decryptData, AccessTokenModel.class);
        boolean md5Check = false;
        String cacheAccessToken = "";
        if (accessToken == null
                || Strings.isBlank(accessToken.getMd5())
                || accessToken.getUserId() == null
                || accessToken.getTimestamp() == null) {
            return check.setErrorMessage(BaseResponseStatusEnum.ACCESS_TOKEN_INVALID.getMessage());

        } else {
            md5Check = accessToken.getMd5().equals(DigestUtils.md5Hex(accessToken.getTokenInfo() + SysConstants.BASE_MD5_SALT));
            if (!md5Check) {
                logInfo(accessTokenStr, decryptData, md5Check, cacheAccessToken);
                return check.setErrorMessage("Check Access Token Failure");
            }

            cacheAccessToken = redisCache.getString(String.format(USER_LOGIN_TOKEN_FORMAT, accessToken.getUserId()));
            if (Strings.isBlank(cacheAccessToken)) {
                logInfo(accessTokenStr, decryptData, md5Check, cacheAccessToken);
                return check.setErrorMessage("Access Token Not Exist Or Has Expired");
            }

            if (!cacheAccessToken.equals(accessToken.getTokenInfo())) {
                logInfo(accessTokenStr, decryptData, md5Check, cacheAccessToken);
                return check.setErrorMessage("Unknown Remote Login");
            }

            check.setValid(true)
                    .setUserId(accessToken.getUserId());
        }

        logInfo(accessTokenStr, decryptData, md5Check, cacheAccessToken);

        return check;
    }

    private void logInfo(String accessTokenStr, String decryptData, boolean md5Check, String cacheAccessToken){
        log.info("> LoginCheck | accessToken: {} | decryptData: {} | md5Check: {} | cacheAccessToken: {}",
                accessTokenStr,
                decryptData,
                md5Check,
                cacheAccessToken);
    }

    @Override
    public void removeAccessToken(Long userId) {
        String key = String.format(USER_LOGIN_TOKEN_FORMAT, userId);
        if (redisCache.hasKey(key)) {
            redisCache.delete(key);
        }
    }
}
