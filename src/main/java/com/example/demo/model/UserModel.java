package com.example.demo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Component
@Data
@Document(collection = "User")
public class UserModel {
    @Id
    private ObjectId id;
    private String name;
    private String password;
    @DBRef
    private List<JournalEntriesModel> journalEntries = new ArrayList<>();
}
