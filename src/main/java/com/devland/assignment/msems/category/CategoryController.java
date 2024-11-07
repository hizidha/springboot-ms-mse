package com.devland.assignment.msems.category;

import com.devland.assignment.msems.category.model.Category;
import com.devland.assignment.msems.category.model.dto.CategoryRequestDTO;
import com.devland.assignment.msems.category.model.dto.CategoryResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<Page<CategoryResponseDTO>> getAll(
            @RequestParam(value = "name") Optional<String> optionalName,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString.toUpperCase()), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Category> pageCategorys = this.categoryService.findAll(optionalName, pageable);
        Page<CategoryResponseDTO> categoryResponseDTOs = pageCategorys.map(Category::convertToResponse);

        return ResponseEntity.ok(categoryResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getOne(
            @PathVariable("id") Long id
    ) {
        Category existingCategory = this.categoryService.findBy(id);
        CategoryResponseDTO categoryResponseDTO = existingCategory.convertToResponse();

        return ResponseEntity.ok(categoryResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> create(
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ) {
        Category newCategory = categoryRequestDTO.convertToEntity();
        Category savedCategory = this.categoryService.create(newCategory);
        CategoryResponseDTO categoryResponseDTO = savedCategory.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ) {
        Category updatedCategory = categoryRequestDTO.convertToEntity();
        updatedCategory.setId(id);

        Category savedCategory = this.categoryService.update(updatedCategory);
        CategoryResponseDTO categoryResponseDTO = savedCategory.convertToResponse();

        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id
    ) {
        this.categoryService.delete(id);

        return ResponseEntity.ok().build();
    }
}