package com.avalburo.backend.apirest.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.avalburo.backend.apirest.entities.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageDao {
	private static final String FILE_PATH = "forum.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    
    private List<Message> messages;

    private int getNextId(List<Message> messages) {
        int maxId = messages.stream().mapToInt(Message::getId).max().orElse(0);
        return maxId + 1;
    }

    public List<Message> findAll(int userId) {
        try {
            messages = objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Message>>() {});
            messages = messages.stream() 
                    .filter(m -> m.getUser().getId() == userId)
                    .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
                    .collect(Collectors.toList());;
            return messages;
        } catch (IOException e) {
            e.printStackTrace();
            messages = null;
            return new ArrayList<>();
        }
    }

    public Message findById(int id) {
        try {
            messages = objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Message>>() {});
            Optional<Message> optionalMessage = messages.stream().filter(message -> message.getId() == id).findFirst();
            return optionalMessage.orElse(null);
        } catch (IOException e) {
            messages = null;
            e.printStackTrace();
        }
        return null;
    }
    
    private Optional<Message> findMessageById(int id, List<Message> messages) {
        return messages.stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }
    
    public Message save(Message message) {
        List<Message> messages = findAll(message.getUser().getId());
        int nextId = getNextId(messages);
        message.setId(nextId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        ZonedDateTime ecuadorDateTime = currentDateTime.atZone(ZoneId.systemDefault());
        Timestamp currentTimestamp = Timestamp.from(ecuadorDateTime.toInstant());
        message.setCreatedAt(currentTimestamp);
        messages.add(message);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), messages);
            return message;
        } catch (IOException e) {
            messages = null;
            e.printStackTrace();
        }
        return null;
    }

    public Message saveResponse(int messageId, Message response) {
        List<Message> messages = findAll(response.getUser().getId());

        Optional<Message> parentOptional = messages.stream()
                .filter(m -> m.getId() == messageId)
                .findFirst();
        LocalDateTime currentDateTime = LocalDateTime.now();
        ZonedDateTime ecuadorDateTime = currentDateTime.atZone(ZoneId.systemDefault());
        Timestamp currentTimestamp = Timestamp.from(ecuadorDateTime.toInstant());

        if (parentOptional.isPresent()) {
            Message parent = parentOptional.get();
            if (parent.getResponses() == null) {
            	parent.setResponses(new ArrayList<>());
            }
            if (response.getResponseId() == 0) {
                int nextResponseId = parent.getResponses().size() + (100 * messageId);
                response.setId(nextResponseId);
                response.setParent(parent);
                response.setCreatedAt(currentTimestamp);
                parent.getResponses().add(response);
            } else {
            	Optional<Message> parentResponseOptional = findMessageById(response.getResponseId(), parent.getResponses());
                if (parentResponseOptional.isPresent()) {
                    Message parentResponse = parentResponseOptional.get();
                    if (parentResponse.getResponses() == null) {
                    	parentResponse.setResponses(new ArrayList<>());
                    }
                    int nextChildrenResponseId = parentResponse.getResponses().size() + (1000 * response.getResponseId());
                    response.setId(nextChildrenResponseId);
                    response.setParent(parentResponse);
                    response.setCreatedAt(currentTimestamp);
                    parentResponse.getResponses().add(response);
                }
            }
            
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), messages);
                return response;
            } catch (IOException e) {
                messages = null;
                e.printStackTrace();
            }
        }
        return null;
    }
    
}
