package top.goldenpigeon.anonymousquestionboxserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetailedDTO {
    Long id;
    Date askDate;
    Date lastModifiedDate;
    String questionerName;
    String answererName;
    String questionDetailed;
    String contentDetailed;
}
