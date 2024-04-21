package com.sjxy.bbs.context;

import com.sjxy.bbs.entity.bo.WebContextBO;

public class WebContext {
    private static final ThreadLocal<WebContextBO> webContext = new ThreadLocal<>();

    public static void setContext(WebContextBO webContextBO){
        webContext.set(webContextBO);
    }

    public static WebContextBO getContext(){
        return webContext.get();
    }

    public static void cleanContext(){
        webContext.remove();
    }

    public static String getIp(){
        WebContextBO webContextBO = webContext.get();
        return webContextBO.getIp();
    }
}
