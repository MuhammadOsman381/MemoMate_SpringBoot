package com.example.demo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@Document(collection = "Journal-Entries")
public class JournalEntriesModel {
    @Id
    private ObjectId id;
    private String title;
    private String content;
}
