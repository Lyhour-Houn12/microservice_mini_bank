package com.jing.accounts.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;


    @NotEmpty(message = "Name is required")
    @Size(min = 5, max = 50, message = "The length of customer name should be 5 to 50 characters")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be a valid value")
    private String email;

    @NotEmpty(message = "Mobile phone is required")
    @Pattern(regexp = "^$|^[0-9]{9}$", message = "Mobile phone must be 9 digits")
    @Column(name = "mobile_phone", unique = true)
    private String mobilePhone;
}
