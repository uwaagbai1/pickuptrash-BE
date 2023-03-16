package dev.waco.pickuptrashBE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.HashSet;

import dev.waco.pickuptrashBE.security.*;
import dev.waco.pickuptrashBE.services.UserService;
import dev.waco.pickuptrashBE.forms.LoginForm;
import dev.waco.pickuptrashBE.forms.SignupRequest;
import dev.waco.pickuptrashBE.models.User;
import dev.waco.pickuptrashBE.models.User.UserRole;
import dev.waco.pickuptrashBE.repositories.UserRepository;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        // Creating user's account
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.USER);

        User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword(), roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.createUser(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@CurrentUser UserPrincipal userPrincipal) {
        userService.deleteUser(userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> logoutUser(@CurrentUser UserPrincipal currentUser) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("User logged out successfully!");
    }
}
