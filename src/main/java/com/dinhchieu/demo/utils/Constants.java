package com.dinhchieu.demo.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final String BASE_URL = "http://localhost:8080";
    public static final long VALIDITY = TimeUnit.DAYS.toMillis(7);
    public static final long REFRESH = TimeUnit.DAYS.toMillis(30);
    public static final String HEX_KEY = "413D111FBFB399AA704EC2F2B9E976CEBE57B295EFAF7DD868F696DE772935537EB0FA9B67B5D2D151CF465AD36942566AD55CBCE100EED2BFA25A8D03EE4263";
    public static final String ISS = "SecondHandGoods";
    public static final String FRONT_END_URL = "http://localhost:3000";
    public static final String EMAIL = "chieuhdf123@gmail.com";
}
