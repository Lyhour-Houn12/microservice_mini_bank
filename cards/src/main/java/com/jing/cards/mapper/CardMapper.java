package com.jing.cards.mapper;

import com.jing.cards.dto.CardDto;
import com.jing.cards.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public CardDto toDto(Card card) {
        if(card == null) return null;
        CardDto cardDto = new CardDto();
        cardDto.setMobileNumber(String.valueOf(card.getMobileNumber()));
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setCardType(card.getCartType());
        cardDto.setTotalLimit(card.getTotalLimit());
        cardDto.setAmountUsed(card.getAmountUsed());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        return cardDto;
    }

    public void updateCard(Card card, CardDto cardDto) {
        if(card == null) return;
        if(cardDto == null) return;
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setCardNumber(cardDto.getCardNumber());
        card.setCartType(cardDto.getCardType());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAmountUsed(cardDto.getAmountUsed());
        card.setAvailableAmount(cardDto.getAvailableAmount());
    }
}
