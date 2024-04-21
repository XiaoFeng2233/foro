package com.sjxy.bbs.filter;

import com.sjxy.bbs.context.WebContext;
import com.sjxy.bbs.entity.bo.WebContextBO;
import com.sjxy.bbs.util.WebUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class WebContextFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        WebContext.cleanContext();
        String ip = WebUtil.getIp(request);
        WebContextBO webContextBO = new WebContextBO();
        webContextBO.setIp(ip);
        WebContext.setContext(webContextBO);
        filterChain.doFilter(request, response);
    }
}
