package io.chou401.framework.interceptor;

import io.chou401.framework.annotation.IgnoreLogin;
import io.chou401.framework.annotation.Login;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * {@code @author}  chou401
 * {@code @date} 2023/12/3
 **/
public abstract class BaseMethodInterceptor implements HandlerInterceptor {

    /**
     * 只处理方法的控制器
     *
     * @param request
     * @param response
     * @param handlerMethod
     * @return
     * @throws Exception
     */
    protected abstract boolean preHandleMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            return preHandleMethod(request, response, handlerMethod);
        }
        return true;
    }

    /**
     * 获取方法上和类上是否有@Login注解，如果没有，则放行，否则，则校验
     *
     * @param handlerMethod
     * @return
     */
    protected boolean isExistLoginAnnotation(HandlerMethod handlerMethod) {
        // 从方法上获取登录注解
        Login login = handlerMethod.getMethodAnnotation(Login.class);
        if (login != null) {
            return true;
        }
        // 从类上获取登录注解
        login = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Login.class);
        if (login != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取方法上和类上是否有@IgnoreLogin注解，如果有，则放行，否则，则校验
     *
     * @param handlerMethod
     * @return
     */
    protected boolean isExistIgnoreLoginAnnotation(HandlerMethod handlerMethod) {
        IgnoreLogin ignoreLogin = handlerMethod.getMethodAnnotation(IgnoreLogin.class);
        if (ignoreLogin != null) {
            return true;
        }
        ignoreLogin = handlerMethod.getMethod().getDeclaringClass().getAnnotation(IgnoreLogin.class);
        if (ignoreLogin != null) {
            return true;
        }
        return false;
    }


}
