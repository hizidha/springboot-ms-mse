package com.devland.assignment.msems.category;

import com.devland.assignment.msems.category.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Page<Category> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}