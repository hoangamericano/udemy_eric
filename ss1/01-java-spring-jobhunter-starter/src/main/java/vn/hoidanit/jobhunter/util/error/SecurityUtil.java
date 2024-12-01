package vn.hoidanit.jobhunter.util.error;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    public void createToken(Authentication authentication) {
        // TODO Auto-generated method stub

        // Instant now = Instant.now();
        // Instant validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);

//         // @formatter:off
//         JwtClaimsSet claims = JwtClaimsSet.builder()
//             .issuedAt(now)
//             .expiresAt(validity)
//             .subject(authentication.getName())
//             .claim(AUTHORITIES_KEY, authorities)
//             .build();

//         JwsHeader jwsHeader 
// = JwsHeader.with(JWT_ALGORITHM).build();
//         return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

    }

}
