package com.anck.repositories;

import com.anck.entity.Link;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
    Optional<Link> findByOriginalValue(String originalValue);

    Optional<Link> findByShortValue(String shortValue);

    @Modifying
    @Query("update Link " +
            "set lastUsageDate = current_timestamp " +
            "where id = :id")
    int updateLastUsage(Long id);
}
