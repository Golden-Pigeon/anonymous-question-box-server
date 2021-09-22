package com.example.anonymousquestionboxserver.model.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
//@NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    @NonNull
    private String openId;
    private String name;
    private Integer askedCnt;
    private Integer askCnt;
    private Integer answerCnt;
    private String identify;

    public User() {
        this("");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(openId, user.openId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
