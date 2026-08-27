package com.jing.cards.service.impl;

import com.jing.cards.constant.CardConstant;
import com.jing.cards.dto.CardDto;
import com.jing.cards.entity.Card;
import com.jing.cards.exception.CardAlreadyExistsException;
import com.jing.cards.exception.ResourceNotFoundException;
import com.jing.cards.mapper.CardMapper;
import com.jing.cards.repository.CardRepository;
import com.jing.cards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper  cardMapper;

    @Override
    public void createCart(String mobileNumber) {
        Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobile number " +  mobileNumber);
        }
        cardRepository.save(createCard(mobileNumber));
    }

    private Card createCard(String mobileNumber) {
        Card card = new Card();
        card.setMobileNumber(mobileNumber);
        card.setCardNumber(generateUniqueCardNumber());
        card.setCartType(CardConstant.CREDIT_CARD);
        card.setTotalLimit(CardConstant.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardConstant.NEW_CARD_LIMIT);
        return card;
    }

    private String generateUniqueCardNumber() {
        String cardNumber;
        do{
            cardNumber  = String.valueOf(100000000000L + new Random().nextInt(900000000));
        }while (cardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "Mobile Number", mobileNumber));
        return cardMapper.toDto(card);
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Card", "Card Number", cardDto.getCardNumber()));
        cardMapper.updateCard(card, cardDto);
        cardRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "Mobile Number", mobileNumber));
        cardRepository.deleteById(card.getCardId());
        return true;
    }
}
