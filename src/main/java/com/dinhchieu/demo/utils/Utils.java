package com.dinhchieu.demo.utils;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static String extractParam(URL url, String param){
        String query = url.getQuery();
        String [] params = query.split("&");
        List<String> paramList = Arrays.asList(params);

        for (String p : paramList){
            if (p.contains(param)){
                return p.split("=")[1];
            }
        }

        return null;
    }
}
