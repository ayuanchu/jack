package com.kancy.mybatisplus.generator.manager;

import com.kancy.mybatisplus.generator.log.Log;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * NetResourceManager
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/29 11:16
 **/

public class NetResourceManager {

    private static final String IMAGES_URL_FORMAT = "https://gitee.com/kancy666/mybatisplus-plugin/raw/master/images/%s";

    private static final byte[] EMPTY_BYTES = new byte[0];

    private static final Map<String, String> urlMap = new HashMap<>();

    private static final Map<String, byte[]> resourceBytesMap = new HashMap<>();

    static {
        urlMap.put("image_wx_me",String.format(IMAGES_URL_FORMAT, "wx_me.png"));
        urlMap.put("image_we_chat",String.format(IMAGES_URL_FORMAT, "wechat.png"));
        urlMap.put("image_qq",String.format(IMAGES_URL_FORMAT, "qq.png"));
        urlMap.put("image_qq_me",String.format(IMAGES_URL_FORMAT, "qq_me.png"));
        urlMap.put("image_pay",String.format(IMAGES_URL_FORMAT, "pay.png"));
    }

    public static byte[] getImage_wx_me() {
        return resourceBytesMap.getOrDefault("image_wx_me", EMPTY_BYTES);
    }

    public static byte[] getImage_we_chat() {
        return resourceBytesMap.getOrDefault("image_we_chat", EMPTY_BYTES);
    }

    public static byte[] getImage_qq() {
        return resourceBytesMap.getOrDefault("image_qq", EMPTY_BYTES);
    }

    public static byte[] getImage_qq_me() {
        return resourceBytesMap.getOrDefault("image_qq_me", EMPTY_BYTES);
    }

    public static byte[] getImage_pay() {
        return resourceBytesMap.getOrDefault("image_pay", EMPTY_BYTES);
    }

    public static void init(){
        // 开启线程去获取网络资源数据
        new Thread(() -> {
            Set<Map.Entry<String, String>> entries = urlMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                try {
                    resourceBytesMap.put(entry.getKey(), getNetResourceBytes(entry.getValue()));
                    Log.debug("成功获取网络数据：%s -> %s", entry.getKey(), entry.getValue());
                } catch (Exception e) {
                    Log.error("无法获取网络数据：%s -> %s", entry.getKey(), entry.getValue());
                }
            }
        }).start();
    }

    private static byte[] getNetResourceBytes(String url){
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            URL urlImg = new URL(url);
            httpURLConnection =(HttpURLConnection)urlImg.openConnection();
            httpURLConnection.addRequestProperty("User-Agent","Mozilla / 4.76");
            inputStream = httpURLConnection.getInputStream();
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(httpURLConnection);
            IOUtils.closeQuietly(inputStream);
        }
        return new byte[0];
    }
}
