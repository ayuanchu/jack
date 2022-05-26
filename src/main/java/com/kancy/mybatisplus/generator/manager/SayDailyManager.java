package com.kancy.mybatisplus.generator.manager;

import com.kancy.mybatisplus.generator.config.Settings;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.utils.AlertUtils;
import org.apache.commons.io.IOUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * SayDailyManager
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/18 13:21
 **/

public class SayDailyManager {
    private static final List<String> saying = new ArrayList<>();
    private static final AtomicBoolean Initialized = new AtomicBoolean(false);

    private static double probability = 0.1;

    static {
        probability = Double.parseDouble(System.getProperty("say", String.valueOf(probability)));
    }

    private static void init() {
        try {
            if (!Initialized.getAndSet(true)){
                saying.addAll(IOUtils.readLines(DataStoreManager.class.getClassLoader().getResourceAsStream("one-sentence-per-day.txt"), Settings.getDefaultCharset()));
            }
        } catch (IOException e) {
            Log.error("SayDailyManager init fail : %s", e.getMessage());
        }
    }

    public static void say(){
        say(null);
    }

    public static void say(Component component){
        if (saying.isEmpty()){
            init();
        }

        if (ThreadLocalRandom.current().nextDouble() >= ( 1 - probability)){
            AlertUtils.msg(component, saying.get(ThreadLocalRandom.current().nextInt(0, saying.size())));
        }
    }
}
