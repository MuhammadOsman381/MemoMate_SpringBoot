package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.JournalEntriesModel;
import com.example.demo.service.JournalEntriesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/journal-entries")
public class JournalEntries {

    @Autowired
    private JournalEntriesService journalEntriesService;

    @PostMapping("/add")
    public String addContent(@RequestBody JournalEntriesModel content,
            @RequestHeader("Authorization") String token) {
        journalEntriesService.AddJournalEntry(content, token);
        return "Journal Entry created succesfully!";
    }

    @GetMapping("/get")
    public List<JournalEntriesModel> getAll() {
        return journalEntriesService.getAllJournalEntry();
    }

    @GetMapping("/get/{postID}")
    public Optional<JournalEntriesModel> getPostById(@PathVariable ObjectId postID) {
        return journalEntriesService.getJournalEntryById(postID);
    }

    @GetMapping("/get-user-entries")
    public List<JournalEntriesModel> getUserEntries(@RequestHeader("Authorization") String token) {
        return journalEntriesService.getUserJournalEntries(token);
    }

    @DeleteMapping("/delete/{postID}")
    public String deletePostById(@PathVariable ObjectId postID,
            @RequestHeader("Authorization") String token) {
        return journalEntriesService.deleteJournalEntryById(postID, token);
    }

    @PutMapping("/update/{postID}")
    public String updatePostById(@PathVariable ObjectId postID, @RequestBody JournalEntriesModel content) {
        return journalEntriesService.updateJournalEntryById(postID, content);
    }
}
