package danielrichtersz.security;

import danielrichtersz.models.JwtUser;
import danielrichtersz.models.Redditor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {


    public String generate(Redditor redditor) {


        Claims claims = Jwts.claims()
                .setSubject(redditor.getUsername());
        claims.put("userId", String.valueOf(redditor.getRedditorId()));
        claims.put("role", redditor.getRole());


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "youtube")
                .compact();
    }
}
