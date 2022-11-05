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
 * ����û��Ƿ��Ѿ���ɵ�¼
 */
@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        log.info("��������Ϊ={}",url);
        if(request.getSession().getAttribute("employee") != null){
            log.info("��̨�û��ѵ�¼��idΪ={}",request.getSession().getAttribute("employee"));
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            return true;
        }
        if(request.getSession().getAttribute("user") != null){
            log.info("ǰ���û��ѵ�¼��idΪ={}",request.getSession().getAttribute("user"));
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));
            return true;
        }
        log.info("û���û���¼");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }

}
