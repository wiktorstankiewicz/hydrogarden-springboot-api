package com.hydrogarden.server.controllers;


import com.hydrogarden.server.security.AsyncConfig;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.*;

@RestController
@RequestMapping("/hydroponic")
@AllArgsConstructor
public class HydroponicController {

    private Executor executor;


    @GetMapping("")
    public DeferredResult<String> test(){
        DeferredResult<String> result = new DeferredResult<>();

        executor.execute(() ->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("done");
            result.setResult("Done!!");
        });
        return result;
    }
}
