package com.ge.seawolf.ingestion.ras.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ge.seawolf.ingestion.domain.Semaphore;

@Repository
public interface SemaphoreRepository extends JpaRepository<Semaphore, Long>, SemaphoreRepositoryCustom {

	Semaphore findByKey(String key);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO semaphores (key, timestamp, ms_to_live) VALUES (?1, ?2, ?3)")
    void nativeInsert(String key, Long timestamp, Integer msToLive);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM semaphores WHERE key = ?1 AND timestamp = ?2")
    void nativeDelete(String key, Long timestamp);
    
}
