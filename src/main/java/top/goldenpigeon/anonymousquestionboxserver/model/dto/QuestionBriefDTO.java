package top.goldenpigeon.anonymousquestionboxserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBriefDTO {
    Long id;
    Date askDate;
    Date lastModifiedDate;
    String questionerName;
    String answererName;
    String questionBrief;
    String contentBrief;
}
