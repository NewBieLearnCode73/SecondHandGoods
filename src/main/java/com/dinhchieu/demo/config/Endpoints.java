package com.dinhchieu.demo.config;

public class Endpoints {

    public static final String[] PUBLIC_POST_ENDPOINTS = {
        "/api/v1/login",
        "/api/v1/refresh-token",
            "/api/v1/payment/vn-pay"
    };

    public static final String[] PUBLIC_GET_ENDPOINTS ={
            "/api/v1/payment/vn-pay-callback",
            "/api/v1/products",
            "/api/v1/products/**",
            "/api/v1/products/user/**",
            "/api/v1/followers/**",
            "/api/v1/followees/**",
            "/api/v1/images/**",
            "/api/v1/reviews/**",
            "/api/v1/reviews/product/**",
    };


//    ENDPOINT FOR USER
    public static final String[] USER_GET_ENDPOINTS = {
            "/api/v1/wishList/**",
            "/api/v1/followers/**",
            "/api/v1/followees/**",
    };

}
