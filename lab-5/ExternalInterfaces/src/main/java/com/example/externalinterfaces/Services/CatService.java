package com.example.externalinterfaces.Services;

import com.example.externalinterfaces.Entity.Cat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatService {
    @Autowired
    AmqpTemplate template;
    public String saveCat(Cat cat) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "saveCat");
        message.put("id", cat.getId());
        message.put("name", cat.getName());
        message.put("color", cat.getColor());
        message.put("breed", cat.getBreed());
        message.put("ownerId", cat.getOwnerID());
        ArrayNode arrayNode = mapper.valueToTree(cat.getFriendsOfCat());
        message.put("friendsOfCat", arrayNode);
        String reply = (String) template.convertSendAndReceive("cat", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }
    public String deleteCat(Long catID) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "deleteCat");
        message.put("id", catID);
        String reply = (String) template.convertSendAndReceive("cat", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");

    }

    public Cat findCatByID(Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findCatByID");
        message.put("id", id);
        String reply = (String) template.convertSendAndReceive("cat", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");

    }

    public ArrayList<Cat> getAllCats() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "getAllCats");
        String reply = (String) template.convertSendAndReceive("cat", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }
    public String addFriendship(Long idFriend1, Long idFriend2) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "addFriendship");
        message.put("id1", idFriend1);
        message.put("id2", idFriend2);
        String reply = (String) template.convertSendAndReceive("cat", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public List<Cat> findCatByBreed(String breed) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findCatByBreed");
        message.put("breed", breed);
        String reply = (String) template.convertSendAndReceive("cat", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public List<Cat> findCatByOwnerId(Long ownerId) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findCatByOwnerId");
        message.put("ownerId", ownerId);
        String reply = (String) template.convertSendAndReceive("cat", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }
}
