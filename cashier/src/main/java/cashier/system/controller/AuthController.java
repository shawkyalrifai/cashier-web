package cashier.system.controller;

import cashier.system.Auth.AuthRequest;
import cashier.system.Auth.AuthResponse;
import cashier.system.Auth.MyUserDetailsService;
import cashier.system.entity.User;
import cashier.system.enums.Role;
import cashier.system.jwt.JwtUtil;
import cashier.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username, request.password));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username);
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        // Set the role based on the user's request or set a default role (e.g., CASHIER)
        if (user.getRole() == null) {
            user.setRole(Role.CASHIER); // Default to CASHIER if no role is specified
        }

        return ResponseEntity.ok(userRepository.save(user));
    }
}
