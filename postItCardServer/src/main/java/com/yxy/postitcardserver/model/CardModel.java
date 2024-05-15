package com.yxy.postitcardserver.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cardarrangement")
public class CardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "card_title", nullable = false)
    private String cardTitle;

    @Column(name = "card_body")
    private String cardBody;

    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "visible", nullable = false)
    private boolean visible;

    public CardModel() {
    }

    public CardModel(String cardTitle, String cardBody, Date publicationDate, boolean visible, Date expirationDate) {
        this.cardTitle = cardTitle;
        this.cardBody = cardBody;
        this.publicationDate = publicationDate;
        this.visible = visible;
        this.expirationDate = expirationDate;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardBody() {
        return cardBody;
    }

    public void setCardBody(String cardBody) {
        this.cardBody = cardBody;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
