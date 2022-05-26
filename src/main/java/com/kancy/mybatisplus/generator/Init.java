package com.kancy.mybatisplus.generator;

import com.kancy.mybatisplus.generator.manager.DataStoreManager;
import com.kancy.mybatisplus.generator.manager.GlobalCacheManager;
import com.kancy.mybatisplus.generator.manager.NetResourceManager;

/**
 * <p>
 * Init
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/16 1:10
 **/

public class Init {
    public static void start(){
        // 网络资源初始化
        NetResourceManager.init();
        // 存储设备初始化
        DataStoreManager.init();
        // 全局缓存初始化
        GlobalCacheManager.init();
    }
}
