package com.yxy.postitcardserver.repository;

import com.yxy.postitcardserver.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardModel, Long> {
    List<CardModel> findByCardTitleContaining(String title);
}
