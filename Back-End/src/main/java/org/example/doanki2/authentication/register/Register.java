package org.example.doanki2.authentication.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Register {

    private String username;

    private String password;

    private String email;

    private String phone;
}
