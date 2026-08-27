package com.jing.accounts.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "Error Response",
        description = "Schema to hold error response of the REST API"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API path invoked by client"
    )
    private String apiPath;

    @Schema(
            description ="Error code representing the errors happen.")
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the errors happen."
    )
    private String errorMessage;

    @Schema(
            description = "Representing the exact time."
    )
    private LocalDateTime errorTime;
}
