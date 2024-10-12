package org.example.doanki2.authentication.login;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE)
public class AuthenticationResponse {
    String token;
    boolean authenticated;
}
