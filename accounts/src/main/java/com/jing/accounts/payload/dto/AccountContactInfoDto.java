package com.jing.accounts.payload.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
@Data
public class AccountContactInfoDto {
    String message;
    Map<String, String> contactDetails;
    List<String> onCallSupport;
}
