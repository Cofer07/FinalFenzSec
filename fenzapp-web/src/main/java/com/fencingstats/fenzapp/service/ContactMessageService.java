package com.fencingstats.fenzapp.service;

import com.fencingstats.fenzapp.dao.ContactMessageRepository;
import com.fencingstats.fenzapp.model.ContactMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private static final String MESSAGE_PATTERN =
            "^(?![\\s\\S]*['\";])(?![\\s\\S]*<script>)(?!.*[0-9]{3,})(?!.*[\\p{Punct}]{3,})[\\p{L}\\p{N}\\p{Punct}\\p{Space}]{1,500}$";

    @Autowired
    public ContactMessageService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @Transactional
    public void saveMessage(ContactMessage contactMessage) {
        if (validateMessage(contactMessage.getMessage())) {
            contactMessageRepository.save(contactMessage);
        } else {
            throw new IllegalArgumentException("Message content is invalid");
        }
    }

    private boolean validateMessage(String message) {
        return Pattern.matches(MESSAGE_PATTERN, message);
    }
}
