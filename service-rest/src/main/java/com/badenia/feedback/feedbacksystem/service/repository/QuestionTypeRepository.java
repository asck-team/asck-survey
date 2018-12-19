package com.badenia.feedback.feedbacksystem.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.badenia.feedback.feedbacksystem.service.repository.model.QuestionTypeTableModel;

public interface QuestionTypeRepository extends JpaRepository<QuestionTypeTableModel, Long> {

}