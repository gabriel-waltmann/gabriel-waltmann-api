package com.waltmann.waltmann_api.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginResponse(
        @Schema(description = "email")
        String email,
        @Schema(description = "JWT token")
        String token) {

}
