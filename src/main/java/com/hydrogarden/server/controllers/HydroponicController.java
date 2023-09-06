package com.hydrogarden.server.controllers;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.*;

@RestController
@RequestMapping("/hydroponic")
@RequiredArgsConstructor
public class HydroponicController {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("")
    public DeferredResult<String> test() {
        DeferredResult<String> result = new DeferredResult<>();

        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.debug("Done!");
        logger.debug(Thread.currentThread().getName());
        result.setErrorResult(new ResponseStatusException(HttpStatus.NOT_FOUND));

        return result;
    }
}
