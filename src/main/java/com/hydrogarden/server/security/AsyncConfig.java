package com.hydrogarden.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    private final ThreadPoolTaskExecutor defaultSpringBootAsyncExecutor = new ThreadPoolTaskExecutor();


    @Override
    @Bean
    public Executor getAsyncExecutor() {
        return new DelegatingSecurityContextAsyncTaskExecutor(defaultSpringBootAsyncExecutor);
    }
}