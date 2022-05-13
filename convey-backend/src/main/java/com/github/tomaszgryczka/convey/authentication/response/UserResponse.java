package com.github.tomaszgryczka.convey.authentication.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;

    private String username;

    private String email;
}
