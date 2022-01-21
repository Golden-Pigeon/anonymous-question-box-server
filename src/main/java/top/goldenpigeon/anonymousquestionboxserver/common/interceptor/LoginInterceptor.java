package top.goldenpigeon.anonymousquestionboxserver.common.interceptor;

import top.goldenpigeon.anonymousquestionboxserver.service.helper.RedisHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    RedisHelper helper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        String session = request.getHeader("session-key");
//        System.out.println(session);
//        if (StringUtils.isNotEmpty(session)) {
//            return helper.hasKey(session);
//        }
//        return false;
        return true;
    }
}
