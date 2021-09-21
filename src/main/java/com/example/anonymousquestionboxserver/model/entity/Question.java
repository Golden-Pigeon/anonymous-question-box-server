package com.example.anonymousquestionboxserver.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@NoArgsConstructor
@Entity
@Table
public class Question {

    @Id
    String id;
    Date askDate;
    Date lastModifiedDate;
    String questionerOpenId;
    String answererOpenId;
    String question;
    String content;
}
