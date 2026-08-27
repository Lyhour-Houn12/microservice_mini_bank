package com.jing.cards.service;

import com.jing.cards.dto.CardDto;

public interface CardService {

    void createCart(String mobileNumber);

    CardDto fetchCard(String mobileNumber);

    boolean updateCard(CardDto cardDto);

    boolean deleteCard(String mobileNumber);
}
