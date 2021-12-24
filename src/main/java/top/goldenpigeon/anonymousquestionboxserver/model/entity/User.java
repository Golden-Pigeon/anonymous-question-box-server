package top.goldenpigeon.anonymousquestionboxserver.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    @NonNull
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String openId;
    private String name;
    private Integer askedCnt;
    private Integer askCnt;
    private Integer answerCnt;
    private String identify;



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
