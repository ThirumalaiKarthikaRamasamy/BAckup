package com.ge.seawolf.ingestion.ras.postgres;

import com.ge.seawolf.ingestion.domain.Semaphore;


public interface SemaphoreRepositoryCustom {

    Semaphore getSemaphore(String key, Integer msToLive);

    Semaphore dropSemaphore(String key, Long timestamp);

}