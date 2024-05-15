package com.yxy.postitcardserver.controller;

import com.yxy.postitcardserver.model.CardModel;
import com.yxy.postitcardserver.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<CardModel>> getAllCards() {
        List<CardModel> cards = cardService.getAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CardModel> addCard(@RequestParam("cardTitle") String cardTitle,
                                             @RequestParam("cardBody") String cardBody,
                                             @RequestParam("publicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date publicationDate,
                                             @RequestParam("expirationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date expirationDate,
                                             @RequestParam("visible") boolean visible) {
        CardModel card = new CardModel(cardTitle, cardBody, publicationDate, visible, expirationDate);
        CardModel createdCard = cardService.createCard(card);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable Long id) {
        // 调用 cardService 中的方法来删除卡片
        boolean deleted = cardService.deleteCard(id);
        if (deleted) {
            return new ResponseEntity<>("Card deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to delete card", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CardModel> editCard(
            @PathVariable Long id,
            @RequestParam("cardTitle") String cardTitle,
            @RequestParam("cardBody") String cardBody,
            @RequestParam("publicationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date publicationDate,
            @RequestParam("expirationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date expirationDate,
            @RequestParam("visible") boolean visible
    ) {
        CardModel updatedCard = cardService.editCard(id, cardTitle, cardBody, publicationDate, expirationDate,visible);
        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
    }
}
