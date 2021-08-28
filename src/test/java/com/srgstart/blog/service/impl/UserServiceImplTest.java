package com.srgstart.blog.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    public static void main(String[] args) {
        String b = "1,2,3,4";
        Long[] longArray = Convert.toLongArray(b);
        Arrays.stream(longArray).forEach(System.out::println);

       List<Long> idList = new ArrayList<>();
       idList.add(1L);
       idList.add(2L);
       idList.add(3L);
       idList.add(4L);

        String s = Convert.toStr(idList);
        String s1 = StrUtil.removeAll(s, '[', ']');
        System.out.println(s1);

    }

}