package com.kadiraksoy.partyapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${token.secret.key}")
    String jwtSecretKey;

    @Value("${token.expirationms}")
    Long jwtExpirationMs;


    // Verilen JWT'den kullanıcı adını çıkarmak için kullanılır.
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Kullanıcı detaylarından (örneğin, kullanıcı adı gibi) bir JWT oluşturmak için kullanılır.
    // Verilen kullanıcı detayları ve
    // isteğe bağlı olarak ekstra talep edilen bilgileri (extraClaims) JWT'nin içine yerleştirir.
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    //Bir JWT'nin geçerliliğini kontrol etmek için kullanılır.
    // Önce verilen token içerisinden kullanıcı adını çıkarır
    // ve sonra bu kullanıcı adını UserDetails'taki kullanıcı adı ile karşılaştırır.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    // JWT'den belirli bir talebi (claim) çıkarmak için tasarlanmıştır.
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }


    // Bu fonksiyon, UserDetails'dan alınan kullanıcı bilgileriyle bir JWT oluşturmayı amaçlar.
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    //Bu fonksiyon, verilen bir JWT'nin süresinin dolup dolmadığını kontrol eder.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Bu fonksiyon, JWT'nin içindeki son kullanma tarihini çıkarmayı hedefler.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    // extractExpiration, isTokenExpired gibi yardımcı fonksiyonlar,
    // JWT'nin içindeki tüm talepleri çıkarmak, son kullanma tarihini almak
    // ve tokenin süresinin dolup dolmadığını kontrol etmek için kullanılır.
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // bu fonksiyon JWT'lerin imzalanması için gereken gizli anahtarı oluşturur
    // ve bu anahtarı kullanarak JWT'nin güvenliğini ve bütünlüğünü sağlamak için imza işlemi gerçekleştirilir.
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}