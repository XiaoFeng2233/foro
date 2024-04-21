package com.sjxy.bbs;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.junit.Test;

public class Td {
    @Test
    public void test(){
        String js = "2024-02-04 14:45:56";
        DateTime parse = DateUtil.parse(js);
        long between = DateUtil.between(new DateTime(),parse, DateUnit.HOUR,false);
        System.out.println(between);
    }
}
