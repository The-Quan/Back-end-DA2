package org.example.doanki2.authentication.login;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.example.doanki2.entity.Users;
import org.example.doanki2.model.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

@Service
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    UserRepository userRepository;

    @Value("${jwt.signerKey}")
    private String getSignerKey;

    // Đăng nhập
    public AuthenticationResponse authentication(AuthenticationRequest request) {
        Optional<Users> user = userRepository.findByUserName(request.getUsername());
        if (!user.isPresent()) {
            log.error("User not found: {}", request.getUsername());
            throw new IllegalArgumentException("User not found");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.get().getPassword());
        if (!authenticated) {
            log.error("Invalid credentials for user: {}", request.getUsername());
            throw new IllegalArgumentException("Invalid credentials");
        }
        var token = generateToken(user.orElse(null));
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // Kiểm tra token hợp lệ
    public IntrospectResponse introspectRequest(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(getSignerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        boolean isValid = verified && expiryTime.after(new Date());

        if (!isValid) {
            log.warn("Invalid or expired token: {}", token);
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    // Tạo token
    private String generateToken(Users users) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(users.getUsername())
                .issuer("quan")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .claim("scope", buildScope(users))
                .build();

        SignedJWT signedJWT = new SignedJWT(header, jwtClaimsSet);

        try {
            signedJWT.sign(new MACSigner(getSignerKey.getBytes()));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token for user: {}", users.getUsername(), e);
            throw new RuntimeException("Error creating token", e);
        }
    }

    // Xây dựng phạm vi (scope) từ role của người dùng
    private String buildScope(Users user) {
        StringJoiner stringJoiner = new StringJoiner(",");
        if (user.getRole() != null && StringUtils.hasText(user.getRole().getRole_name())) {
            stringJoiner.add(user.getRole().getRole_name());
        } else {
            stringJoiner.add("default_role");
        }
        return stringJoiner.toString();
    }
}
