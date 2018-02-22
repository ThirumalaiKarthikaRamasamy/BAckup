package com.ge.seawolf.ingestion.ras.postgres.impl;

import java.time.Instant;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ge.seawolf.ingestion.domain.Semaphore;
import com.ge.seawolf.ingestion.exceptions.SemaphoreException;
import com.ge.seawolf.ingestion.ras.postgres.SemaphoreRepository;
import com.ge.seawolf.ingestion.ras.postgres.SemaphoreRepositoryCustom;

public class SemaphoreRepositoryImpl implements SemaphoreRepositoryCustom{
	
    @Autowired
    private SemaphoreRepository semaphoreRepository;

    /*
     * THE @TRANSACTIONAL ANNOTATIONS ARE HERE BECAUSE JPA WILL THROW AN ERROR IF @MODIFYING METHODS ARE NOT RUN
     * AS PART OF AN @TRANSACTIONAL METHOD.  DO NOT MOVE THE @TRANSACTIONAL ANNOTATIONS FROM THESE METHODS
     * TO THE @MODIFYING REPO METHODS.  DOING SO PRODUCES AN INFINITE LOOP!
     */
    @Override
    @Transactional
    public Semaphore getSemaphore(String key, Integer msToLive) {

        long now = Instant.now().toEpochMilli();
        Semaphore semaphore = semaphoreRepository.findByKey(key);

        if (semaphore == null) {
            semaphoreRepository.nativeInsert(key, now, msToLive);
            return semaphoreRepository.findByKey(key);
        }

        if (semaphore.getTimestamp() + semaphore.getMsToLive() < now) {
            semaphoreRepository.nativeDelete(semaphore.getKey(), semaphore.getTimestamp());
            semaphoreRepository.nativeInsert(key, now, msToLive);
            return semaphoreRepository.findByKey(key);
        }

        throw new SemaphoreException("The semaphore could not be allocated");
    }
    
    @Override
    @Transactional
    public Semaphore dropSemaphore(String key, Long timestamp) {

        Semaphore semaphore = semaphoreRepository.findByKey(key);

        if (semaphore != null && semaphore.getTimestamp().equals(timestamp)) {
            semaphoreRepository.nativeDelete(semaphore.getKey(), semaphore.getTimestamp());
            return semaphore;
        }

        throw new SemaphoreException("The specified semaphore could not be found");
    }

}
