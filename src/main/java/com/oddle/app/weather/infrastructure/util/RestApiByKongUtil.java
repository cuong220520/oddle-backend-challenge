package com.oddle.app.weather.infrastructure.util;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.util
 */
@Slf4j
public class RestApiByKongUtil {

    private RestApiByKongUtil() {
    }

    /**
     * @param url
     * @param headers
     * @param object
     * @return
     */
    public static HttpResponse<String> postByBody(String url, Map<String, String> headers, String object) {
        return Unirest.post(url).headers(headers).body(object).asString();
    }

    /**
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static HttpResponse<String> get(String url, Map<String, String> headers, Map<String, Object> params) {
        return Unirest.get(url).queryString(params).headers(headers).asString();
    }

}
