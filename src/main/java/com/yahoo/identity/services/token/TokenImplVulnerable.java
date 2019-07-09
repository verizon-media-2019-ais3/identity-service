package com.yahoo.identity.services.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.yahoo.identity.IdentityError;
import com.yahoo.identity.IdentityException;
import com.yahoo.identity.services.key.KeyService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.annotation.Nonnull;
import javax.ws.rs.NotAuthorizedException;

public class TokenImplVulnerable implements Token {

    private final KeyService keyService;
    private TokenType tokenType;
    private Instant issueTime;
    private Instant expireTime;
    private String subject;

    public TokenImplVulnerable(@Nonnull KeyService keyService) {
        this.keyService = keyService;
    }

    @Override
    public void setIssueTime(@Nonnull Instant issueTime) {
        this.issueTime = issueTime;
    }

    @Override
    public void setExpireTime(@Nonnull Instant expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public void setSubject(@Nonnull String subject) {
        this.subject = subject;
    }

    @Override
    @Nonnull
    public String toString() {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.keyService.getSecret("token.key"));
            String token = JWT.create()
                .withExpiresAt(Date.from(this.expireTime))
                .withIssuedAt(Date.from(this.issueTime))
                .withSubject(this.subject)
                .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new IdentityException(IdentityError.INVALID_CREDENTIAL, "JWT verification does not succeed.");
        }
    }

    public void setTokenType(@Nonnull TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public void validate() {
        Boolean isValid = this.expireTime.compareTo(Instant.now()) > 0;

        if (tokenType == TokenType.CRITICAL) {
            Instant criteriaTs = Instant.now().plus(-5, ChronoUnit.MINUTES);
            isValid = (this.issueTime.compareTo(criteriaTs) > 0) && isValid;
        }
        if (!isValid) {
            throw new NotAuthorizedException("Token is not valid.");
        }
    }
}
