package com.example.anonymousquestionboxserver.repository;

import com.example.anonymousquestionboxserver.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    Optional<Question> findById(String id);

    List<Question> findAllByAnswererIdentify(String aId);
}
