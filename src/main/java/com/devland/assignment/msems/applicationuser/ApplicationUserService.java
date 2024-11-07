package com.devland.assignment.msems.applicationuser;

import com.devland.assignment.msems.applicationuser.exception.*;
import com.devland.assignment.msems.applicationuser.model.ApplicationUser;
import com.devland.assignment.msems.authentication.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) {
        ApplicationUser applicationUser = this.applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username Not Found."));

        return UserPrincipal.build(applicationUser);
    }

    public ApplicationUser findBy(Long userId) {
        return this.applicationUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with Id " + userId + " not found."));
    }

    public Page<ApplicationUser> findAll(String name, String username, Pageable pageable) {
        return this.applicationUserRepository.findAll(name, username, pageable);
    }

    public ApplicationUser create(ApplicationUser newUser) {
        checkAvailability(newUser);

        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        return this.applicationUserRepository.save(newUser);
    }

    public ApplicationUser update(ApplicationUser updatedUser) {
        ApplicationUser existingUser = this.findBy(updatedUser.getId());
        updatedUser.setId(existingUser.getId());

        if (updatedUser.getProfilePicturePath() == null) {
            updatedUser.setProfilePicturePath(existingUser.getProfilePicturePath());
        }

        if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(existingUser.getPassword());
            return this.applicationUserRepository.save(updatedUser);
        }

        updatedUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        return this.applicationUserRepository.save(updatedUser);
    }

    public void delete(Long id) {
        if (getCurrentLoggedInUser().getId().equals(id)) {
            throw new AdminDeletionNotAllowedException("Cannot delete the currently logged-in user.");
        }

        if (this.applicationUserRepository.count() <= 1) {
            throw new AdminDeletionNotAllowedException("Cannot delete the last remaining admin.");
        }

        this.applicationUserRepository.deleteById(this.findBy(id).getId());
    }

    private void checkAvailability(ApplicationUser updatedUser) {
        if (this.applicationUserRepository.findByUsername(updatedUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Username '" + updatedUser.getUsername() + "' Already Exist.");
        }

        if (this.applicationUserRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("Email '" + updatedUser.getEmail() + "' Already Exist.");
        }
    }

    private ApplicationUser getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return applicationUserRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}