package com.jing.accounts.payload.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Schema(name = "Account", description = "Schema to hold Account EazyBank")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @Schema(
            name = "Account Number of EazyBank Account",example ="1341234923"
    )
    @NotEmpty(message = "Account number is required")
    @Pattern(regexp = "(^$[0,9]{10})", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            name = "Account Type of EazyBank Account", example = "SAVINGS"
    )
    @NotEmpty(message = "Account type is required")
    private String accountType;


    @Schema(
            name = "Branch Address of EazyBank Account", example = "271 Toul Sangke, Phnom Penh"
    )
    @NotEmpty(message = "Branch address is required")
    private String branchAddress;
}
