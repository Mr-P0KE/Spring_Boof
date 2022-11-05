package com.poke.Comon;

import com.alibaba.fastjson.JSON;
import com.poke.Util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
//需要配合@ServletComponentScan在启动类，一块使用
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j

public class LoginCheckFilter implements Filter{
    //路径匹配
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();

        log.info("本次请求为={}",url);
        String urls[] = new String[]
                {"/employee/login","/employee/logout","/backend/**","/front/**"};

        boolean b = check(urls,url); //true 不处理，false 进行处理
        if(b){
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee") != null){
            filterChain.doFilter(request,response);
            return;
        }
        log.info("没有用户登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }


    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
