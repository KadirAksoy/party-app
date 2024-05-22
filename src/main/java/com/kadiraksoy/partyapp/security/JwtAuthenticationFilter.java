package com.kadiraksoy.partyapp.security;
import com.kadiraksoy.partyapp.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        //HTTP isteğinin başlıklarından "Authorization" başlığını alır.
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        //Eğer "Authorization" başlığı boşsa veya "Bearer " ile başlamıyorsa,
        // isteği filtreleme işlemine devam ettirir (filterChain.doFilter(request, response);),
        // yani kimlik doğrulama adımını atlar ve isteği işleme devam ettirir.
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //"Authorization" başlığı "Bearer " ile başlıyorsa, JWT'yi alır ve başındaki "Bearer " kısmını çıkarır.
        jwt = authHeader.substring(7);
        //Aldığı JWT'yi loglar.
        log.debug("JWT - {}", jwt.toString());
        // JWT'den kullanıcı adını çıkarır.
        userEmail = jwtService.extractUserName(jwt);
        //Eğer kullanıcı adı geçerli ve güvenlik bağlamı (SecurityContext) içerisinde kimlik doğrulaması yapılmamışsa
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Kullanıcı detaylarını kullanarak userService'den UserDetails nesnesini alır.
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            //jwtService.isTokenValid(jwt, userDetails) ile JWT'nin geçerliliğini ve doğruluğunu kontrol eder.
            //Eğer JWT geçerliyse:
            if (jwtService.isTokenValid(jwt, userDetails)) {
                log.debug("User - {}", userDetails);
                //Kullanıcıyı temsil eden bir kimlik doğrulama token'ı oluşturur (UsernamePasswordAuthenticationToken).
                //İstek detaylarını (details) ekler ve güvenlik bağlamını oluşturur (authentication).
                //Güvenlik bağlamını günceller (SecurityContextHolder.setContext(context);).
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        //işlem devam eder ve gelen isteği diğer filtrelerle birlikte işler.
        filterChain.doFilter(request, response);
    }
}