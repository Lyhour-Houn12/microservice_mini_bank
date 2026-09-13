package com.jing.accounts.payload.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Tag(
        name = "Customer Description",
        description = "To hold details customer, cards, and loans"

)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDerailsDto {

    @Schema(
            description = "Name of the customer", example = "Houn Lyhour"
    )
    @NotEmpty(message = "Name is required")
    @Size(min = 5, max = 50, message = "The length of customer name should be 5 to 50 characters")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "lyhourhoun@example.com"
    )
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Phone of the customer", example = "012345678"
    )
    @NotEmpty(message = "Mobile phone is required")
    @Pattern(regexp = "^$|^[0-9]{9}$", message = "Mobile phone must be 9 digits")
    private String mobilePhone;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountDto accountDto;

    private CardDto cardDto;

    private LoanDto loanDto;
}
