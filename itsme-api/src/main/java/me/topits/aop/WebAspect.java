package me.topits.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.topits.annotations.Decrypted;
import me.topits.annotations.Encrypted;
import me.topits.annotations.NeedLogin;
import me.topits.configuration.SysSecretProperties;
import me.topits.enums.BaseStatusEnum;
import me.topits.exception.BaseException;
import me.topits.exception.InvalidTokenException;
import me.topits.filter.ThreadLocalContext;
import me.topits.model.BaseRequest;
import me.topits.model.BaseResponse;
import me.topits.service.IAccessTokenService;
import me.topits.service.model.AccessTokenCheckModel;
import me.topits.utils.RsaUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author QingKe
 * @date 2020-09-14 20:35
 **/
@Slf4j
@Aspect
@Component
public class WebAspect {

    @Resource
    IAccessTokenService accessTokenService;

    @Pointcut(value = "(@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)) " +
            "||@annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void pointcut() {
    }

    /**
     * <p>
     * aop执行顺序
     * Around
     * Before
     * *Method
     * Around
     * After
     * AfterReturning
     * </p>
     */
    @Around("pointcut()")
    public Object handleAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object response;
        BaseRequest<?> baseRequest = this.getBaseRequest(joinPoint.getArgs());

        // signature
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = ((MethodSignature) signature);
        Object target = joinPoint.getTarget();
        Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        boolean isNeedLoginCheck = method.getAnnotation(NeedLogin.class) != null;
        boolean isNeedDecrypted = method.getAnnotation(Decrypted.class) != null;
        boolean isNeedEncrypted = method.getAnnotation(Encrypted.class) != null;

        // need login
        if (isNeedLoginCheck) {
            String accessToken = ThreadLocalContext.getSysParams().getAccessToken();
            AccessTokenCheckModel checkModel = accessTokenService.checkAccessToken(accessToken);
            if (checkModel.isValid()) {
                if (baseRequest != null) {
                    JSONObject extra = new JSONObject();
                    extra.put("userId", checkModel.getUserId());
                    baseRequest.setExtraParams(extra);
                }
            } else {
                throw new InvalidTokenException(checkModel.getErrorMessage());
            }
        }

        // need decrypted
        if (isNeedDecrypted) {
            String data = ThreadLocalContext.getParams().getString("data");
            String param = RsaUtil.encrypt(SysSecretProperties.RSA_PRIVATE_KEY, data);
            if (param == null) {
                throw new BaseException(BaseStatusEnum.AES_DECRYPTED_INVALID);
            }
            ThreadLocalContext.PARAMS.set(JSONObject.parseObject(param));
        }

        if (baseRequest != null) {
            baseRequest.setRequest(ThreadLocalContext.getParams())
                    .setSysParams(ThreadLocalContext.getSysParams());
        }

        response = joinPoint.proceed();

        // need encrypted
        if (isNeedEncrypted) {
            if (response instanceof BaseResponse) {
                Object responseData = ((BaseResponse<?>) response).getData();
                response = BaseResponse.success(RsaUtil.decrypt(SysSecretProperties.RSA_PUBLIC_KEY,
                        JSONObject.toJSONString(responseData)));
            }
        }
        return response;
    }

    private BaseRequest<?> getBaseRequest(Object[] args) {
        BaseRequest<?> baseRequest = null;
        for (Object arg : args) {
            if (arg instanceof BaseRequest) {
                baseRequest = ((BaseRequest<?>) arg);
                break;
            }
        }
        return baseRequest;
    }

}
