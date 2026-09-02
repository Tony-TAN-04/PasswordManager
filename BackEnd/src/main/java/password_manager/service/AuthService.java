package password_manager.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HexFormat;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import password_manager.dto.LoginRequest;
import password_manager.dto.LoginResponse;
import password_manager.model.User;
import password_manager.model.UserSession;
import password_manager.repository.UserRepository;
import password_manager.repository.UserSessionRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final PasswordEncoder passwordEncoder;

    private final SecureRandom secureRandom = new SecureRandom();

    public AuthService(
            UserRepository userRepository,
            UserSessionRepository userSessionRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password"
                ));

        // L'utilisateur possède un mot de passe
        if (user.getPassword() != null) {

            if (request.password() == null ||
                    !passwordEncoder.matches(
                            request.password(),
                            user.getPassword()
                    )) {

                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password"
                );
            }
        }

        String token = generateToken();

        UserSession session = new UserSession();

        session.setUser(user);
        session.setToken(token);
        session.setExpiresAt(
                LocalDateTime.now().plusDays(30)
        );

        userSessionRepository.save(session);

        return new LoginResponse(
                token,
                user.getId(),
                user.getUsername()
        );
    }

    private String generateToken() {

        byte[] bytes = new byte[32];

        secureRandom.nextBytes(bytes);

        return HexFormat.of().formatHex(bytes);
    }
}