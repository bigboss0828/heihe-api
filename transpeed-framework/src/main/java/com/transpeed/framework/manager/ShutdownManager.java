package com.transpeed.framework.manager;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author hanshuai
 * @title: ShutdownManager
 * @description: 确保应用退出时能关闭后台线程
 * @date 2023/6/1 10:25
 */
@Component
public class ShutdownManager {

    @PreDestroy
    public void destroy()
    {
        shutdownAsyncManager();
    }

    /**
     * 停止异步执行任务
     */
    private void shutdownAsyncManager() {
        try {
            AsyncManager.me().shutdown();
        } catch (Exception e) {
        }
    }

}
