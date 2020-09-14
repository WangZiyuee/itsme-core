package me.topits.aop;

import lombok.extern.slf4j.Slf4j;
import me.topits.annotations.NeedLogin;
import me.topits.exception.InvalidTokenException;
import me.topits.filter.ThreadLocalContext;
import me.topits.model.BaseRequest;
import me.topits.service.IAccessTokenService;
import me.topits.service.model.AccessTokenCheckModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    private void webPointcut() {
    }

    @Before(value = "webPointcut()")
    public void handleBefore(JoinPoint joinPoint) {
        BaseRequest baseRequest = getBaseRequest(joinPoint.getArgs());
        if (baseRequest != null) {
            baseRequest.setRequest(ThreadLocalContext.getParams())
                    .setSysParams(ThreadLocalContext.getSysParams());
        }
    }

    @Before(value = "webPointcut()&&@annotation(needLogin)")
    public void handleBefore(JoinPoint joinPoint, NeedLogin needLogin) {
        BaseRequest baseRequest = getBaseRequest(joinPoint.getArgs());

        if (needLogin != null && needLogin.value()) {
            AccessTokenCheckModel checkModel;
            String accessToken = ThreadLocalContext.getSysParams().getAccessToken();
            try {
                checkModel = accessTokenService.checkAccessToken(accessToken);
            } catch (Exception e) {
                throw new InvalidTokenException();
            }

            if (checkModel.isValid() && baseRequest != null) {
                baseRequest.setUserId(checkModel.getUserId())
                        .setRequest(ThreadLocalContext.getParams())
                        .setSysParams(ThreadLocalContext.getSysParams());
            } else {
                throw new InvalidTokenException(checkModel.getErrorMessage());
            }
        }
    }

    private BaseRequest getBaseRequest(Object[] args) {
        BaseRequest baseRequest = null;
        for (Object arg : args) {
            if (arg instanceof BaseRequest) {
                baseRequest = ((BaseRequest) arg);
            }
        }
        return baseRequest;
    }

}
