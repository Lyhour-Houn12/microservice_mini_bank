package com.jing.accounts.payload.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(
        name = "Response Information",
        description = "Schema to hold successful information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    @Schema(
            description = "To show status code when response"
    )
    private String statusCode;

    @Schema(
            description = "To show state message when response"
    )
    private String statusMessage;
}
