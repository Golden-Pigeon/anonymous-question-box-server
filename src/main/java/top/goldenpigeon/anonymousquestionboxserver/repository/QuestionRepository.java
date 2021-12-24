package top.goldenpigeon.anonymousquestionboxserver.repository;

import top.goldenpigeon.anonymousquestionboxserver.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    Optional<Question> findById(Long id);

    List<Question> findAllByAnswererIdentify(String aId);
}
