package com.devland.assignment.msems.tag;

import com.devland.assignment.msems.event.model.dto.EventRequestDTO;
import com.devland.assignment.msems.tag.exception.TagAlreadyExistException;
import com.devland.assignment.msems.tag.exception.TagNotFoundException;
import com.devland.assignment.msems.tag.model.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Page<Tag> findAll(Optional<String> optionalName, Pageable pageable) {
        if (optionalName.isPresent()) {
            return this.tagRepository.findAllByNameContainsIgnoreCase(optionalName.get(), pageable);
        }
        return this.tagRepository.findAll(pageable);
    }

    public Tag findBy(Long id) {
        return this.tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("Tag with ID " + id + " not found"));
    }

    public Tag create(Tag newTag) {
        Optional<Tag> existingTag = this.tagRepository.findByName(newTag.getName());

        if (existingTag.isPresent()) {
            throw new TagAlreadyExistException("Tag: " + newTag.getName() + " already exist");
        }

        return this.tagRepository.save(newTag);
    }

    public Tag update(Tag updatedTag) {
        Tag existingTag = this.findBy(updatedTag.getId());
        updatedTag.setId(existingTag.getId());

        return this.tagRepository.save(updatedTag);
    }

    public void delete(Long id) {
        this.tagRepository.deleteById(this.findBy(id).getId());
    }

    public Set<Tag> getTags(EventRequestDTO eventRequestDTO) {
        if (eventRequestDTO.getTagsIds() != null && !eventRequestDTO.getTagsIds().isEmpty()) {
            return eventRequestDTO.getTagsIds().stream()
                    .map(this::findBy)
                    .collect(Collectors.toSet());
        }

        return Set.of();
    }
}