package password_manager.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import password_manager.model.User;
import password_manager.model.UserSession;
import password_manager.repository.UserSessionRepository;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final UserSessionRepository userSessionRepository;

    public TokenAuthenticationFilter(
            UserSessionRepository userSessionRepository
    ) {
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        UserSession session = userSessionRepository
                .findByToken(token)
                .orElse(null);

        if (session == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {

            userSessionRepository.delete(session);

            filterChain.doFilter(request, response);
            return;
        }

        User user = session.getUser();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}