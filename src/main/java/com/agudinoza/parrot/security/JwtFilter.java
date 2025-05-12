package com.agudinoza.parrot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        logger.debug("Procesando solicitud para la ruta: " + path);

        final String authHeader = request.getHeader("Authorization");
        logger.debug("Encabezado de autorización: "+ authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No hay token JWT en la solicitud");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        logger.debug("Token JWT extraído: "+ jwt);

        try {

            boolean isValid = jwtTokenProvider.validateToken(jwt);
            logger.debug("¿El token es válido?: " + isValid);

            if (isValid) {

                final String email = jwtTokenProvider.getEmailFromToken(jwt);
                logger.debug("Email extraído del token: "+ email);



                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    logger.debug("Usuario cargado: " + userDetails.getUsername());

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );


                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("Autenticación establecida en el contexto de seguridad");

            } else {
                logger.debug("Token JWT inválido");
            }
        } catch (Exception e) {
            logger.error("No se pudo establecer la autenticación: " + e.getMessage());
        }


        filterChain.doFilter(request, response);
    }

}
