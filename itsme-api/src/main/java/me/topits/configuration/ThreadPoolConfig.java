package me.topits.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author QingKe
 * @date 2020-09-14 14:36
 **/
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    /**
     * 自定义线程池发送消息
     * 核心线程数为 2* 核数 +1  （io 吞吐型）
     * 最大线程数 为 100
     * 有界阻塞队列 10240
     * 拒绝策略为 主线程执行任务
     *
     * @return Executor
     */
    @Bean
    public Executor customThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 自定义线程最少数量
        executor.setCorePoolSize(2 * Runtime.getRuntime().availableProcessors() + 1);
        // 自定义线程最大数量
        executor.setMaxPoolSize(100);
        // 等待队列大小
        executor.setQueueCapacity(512);
        // 保持等待时间
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("CustomThreadPool");
        // RejectedExecutionHandler ： 线程池对拒绝任务的处理策略。在 ThreadPoolExecutor 里面定义了 4 种 handler 策略，分别是
        // 1. CallerRunsPolicy ：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
        // 2. AbortPolicy ：对拒绝任务抛弃处理，并且抛出异常。
        // 3. DiscardPolicy ：对拒绝任务直接无声抛弃，没有异常信息。
        // 4. DiscardOldestPolicy ：对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
