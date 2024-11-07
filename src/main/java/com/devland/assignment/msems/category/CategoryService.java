package com.devland.assignment.msems.category;

import com.devland.assignment.msems.category.exception.CategoryAlreadyExistException;
import com.devland.assignment.msems.category.exception.CategoryNotFoundException;
import com.devland.assignment.msems.category.model.Category;
import com.devland.assignment.msems.event.model.dto.EventRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Page<Category> findAll(Optional<String> optionalName, Pageable pageable) {
        if (optionalName.isPresent()) {
            return this.categoryRepository.findAllByNameContainsIgnoreCase(optionalName.get(), pageable);
        }
        return this.categoryRepository.findAll(pageable);
    }

    public Category findBy(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
    }

    public Category create(Category newCategory) {
        Optional<Category> existingCategory = this.categoryRepository.findByName(newCategory.getName());

        if (existingCategory.isPresent()) {
            throw new CategoryAlreadyExistException("Category: " + newCategory.getName() + " already exist");
        }

        return this.categoryRepository.save(newCategory);
    }

    public Category update(Category updatedCategory) {
        Category existingCategory = this.findBy(updatedCategory.getId());
        updatedCategory.setId(existingCategory.getId());

        return this.categoryRepository.save(updatedCategory);
    }

    public void delete(Long id) {
        this.categoryRepository.deleteById(this.findBy(id).getId());
    }

    public Set<Category> getCategories(EventRequestDTO eventRequestDTO) {
        if (eventRequestDTO.getCategoriesIds() != null && !eventRequestDTO.getCategoriesIds().isEmpty()) {
            return eventRequestDTO.getCategoriesIds().stream()
                    .map(this::findBy)
                    .collect(Collectors.toSet());
        }

        return Set.of();
    }
}