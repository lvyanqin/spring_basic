package com.lynn.mybaits.v2.interceptor;

import com.lynn.mybaits.v2.plugin.Interceptor;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class MyPlugin implements Interceptor {

    @Override
    public void interceptor() {
        System.out.println("================== 执行自定义插件 ==================");
    }
}
