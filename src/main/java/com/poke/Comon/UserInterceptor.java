package com.poke.Comon;

import com.alibaba.fastjson.JSON;
import com.poke.Util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 检查用户是否已经完成登录
 */
@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        log.info("本次请求为={}",url);
        if(request.getSession().getAttribute("employee") != null){
            log.info("后台用户已登录，id为={}",request.getSession().getAttribute("employee"));
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            return true;
        }
        if(request.getSession().getAttribute("user") != null){
            log.info("前端用户已登录，id为={}",request.getSession().getAttribute("user"));
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));
            return true;
        }
        log.info("没有用户登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }

}
