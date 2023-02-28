package com.lijiayi.springbootpromedemo.util;

import com.lijiayi.springbootpromedemo.entity.PromResponceInfo;
import org.springframework.web.client.RestTemplate;


public class PromInfoUtils {

    private static final String LIJIAYI_GET_LAST_TIME = "http://192.168.135.74:8082/api/v1/query?query=lijiayi_get_last_time";
    private static final String LIJIAYI_ALL_HTTP_REQUEST_COUNTER_TOTAL_BEFORE = "http://192.168.135.74:8082/api/v1/query?query=increase(lijiayi_all_http_request_counter_total[4h] offset 43h)";
    private static final String LIJIAYI_ALL_HTTP_REQUEST_COUNTER_TOTAL_NOW = "http://192.168.135.74:8082/api/v1/query?query=increase(lijiayi_all_http_request_counter_total[4h])";

    public static PromResponceInfo lijiayiGetLastTime() {
        RestTemplate template = new RestTemplate();
        String url = LIJIAYI_GET_LAST_TIME;

        PromResponceInfo promResponceInfo = template.getForObject(url, PromResponceInfo.class);
        return promResponceInfo;
    }

    public static PromResponceInfo lijiayiAllHttpRequestCounterTotalBefore() {
        RestTemplate template = new RestTemplate();
        String url = LIJIAYI_ALL_HTTP_REQUEST_COUNTER_TOTAL_BEFORE;

        PromResponceInfo promResponceInfo = template.getForObject(url, PromResponceInfo.class);
        return promResponceInfo;
    }

    public static PromResponceInfo lijiayiAllHttpRequestCounterTotalNow() {
        RestTemplate template = new RestTemplate();
        String url = LIJIAYI_ALL_HTTP_REQUEST_COUNTER_TOTAL_NOW;

        PromResponceInfo promResponceInfo = template.getForObject(url, PromResponceInfo.class);
        return promResponceInfo;
    }



}
