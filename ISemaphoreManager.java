package com.ge.seawolf.ingestion.managers;

import com.ge.seawolf.ingestion.domain.Semaphore;

public interface ISemaphoreManager {

    Semaphore getSemaphore(String key, Integer msToLive, Integer msToWait);

    Semaphore dropSemaphore(String key, Long timestamp);

}
