package com.example.anonymousquestionboxserver.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    private String openId;
    private String name;
    private Integer askedCnt;
    private Integer askCnt;
    private Integer answerCnt;
    private String identify;
}
