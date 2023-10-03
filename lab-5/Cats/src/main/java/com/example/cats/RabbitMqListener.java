package com.example.cats;

import com.example.cats.DTO.CatDTO;
import com.example.cats.Services.CatService;
import com.example.cats.Services.CatServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class RabbitMqListener {
    @Autowired
    CatServiceImpl service;
    @RabbitListener(queues = "query-cats")
    public String listen(String message) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode reply = mapper.createObjectNode();

        try {
            ObjectNode jsonMessage = (ObjectNode) new ObjectMapper().readTree(message);
            if (Objects.equals(jsonMessage.get("command").asText(), "saveCat")) {
                CatDTO catDTO = createCatDTO(jsonMessage);
                reply.put("result", "successful");
                reply.put("id", service.saveCat(catDTO));
                return reply.asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "deleteCat")) {
                service.deleteCat(jsonMessage.get("id").asLong());
                reply.put("result", "successful");
                return reply.asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "findCatByID")) {
                CatDTO catDTO = service.findCatByID(jsonMessage.get("id").asLong());
                if (catDTO == null) {
                    reply.put("result", "error");
                    reply.put("message", "cat not found");
                    return reply.asText();
                } else {
                    reply.put("result", "successful");
                    reply.put("cat", createJsonNode(catDTO).asText());
                    return reply.asText();
                }

            } else if (Objects.equals(jsonMessage.get("command").asText(), "getAllCats")) {
                List<CatDTO> result = service.getAllCats();
                return createArrayCats(result).asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "addFriendship")) {
                service.addFriendship(jsonMessage.get("id1").asLong(), jsonMessage.get("id1").asLong());
                reply.put("result", "successful");
                return reply.asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "findCatByBreed")) {
                List<CatDTO> result = service.findCatByBreed(jsonMessage.get("breed").asText());
                return createArrayCats(result).asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "findCatByBreed")) {
                List<CatDTO> result = service.findCatByOwnerId(jsonMessage.get("ownerId").asLong());
                return createArrayCats(result).asText();
            } else {
                throw new UnsupportedOperationException("Unsupported command");
            }
        }catch (Exception e){
            reply.put("result", "error");
            reply.put("message", e.getMessage());
            return reply.asText();
        }
    }

    private CatDTO createCatDTO(ObjectNode message){
        Long id = Long.parseLong(message.get("id").asText());
        String name = message.get("name").asText();
        String color = message.get("color").asText();
        String breed = message.get("breed").asText();
        Long ownerId = Long.parseLong(message.get("ownerId").asText());
        List<Long> friends = new ArrayList<>();
        for (JsonNode node: message.get("friendsOfCat")){
            friends.add(Long.parseLong(node.asText()));
        }

        return new CatDTO(id, name, breed, color, ownerId, friends);
    }

    private JsonNode createJsonNode(CatDTO catDTO){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("id", catDTO.getId());
        result.put("name", catDTO.getName());
        result.put("breed", catDTO.getBreed());
        result.put("color", catDTO.getColor());
        ArrayNode friends = mapper.valueToTree(catDTO.getFriendsOfCat());
        result.put("friendsOfCat", friends);

        return result;
    }

    private JsonNode createArrayCats(List<CatDTO> cats){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        for (CatDTO catDTO: cats){
            arrayNode.add(createJsonNode(catDTO));
        }
        result.put("result", "successful");
        result.put("cats", arrayNode);
        return result;
    }
}
