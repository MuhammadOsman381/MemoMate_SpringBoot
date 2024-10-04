package com.example.demo.repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.example.demo.model.JournalEntriesModel;

@Component
public interface JournalEntriesRepo extends MongoRepository<JournalEntriesModel,ObjectId> {

}
