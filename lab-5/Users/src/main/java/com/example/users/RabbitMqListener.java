package com.example.users;

import com.example.users.DTO.OwnerDTO;
import com.example.users.DTO.RoleDTO;
import com.example.users.DTO.UserDTO;
import com.example.users.Services.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    UserServiceImpl service;

    @RabbitListener(queues = "query-owners")
    public String worker1(String message) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode reply = mapper.createObjectNode();

        try {
            ObjectNode jsonMessage = (ObjectNode) new ObjectMapper().readTree(message);
            if (Objects.equals(jsonMessage.get("command").asText(), "changeOwner")) {
                OwnerDTO ownerDTO = createOwnerDTO(jsonMessage);
                reply.put("result", "successful");
                reply.put("id", service.changeOwner(ownerDTO));
                return reply.asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "findOwnerByID")) {
                OwnerDTO ownerDTO = service.findOwnerByID(jsonMessage.get("id").asLong());
                if (ownerDTO == null) {
                    reply.put("result", "error");
                    reply.put("message", "owner not found");
                    return reply.asText();
                } else {
                    reply.put("result", "successful");
                    reply.put("cat", createJsonNodeOwner(ownerDTO).asText());
                    return reply.asText();
                }

            } else if (Objects.equals(jsonMessage.get("command").asText(), "findOwnersByName")) {
                List<OwnerDTO> result = service.findOwnersByName(jsonMessage.get("name").asText());
                reply.put("result", "successful");
                reply.put("message", createArrayOwners(result).asText());
                return reply.asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "loadUserByUsername")) {
                UserDTO result = service.findUserByUsername(jsonMessage.get("username").asText());
                reply.put("result", "successful");
                reply.put("message", createJsonNodeUser(result).asText());
                return reply.asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "saveUser")) {
                UserDTO userDTO = createUserDTO(jsonMessage);
                service.saveUser(userDTO);
                reply.put("result", "successful");
                reply.put("message", service.saveUser(userDTO));
                return reply.asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "deleteUser")) {
                service.deleteUser(jsonMessage.get("ownerId").asLong());
                reply.put("result", "successful");
                return reply.asText();

            } else if (Objects.equals(jsonMessage.get("command").asText(), "deleteCat")) {
                service.deleteCat(jsonMessage.get("catId").asLong(), jsonMessage.get("ownerId").asLong());
                reply.put("result", "successful");
                return reply.asText();
            } else if (Objects.equals(jsonMessage.get("command").asText(), "addCat")) {
                service.addCat(jsonMessage.get("catId").asLong(), jsonMessage.get("ownerId").asLong());
                reply.put("result", "successful");
                return reply.asText();
            } else if (Objects.equals(jsonMessage.get("command").asText(), "getAllOwners")) {
                List<OwnerDTO> result = service.getAllOwners();
                reply.put("result", "successful");
                reply.put("message", createArrayOwners(result).asText());
                return reply.asText();
            } else {
                throw new UnsupportedOperationException("Unsupported command");
            }
        } catch (Exception e) {
            reply.put("result", "error");
            reply.put("message", e.getMessage());
            return reply.asText();
        }
    }

    private OwnerDTO createOwnerDTO(ObjectNode message) {
        Long id = Long.parseLong(message.get("id").asText());
        String name = message.get("name").asText();
        List<Long> cats = new ArrayList<>();
        for (JsonNode node : message.get("cats")) {
            cats.add(Long.parseLong(node.asText()));
        }

        return new OwnerDTO(id, name, cats);
    }

    private UserDTO createUserDTO(ObjectNode message) throws JsonProcessingException {
        Long id = Long.parseLong(message.get("id").asText());
        String username = message.get("username").asText();
        String password = message.get("password").asText();
        RoleDTO roleDTO = new RoleDTO(message.get("username").asText());
        ObjectMapper mapper = new ObjectMapper();
        OwnerDTO ownerDTO = createOwnerDTO((ObjectNode) message.get("owner"));

        return new UserDTO(id, password, password, ownerDTO, roleDTO);
    }

    private JsonNode createJsonNodeOwner(OwnerDTO ownerDTO) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("id", ownerDTO.getId());
        result.put("name", ownerDTO.getName());
        ArrayNode arrayNode = mapper.valueToTree(ownerDTO.getCats());
        result.put("cats", arrayNode);

        return result;
    }

    private JsonNode createJsonNodeUser(UserDTO userDTO) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        result.put("id", userDTO.getId());
        result.put("username", userDTO.getUsername());
        result.put("password", userDTO.getPassword());
        result.put("role", userDTO.getRole().getName());
        result.put("owner", createJsonNodeOwner(userDTO.getOwner()));

        return result;
    }

    private JsonNode createArrayOwners(List<OwnerDTO> owners) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        ArrayNode arrayNode = mapper.createArrayNode();
        for (OwnerDTO ownerDTO : owners) {
            arrayNode.add(createJsonNodeOwner(ownerDTO));
        }
        result.put("result", "successful");
        result.put("owners", arrayNode);
        return result;
    }
}
