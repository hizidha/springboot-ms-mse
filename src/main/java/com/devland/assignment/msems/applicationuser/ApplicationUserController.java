package com.devland.assignment.msems.applicationuser;

import com.devland.assignment.msems.applicationuser.model.ApplicationUser;
import com.devland.assignment.msems.applicationuser.model.dto.ApplicationUserRequestDTO;
import com.devland.assignment.msems.applicationuser.model.dto.ApplicationUserResponseDTO;
import com.devland.assignment.msems.applicationuser.model.dto.ApplicationUserUpdateRequestDTO;
import com.devland.assignment.msems.fileupload.FileUploadService;
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
import org.springframework.web.multipart.MultipartFile;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ApplicationUserController {
    private final FileUploadService fileUploadService;
    private final ApplicationUserService applicationUserService;

    @GetMapping()
    public ResponseEntity<Page<ApplicationUserResponseDTO>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString.toUpperCase()), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<ApplicationUser> pageUsers = this.applicationUserService.findAll(name, username, pageable);
        Page<ApplicationUserResponseDTO> userResponseDTOs = pageUsers.map(ApplicationUser::convertToResponse);

        return ResponseEntity.ok(userResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUserResponseDTO> getOne(
            @PathVariable("id") Long id
    ) {
        ApplicationUser existingUser = this.applicationUserService.findBy(id);
        ApplicationUserResponseDTO userResponseDTO = existingUser.convertToResponse();

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<ApplicationUserResponseDTO> create(
            @RequestPart("fileImage") MultipartFile file,
            @RequestPart("applicationUserRequestDTO") @Valid ApplicationUserRequestDTO applicationUserRequestDTO
    ) {
        ApplicationUser newUser = applicationUserRequestDTO.convertToEntity();
        if (file != null && !file.isEmpty()) {
            String nameFile = this.fileUploadService.savedPhoto(file);
            newUser.setProfilePicturePath(nameFile);
        }

        ApplicationUser savedUser = this.applicationUserService.create(newUser);
        ApplicationUserResponseDTO userResponseDTO = savedUser.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationUserResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestPart("fileImage") MultipartFile file,
            @RequestPart("applicationUserUpdateRequestDTO") @Valid ApplicationUserUpdateRequestDTO applicationUserUpdateRequestDTO
    ) {
        ApplicationUser updatedUser = applicationUserUpdateRequestDTO.convertToEntity();
        updatedUser.setId(id);

        if (file != null && !file.isEmpty()) {
            String nameFile = this.fileUploadService.changedPhoto(updatedUser, file);
            updatedUser.setProfilePicturePath(nameFile);
        }

        ApplicationUser savedStudent = this.applicationUserService.update(updatedUser);
        ApplicationUserResponseDTO studentResponseDTO = savedStudent.convertToResponse();

        return ResponseEntity.ok().body(studentResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        this.applicationUserService.delete(id);

        return ResponseEntity.noContent().build();
    }
}