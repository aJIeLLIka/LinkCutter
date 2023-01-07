package com.anck.repositories;

import com.anck.entity.LinkInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<LinkInfo, Long> {
    Optional<LinkInfo> findByOriginalValue(String originalValue);

    Optional<LinkInfo> findByShortValue(String shortValue);

    @Modifying
    @Query("update LinkInfo " +
            "set lastUsageDate = current_timestamp " +
            "where id = :id")
    int updateLastUsage(Long id);
}
