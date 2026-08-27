package com.jing.cards.repository;

import com.jing.cards.entity.Card;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByMobileNumber(String mobileNumber);

    boolean existsByCardNumber(String cardNumber);

    Optional<Card> findByCardNumber(@NotEmpty(message = "Card Number can not be a null or empty") @Pattern(regexp="(^$|[0-9]{12})",message = "CardNumber must be 12 digits") String cardNumber);
}
