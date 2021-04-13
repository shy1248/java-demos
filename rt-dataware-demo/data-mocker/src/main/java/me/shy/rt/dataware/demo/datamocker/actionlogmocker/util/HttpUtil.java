/**
 * @Date        : 2021-04-11 16:19:56
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Http client.
 */
package me.shy.rt.dataware.demo.datamocker.actionlogmocker.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import me.shy.rt.dataware.demo.datamocker.actionlogmocker.config.AppConfig;

@Slf4j
public class HttpUtil {
    private static OkHttpClient CLIENT;

    private HttpUtil() {
    }

    public static OkHttpClient getInstance() {
        if (null == CLIENT) {
            synchronized (HttpUtil.class) {
                if (null == CLIENT) {
                    CLIENT = new OkHttpClient();
                }
            }
        }
        return CLIENT;
    }

    public static boolean get(String param) {
        String url = null;
        if (null == param || "".equals(param.trim())) {
            url = AppConfig.MOCKER_URL;
        } else {
            url = AppConfig.MOCKER_URL + "?" + param;
        }
        try {
            String encodedUrl = URLEncoder.encode(url, "utf-8");
            Request get = new Request.Builder().url(encodedUrl).get().build();
            long start = System.currentTimeMillis();
            Response response = HttpUtil.getInstance().newCall(get).execute();
            long cast = System.currentTimeMillis() - start;
            log.info("GET[{}] - Response: {}, Cast: {}ms.", url, response.body().string(), cast);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean post(String param, String body) {
        String url = null;
        if (null == param || "".equals(param.trim())) {
            url = AppConfig.MOCKER_URL;
        } else {
            url = AppConfig.MOCKER_URL + "?" + param;
        }
        try {
            RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json;charset=utf-8"));
            String encodedUrl = URLEncoder.encode(url, "utf-8");
            Request post = new Request.Builder().url(encodedUrl).post(requestBody).build();
            long start = System.currentTimeMillis();
            Response response = HttpUtil.getInstance().newCall(post).execute();
            long cast = System.currentTimeMillis() - start;
            log.info("POST[{}] - Response: {}, Cast: {}ms", url, response.body().string(), cast);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
