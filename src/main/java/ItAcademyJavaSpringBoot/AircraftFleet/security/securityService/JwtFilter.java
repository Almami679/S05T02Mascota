package ItAcademyJavaSpringBoot.AircraftFleet.security.securityService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customDetailsService;

    Claims claims = null;

    private String username = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Permitir acceso sin autenticación a ciertos endpoints
        if (request.getServletPath().matches("/user/login|/user/forgotPassword|/user/signup|/swagger-ui/index.html")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener el header de autorización
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Validar que el header no sea null y comience con "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Extraer el token real

            // Validar que el token no esté vacío antes de procesarlo
            if (!token.trim().isEmpty()) {
                try {
                    username = jwtService.extractUsername(token);
                    claims = jwtService.extractAllClaims(token);
                } catch (Exception e) {
                    // Si el token es inválido, no se intenta autenticar
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido o expirado");
                    return;
                }
            }
        }

        // Si hay un usuario válido y no hay autenticación previa, validar el token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }



    public boolean isAdmin(){
        return "Admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){
        return username;
    }
}
