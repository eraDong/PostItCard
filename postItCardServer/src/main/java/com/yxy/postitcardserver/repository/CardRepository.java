package com.yxy.postitcardserver.repository;

import com.yxy.postitcardserver.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardModel, Long> {
}
