package com.devland.assignment.msems.tag;

import com.devland.assignment.msems.tag.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findAllByNameContainsIgnoreCase(String s, Pageable pageable);

    Optional<Tag> findByName(String name);
}