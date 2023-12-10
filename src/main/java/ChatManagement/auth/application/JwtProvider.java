package ChatManagement.auth.application;

import ChatManagement.auth.error.InvalidTokenException;
import ChatManagement.auth.error.JwtSecurityException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    private final Key key;

    public JwtProvider(@Value("${jwt.secret}"
    ) String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            throw new JwtSecurityException();
        }
        catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
               IllegalArgumentException e) {
            throw e;
        }
    }

    public String parseAccessToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            return token;
        } else {
            throw new InvalidTokenException("AccessToken이 없거나 Bearer type이 아닙니다.");
        }
    }

    public long getUserIdFromToken(String accessToken) {
        String userId = parseClaims(accessToken).getSubject();

        return Long.parseLong(userId);
    }

    protected Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
