package com.example.cats.Config;

import com.example.cats.DTO.CatDTO;
import com.example.cats.Services.CatService;
import com.example.cats.Services.CatServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class RabbitMqListener {
    @Autowired
    CatServiceImpl service;
    @RabbitListener(queues = "query-cats")
    @Transactional
    public String listen(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode reply = mapper.createObjectNode();

        try {
            ObjectNode jsonMessage = (ObjectNode) new ObjectMapper().readTree(message);
            if (Objects.equals(jsonMessage.get("command").textValue(), "saveCat")) {
                CatDTO catDTO = createCatDTO(jsonMessage);
                reply.put("result", "successful");
                reply.put("message", service.saveCat(catDTO));
                return mapper.writeValueAsString(reply);

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "deleteCat")) {
                service.deleteCat(jsonMessage.get("id").asLong());
                reply.put("result", "successful");
                return mapper.writeValueAsString(reply);

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "findCatByID")) {
                CatDTO catDTO = service.findCatByID(jsonMessage.get("id").asLong());
                if (catDTO == null) {
                    reply.put("result", "error");
                    reply.put("message", "cat not found");
                    return mapper.writeValueAsString(reply);
                } else {
                    reply.put("result", "successful");
                    reply.put("message", createJsonNode(catDTO));
                    return mapper.writeValueAsString(reply);
                }

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "getAllCats")) {
                List<CatDTO> result = service.getAllCats();
                return mapper.writeValueAsString(createArrayCats(result));

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "addFriendship")) {
                service.addFriendship(jsonMessage.get("id1").asLong(), jsonMessage.get("id2").asLong());
                reply.put("result", "successful");
                return mapper.writeValueAsString(reply);

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "findCatByBreed")) {
                List<CatDTO> result = service.findCatByBreed(jsonMessage.get("breed").textValue());
                return mapper.writeValueAsString(createArrayCats(result));

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "findCatByOwnerId")) {
                List<CatDTO> result = service.findCatByOwnerId(jsonMessage.get("ownerId").asLong());
                return mapper.writeValueAsString(createArrayCats(result));
            } else {
                throw new UnsupportedOperationException("Unsupported command");
            }
        }catch (Exception e){
            reply.put("result", "error");
            reply.put("message", e.getMessage());
            return mapper.writeValueAsString(reply);
        }
    }

    private CatDTO createCatDTO(ObjectNode message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Long id = message.get("id").asLong();
        String name = message.get("name").textValue();
        String color = message.get("color").textValue();
        String breed = message.get("breed").textValue();
        Long ownerId = message.get("ownerId").asLong();

        return new CatDTO(id, name, breed, color, ownerId);
    }

    private JsonNode createJsonNode(CatDTO catDTO){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("id", catDTO.getId());
        result.put("name", catDTO.getName());
        result.put("breed", catDTO.getBreed());
        result.put("color", catDTO.getColor());
        result.put("ownerId", catDTO.getOwnerID());
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
        result.put("message", arrayNode);
        return result;
    }
}
