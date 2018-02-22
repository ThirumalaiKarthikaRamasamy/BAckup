package com.ge.seawolf.ingestion.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ge.seawolf.ingestion.domain.Semaphore;
import com.ge.seawolf.ingestion.managers.ISemaphoreManager;


@RestController
@RequestMapping("/service/v1/semaphores")
public class SemaphoreControllerV1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(SemaphoreControllerV1.class);

    private ISemaphoreManager semaphoreManager;

    @Autowired
    public SemaphoreControllerV1(ISemaphoreManager semaphoreManager) {
        this.semaphoreManager = semaphoreManager;
    }

    // Returns a semaphore if it can be allocated within the specified milliseconds to wait, otherwise returns null
    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public Semaphore getSemaphore(@PathVariable String key,
                                  @RequestParam("msToLive") Integer msToLive,
                                  @RequestParam("msToWait") Integer msToWait) {

    	LOGGER.info("getSemaphore key: {}, msToLive: {}, msToWait: {}", key, msToLive, msToWait);
        Semaphore semaphore = semaphoreManager.getSemaphore(key, msToLive, msToWait);

        if (semaphore != null) {
        	LOGGER.info("Returned semaphore with timestamp: {}", semaphore.getTimestamp());
        } else {
        	LOGGER.info("Could not allocate semaphore");
        }
        return semaphore;
    }

    // Returns the dropped semaphore if it was present, otherwise returns null
    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    public Semaphore dropSemaphore(@PathVariable String key,
                                   @RequestParam("timestamp") Long timestamp) {

    	LOGGER.info("dropSemaphore key: {}, timestamp: {}", key, timestamp);
        Semaphore semaphore = semaphoreManager.dropSemaphore(key, timestamp);

        if (semaphore != null) {
        	LOGGER.info("Semaphore was dropped");
        } else {
        	LOGGER.info("Semaphore was not found");
        }
        return semaphore;
    }



}
