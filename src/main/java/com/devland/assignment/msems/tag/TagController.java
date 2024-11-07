package com.devland.assignment.msems.tag;

import com.devland.assignment.msems.tag.model.Tag;
import com.devland.assignment.msems.tag.model.dto.TagRequestDTO;
import com.devland.assignment.msems.tag.model.dto.TagResponseDTO;
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
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping()
    public ResponseEntity<Page<TagResponseDTO>> getAll(
            @RequestParam(value = "name") Optional<String> optionalName,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString.toUpperCase()), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Tag> pageTags = this.tagService.findAll(optionalName, pageable);
        Page<TagResponseDTO> tagResponseDTOs = pageTags.map(Tag::convertToResponse);

        return ResponseEntity.ok(tagResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDTO> getOne(
            @PathVariable("id") Long id
    ) {
        Tag existingTag = this.tagService.findBy(id);
        TagResponseDTO tagResponseDTO = existingTag.convertToResponse();

        return ResponseEntity.ok(tagResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ResponseEntity<TagResponseDTO> create(
            @RequestBody @Valid TagRequestDTO tagRequestDTO
    ) {
        Tag newTag = tagRequestDTO.convertToEntity();
        Tag savedTag = this.tagService.create(newTag);
        TagResponseDTO tagResponseDTO = savedTag.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(tagResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid TagRequestDTO tagRequestDTO
    ) {
        Tag updatedTag = tagRequestDTO.convertToEntity();
        updatedTag.setId(id);

        Tag savedTag = this.tagService.update(updatedTag);
        TagResponseDTO tagResponseDTO = savedTag.convertToResponse();

        return ResponseEntity.ok().body(tagResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id
    ) {
        this.tagService.delete(id);

        return ResponseEntity.ok().build();
    }
}