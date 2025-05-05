package org.example.psychologicalcounseling.module.safety;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtilTokenBuilder {
    private final String secretKey = "huashixintuyinhezhanjianjintianzhengshiqihang"; // 用于签署和验证令牌的密钥，请替换为自己的密钥
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
    private final long validityInMilliseconds = 360000000; // 令牌有效期一百小时

    /**
     * 生成JWT令牌
     * @param email 用户的电子邮件地址
     * @return 生成的JWT令牌
     */
    public String generateToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从JWT令牌中提取电子邮件地址
     * @param token JWT令牌
     * @return 提取的电子邮件地址
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * 验证JWT令牌的有效性
     * @param token JWT令牌
     * @return 如果令牌有效，则返回true，否则返回false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
