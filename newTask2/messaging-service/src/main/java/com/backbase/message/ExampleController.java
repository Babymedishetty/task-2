package com.backbase.message;

import com.backbase.dbs.messaging_service.api.service.v2.MessageApi;
import com.backbase.dbs.messaging_service.api.service.v2.model.MessageId;
import com.backbase.dbs.messaging_service.api.service.v2.model.MessageItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


import java.util.List;

@RestController
public class ExampleController implements MessageApi {
    private final ConcurrentMap<String, MessageItem> messageStore = new ConcurrentHashMap<>();


    @RequestMapping(method = RequestMethod.GET, value = "/message", produces = {
                    "application/json"
    })
    @ResponseStatus(HttpStatus.OK)
    public Message getMessage() {
        return new Message("Hello World");
    }

    @Override
    public ResponseEntity<Void> deleteMessage(String id) {
        if (messageStore.containsKey(id)) {
            messageStore.remove(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    @Override
    public ResponseEntity<MessageItem> getMessage(String id) {
        MessageItem message = messageStore.get(id);
        if (message != null) {
            return ResponseEntity.ok(message); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    @Override
    public ResponseEntity<List<MessageItem>> getMessages() {
        return ResponseEntity.ok(new ArrayList<>(messageStore.values())); // 200 OK
    }

    @Override
    public ResponseEntity<MessageId> postMessage(MessageItem body) {
        if (body.getId() == null || body.getId().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }
        messageStore.put(body.getId(), body);
        MessageId messageId = new MessageId();
        messageId.setId(body.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(messageId); // 201 Created
    }

    @Override
    public ResponseEntity<Void> putMessage(MessageItem body) {
        if (body.getId() == null || body.getId().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }
        messageStore.put(body.getId(), body);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}