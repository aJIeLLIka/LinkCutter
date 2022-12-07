package com.anck.repositories;

import com.anck.models.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<Link, Long> {
    Optional<Link> findByOriginalValue(String originalValue);
    Optional<Link> findByShortValue(String shortValue);
}
