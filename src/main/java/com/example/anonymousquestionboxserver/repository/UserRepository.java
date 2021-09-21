package com.example.anonymousquestionboxserver.repository;

import com.example.anonymousquestionboxserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByOpenId(String openId);

    Optional<User> findByIdentify(String identify);


}
