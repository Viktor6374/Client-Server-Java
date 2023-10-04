package com.example.externalinterfaces.Services;

import com.example.externalinterfaces.Entity.Cat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CatService {
    @Autowired
    AmqpTemplate template;
    public Long saveCat(Cat cat) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "saveCat");
        message.put("id", cat.getId());
        message.put("name", cat.getName());
        message.put("color", cat.getColor());
        message.put("breed", cat.getBreed());
        message.put("ownerId", cat.getOwnerID());
        String reply = (String) template.convertSendAndReceive("cat", mapper.writeValueAsString(message));
        JsonNode jsonReply = new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return jsonReply.get("message").asLong();
    }
    public void deleteCat(Long catID) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "deleteCat");
        message.put("id", catID);
        String reply = (String) template.convertSendAndReceive("cat", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }
    }

    public Cat findCatByID(Long id) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findCatByID");
        message.put("id", id);
        String reply = (String) template.convertSendAndReceive("cat", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return parseCatReply(jsonReply.get("message"));
    }

    public List<Cat> getAllCats() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "getAllCats");
        String reply = (String) template.convertSendAndReceive("cat", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return parseArrayReply(jsonReply.get("message"));
    }
    public void addFriendship(Long idFriend1, Long idFriend2) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "addFriendship");
        message.put("id1", idFriend1);
        message.put("id2", idFriend2);
        String reply = (String) template.convertSendAndReceive("cat", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }
    }

    public List<Cat> findCatByBreed(String breed) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findCatByBreed");
        message.put("breed", breed);
        String reply = (String) template.convertSendAndReceive("cat", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return parseArrayReply(jsonReply.get("message"));
    }

    public List<Cat> findCatByOwnerId(Long ownerId) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findCatByOwnerId");
        message.put("ownerId", ownerId);
        String reply = (String) template.convertSendAndReceive("cat", mapper.writeValueAsString(message));
        JsonNode jsonReply = new ObjectMapper().readTree(reply);
        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return parseArrayReply(jsonReply.get("message"));
    }

    private Cat parseCatReply(JsonNode cat) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Long id = cat.get("id").asLong();
        String name = cat.get("name").textValue();
        String breed = cat.get("breed").textValue();
        String color = cat.get("color").textValue();
        Long ownerId = cat.get("ownerId").asLong();
        ArrayList<Long> friends = new ArrayList<>();
        if (cat.get("friendsOfCat") != null) {
            for (JsonNode friend : cat.get("friendsOfCat")) {
                friends.add(friend.asLong());
            }
        }
        Cat result = new Cat(id, name, breed, color, ownerId, friends);
        return result;
    }

    private List<Cat> parseArrayReply(JsonNode arrayNode) throws JsonProcessingException {
        List<Cat> result = new ArrayList<>();
        if (arrayNode == null){
            return result;
        }

        for (JsonNode cat : arrayNode){
            result.add(parseCatReply(cat));
        }

        return result;
    }
}
