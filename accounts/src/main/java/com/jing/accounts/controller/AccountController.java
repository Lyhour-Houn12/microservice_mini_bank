package com.jing.accounts.controller;

import com.jing.accounts.constant.AccountConstants;
import com.jing.accounts.payload.dto.AccountContactInfoDto;
import com.jing.accounts.payload.dto.CustomerDto;
import com.jing.accounts.payload.response.ErrorResponseDto;
import com.jing.accounts.payload.response.ResponseDto;
import com.jing.accounts.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "CRUD REST API for Accounts in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH, AND DELETE accounts in EazyBank"
)
@RestController
@RequestMapping("/api/v1/accounts")
@Validated
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountContactInfoDto accountContactInfoDto;

    @Operation(
            summary = "Create Account For REST API",
            description = "REST API to create customer & account details"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }
    @Operation(
            summary = "Fetch Accounts Details REST API",
            description = "REST API to fetch Customer & Account details based on a mobile phone"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(@Valid @RequestParam @Pattern(regexp = "^$|^[0-9]{9}$", message = "Mobile phone must be 10 digits") String mobilePhone){
        CustomerDto customerDto = accountService.fetchCustomerDetails(mobilePhone);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @Operation(
            summary = "Update Account for REST API",
            description = "REST API to update Customer & Account details based on customerDto as request body"
    )
    @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP status EXPECTATION_FAILED",
                    content = @Content(
                            schema =  @Schema(implementation = ErrorResponseDto.class)
                    )
            )



    })

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdate = accountService.updateAccount(customerDto);
        if(isUpdate){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED  ).body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Account for REST API",
            description = "REST API to delete Customer & Account details based on a mobile phone"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP status EXPECTATION_FAILED",
                    content = @Content(
                            schema =  @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@Valid @RequestParam @Pattern(regexp = "^$|^[0-9]{9}$", message = "Mobile phone must be 10 digits") String mobilePhone){
        boolean isDelete = accountService.deleteAccount(mobilePhone);
        if(isDelete){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }


    @Operation(
            summary = "Builder Version Info For REST API",
            description = "REST API to describe builder version"
    )
    @GetMapping("/build-info")
    public ResponseEntity<?> getBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @Operation(
            summary = "Get Java Version",
            description = "REST API to describe java version"
    )
    @GetMapping("/java-version")
    public ResponseEntity<?> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }


    @Operation(
            summary = "Get Contact Information",
            description = "REST API to describe about contact information of accounts"
    )
    @GetMapping("/contact-info")
    public ResponseEntity<?> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(accountContactInfoDto);
    }

}
