package com.yxy.postitcardserver.service;

import com.yxy.postitcardserver.model.CardModel;
import com.yxy.postitcardserver.repository.CardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<CardModel> getAllCards() {
        return cardRepository.findAll();
    }

    public CardModel createCard(CardModel card) {
        // 验证必填字段
        if (card.getCardTitle() == null || card.getCardBody() == null) {
            throw new IllegalArgumentException("Card title and body are required.");
        }

        // 验证日期
        if (card.getExpirationDate() != null && card.getPublicationDate() != null) {
            if (card.getExpirationDate().before(card.getPublicationDate())) {
                throw new IllegalArgumentException("Expiration date cannot be before publication date.");
            }
        }

        // 验证长度限制
        int titleMaxLength = 100; // 标题最大长度
        int bodyMaxLength = 1000; // 内容最大长度
        if (card.getCardTitle().length() > titleMaxLength || card.getCardBody().length() > bodyMaxLength) {
            throw new IllegalArgumentException("Card title or body exceeds maximum length.");
        }

        return cardRepository.save(card);
    }

    public boolean deleteCard(Long id) {
        // 在这里编写删除卡片的逻辑
        try {
            cardRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            // 如果删除失败，则记录日志并返回 false
            e.printStackTrace();
            return false;
        }
    }

    public CardModel editCard(Long id, String cardTitle, String cardBody, Date publicationDate, Date expirationDate, boolean visible) {
        // 检查卡片是否存在
        CardModel existingCard = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with id: " + id));

        // 更新卡片属性
        existingCard.setCardTitle(cardTitle);
        existingCard.setCardBody(cardBody);
        existingCard.setPublicationDate(publicationDate);
        existingCard.setExpirationDate(expirationDate);
        existingCard.setVisible(visible);

        // 保存更新后的卡片并返回
        return cardRepository.save(existingCard);
    }
}
