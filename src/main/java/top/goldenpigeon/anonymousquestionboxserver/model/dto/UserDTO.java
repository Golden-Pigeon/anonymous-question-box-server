package top.goldenpigeon.anonymousquestionboxserver.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String name;
    private Integer askedCnt;
    private Integer askCnt;
    private Integer answerCnt;
    private String identify;
}
