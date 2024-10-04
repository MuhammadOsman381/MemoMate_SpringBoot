package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.model.JournalEntriesModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.JournalEntriesRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.resources.JWT;
import io.jsonwebtoken.Claims;

@Component
public class JournalEntriesService {

    @Autowired
    private JournalEntriesRepo journalEntriesRepo;
    @Autowired
    JWT jwt;
    @Autowired
    UserRepo userRepo;

    public void AddJournalEntry(JournalEntriesModel journalEntry, String token) {
        Claims decodedToken = jwt.decodeJWT(token);
        String hexStringId = decodedToken.getSubject();
        ObjectId userId = new ObjectId(hexStringId);
        Optional<UserModel> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            JournalEntriesModel savedJournalEntry = journalEntriesRepo.save(journalEntry);
            user.getJournalEntries().add(savedJournalEntry);
            userRepo.save(user);
            System.out.println("Journal entry added to the user's journalEntries array.");
        } else {
            System.out.println("User not found.");
        }
    }

    public List<JournalEntriesModel> getAllJournalEntry() {
        return journalEntriesRepo.findAll();
    }

    public Optional<JournalEntriesModel> getJournalEntryById(ObjectId journalEntryID) {
        return journalEntriesRepo.findById(journalEntryID);
    }

    public List<JournalEntriesModel> getUserJournalEntries(String token) {
        Claims decodedToken = jwt.decodeJWT(token);
        String hexStringId = decodedToken.getSubject();
        ObjectId userId = new ObjectId(hexStringId);
        Optional<UserModel> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            return user.getJournalEntries();
        }
        return null;
    }

    public String deleteJournalEntryById(ObjectId journalEntryID, String token) {
        Claims decodedToken = jwt.decodeJWT(token);
        String hexStringId = decodedToken.getSubject();
        ObjectId userId = new ObjectId(hexStringId);
        Optional<UserModel> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            Optional<JournalEntriesModel> journalEntryOptional = journalEntriesRepo.findById(journalEntryID);
            if (journalEntryOptional.isPresent()) {
                JournalEntriesModel journalEntry = journalEntryOptional.get();
                boolean removed = user.getJournalEntries().remove(journalEntry);
                if (removed) {
                    userRepo.save(user);
                    journalEntriesRepo.deleteById(journalEntryID);
                    return "Journal entry deleted successfully";
                } else {
                    return "Journal entry not found in user's entries";
                }
            }
            return "Journal entry not found";
        }
        return "User not found";
    }

    public String updateJournalEntryById(ObjectId journalEntryID, JournalEntriesModel journalEntryData) {
        Optional<JournalEntriesModel> existingEntryOptional = journalEntriesRepo.findById(journalEntryID);
        if (existingEntryOptional.isPresent()) {
            JournalEntriesModel existingEntry = existingEntryOptional.get();
            existingEntry.setTitle(journalEntryData.getTitle());
            existingEntry.setContent(journalEntryData.getContent());
            journalEntriesRepo.save(existingEntry);
            return "Journal entry updated successfully";
        } else {
            return "Journal entry not found";
        }
    }

}
