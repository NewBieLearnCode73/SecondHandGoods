package com.dinhchieu.demo.config;

public class Endpoints {

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/v1/login",
            "/api/v1/users/register",
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
    };

    //////////////////////////////////////
    //    CLASSIFY FOR USER ENDPOINTS   //
    //////////////////////////////////////
    public static final String[] USER_GET_ENDPOINTS ={
            "/api/v1/users/**",
            "/api/v1/users/username/**",
            "/api/v1/users/email/**",
            "/api/v1/users/wishList/**",
    };

    public static final String[] USER_POST_ENDPOINTS ={
            "/api/v1/follow",
            "/api/v1/images",
            "/api/v1/products",
            "/api/v1/reviews",
            "/api/v1/wishList",
    };

    public static final String[] USER_DELETE_ENDPOINTS ={
            "/api/v1/follow",
            "/api/v1/images/**",
            "/api/v1/reviews/**",
            "/api/v1/wishList/**",
    };

    public static final String[] USER_PATCH_ENDPOINTS ={
            "/api/v1/images/**",
            "/api/v1/users/updateProfile/**",
            "/api/v1/users/updatePassword/**",
            "/api/v1/products/**",
            "/api/v1/reviews/**",
    };




    ////////////////////////////////////////
    //    CLASSIFY FOR STAFF ENDPOINTS   //
    //////////////////////////////////////
    public static final String[] STAFF_GET_ENDPOINTS = {
            "/api/v1/users",
            "/api/v1/users/**",
            "/api/v1/users/username/**",
            "/api/v1/users/email/**",
    };

    public static final String[] STAFF_POST_ENDPOINTS ={

    };


    public static final String[] STAFF_DELETE_ENDPOINTS ={
            "/api/v1/images/**",
            "/api/v1/products/**",
            "/api/v1/reviews/**",
    };

    public static final String[] STAFF_PATCH_ENDPOINTS ={
            "/api/v1/products/state/**",
            "/api/v1/users/updateProfile/**",
            "/api/v1/users/updatePassword/**"
    };




    ////////////////////////////////////////
    //    CLASSIFY FOR ADMIN ENDPOINTS   //
    //////////////////////////////////////
    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/api/v1/users",
            "/api/v1/users/**",
            "/api/v1/users/username/**",
            "/api/v1/users/email/**",
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {
    };

    public static final String[] ADMIN_DELETE_ENDPOINTS ={
            "/api/v1/images/**",
            "/api/v1/products/**",
            "/api/v1/reviews/**",
    };

    public static final String[] ADMIN_PATCH_ENDPOINTS ={
            "/api/v1/images/**",
            "/api/v1/reviews/**",
            "/api/v1/products/**",
            "/api/v1/products/state/**",
            "/api/v1/users/updateProfile/**",
            "/api/v1/users/updatePassword/**",
            "/api/v1/users/updateAccountState/**",
    };


}
