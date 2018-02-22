package com.ge.seawolf.ingestion.managers.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ge.seawolf.ingestion.domain.Semaphore;
import com.ge.seawolf.ingestion.exceptions.SemaphoreException;
import com.ge.seawolf.ingestion.managers.ISemaphoreManager;
import com.ge.seawolf.ingestion.ras.postgres.SemaphoreRepository;

@Service
public class SemaphoreManager implements ISemaphoreManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SemaphoreManager.class);

    @Value("${wait.minimum.ms}")
    private int waitMinimumMs;

    @Value("${wait.range.ms}")
    private int waitRangeMs;

    private static final Random RANDOM = new Random();

    private SemaphoreRepository semaphoreRepository;

    @Autowired
    public SemaphoreManager(SemaphoreRepository semaphoreRepository) {
        this.semaphoreRepository = semaphoreRepository;
    }
    
    // Returns a semaphore if it can be allocated within the specified milliseconds to wait, otherwise returns null
    @Override
    public Semaphore getSemaphore(String key, Integer msToLive, Integer msToWait) {

        Semaphore semaphore;
        long now = Instant.now().toEpochMilli();
        long cutOff = now + msToWait;
        long msToSleep;

        while (now <= cutOff) {
            try {
                semaphore = semaphoreRepository.getSemaphore(key, msToLive);
                return semaphore;
            } catch (DataAccessException ex) {
            	LOGGER.debug(ex.getMessage());
            }

            now = Instant.now().toEpochMilli();
            if (now >= cutOff) {
                return null;
            }

            msToSleep = (long)(waitMinimumMs + RANDOM.nextInt(waitRangeMs));
            if (now + msToSleep > cutOff) {
                msToSleep = cutOff - now;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(msToSleep);
            } catch (InterruptedException ex) {
            	LOGGER.debug(ex.getMessage());
            }
        }
        throw new SemaphoreException ("Something went wrong while getting the semaphore for " + key);
    }

    // Returns the dropped semaphore if it was present, otherwise returns null
    @Override
    public Semaphore dropSemaphore(String key, Long timestamp) {

        Semaphore droppedSemaphore;
        int exceptionCount = 0;

        while (exceptionCount < 5) {
            try {
                droppedSemaphore = semaphoreRepository.dropSemaphore(key, timestamp);
                return droppedSemaphore;

            }  catch (DataAccessException ex) {
            	LOGGER.debug(ex.getMessage());
                exceptionCount++;
            }catch (Exception ex) {
            	LOGGER.debug(ex.getMessage());
                return null;

            }
        }
        return null;
    }

}
