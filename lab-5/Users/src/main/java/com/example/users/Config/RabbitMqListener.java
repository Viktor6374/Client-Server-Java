package com.example.users.Config;

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
    public String worker1(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode reply = mapper.createObjectNode();

        try {
            ObjectNode jsonMessage = (ObjectNode) new ObjectMapper().readTree(message);
            if (Objects.equals(jsonMessage.get("command").textValue(), "changeOwner")) {
                OwnerDTO ownerDTO = createOwnerDTO(jsonMessage);
                reply.put("result", "successful");
                reply.put("message", service.changeOwner(ownerDTO));
                return mapper.writeValueAsString(reply);

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "findOwnerByID")) {
                OwnerDTO ownerDTO = service.findOwnerByID(jsonMessage.get("id").asLong());
                if (ownerDTO == null) {
                    reply.put("result", "error");
                    reply.put("message", "owner not found");
                    return mapper.writeValueAsString(reply);
                } else {
                    reply.put("result", "successful");
                    reply.put("message", createJsonNodeOwner(ownerDTO));
                    return mapper.writeValueAsString(reply);
                }

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "findOwnersByName")) {
                List<OwnerDTO> result = service.findOwnersByName(jsonMessage.get("name").textValue());
                reply.put("result", "successful");
                reply.put("message", createArrayOwners(result));
                return mapper.writeValueAsString(reply);

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "loadUserByUsername")) {
                UserDTO result = service.findUserByUsername(jsonMessage.get("username").textValue());
                reply.put("result", "successful");
                reply.put("message", createJsonNodeUser(result));
                return mapper.writeValueAsString(reply);

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "saveUser")) {
                UserDTO userDTO = createUserDTO(jsonMessage);
                reply.put("result", "successful");
                reply.put("message", service.saveUser(userDTO));
                return mapper.writeValueAsString(reply);

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "deleteUser")) {
                service.deleteUser(jsonMessage.get("ownerId").asLong());
                reply.put("result", "successful");
                return mapper.writeValueAsString(reply);

            } else if (Objects.equals(jsonMessage.get("command").textValue(), "deleteCat")) {
                service.deleteCat(jsonMessage.get("catId").asLong(), jsonMessage.get("ownerId").asLong());
                reply.put("result", "successful");
                return mapper.writeValueAsString(reply);
            } else if (Objects.equals(jsonMessage.get("command").textValue(), "addCat")) {
                service.addCat(jsonMessage.get("catId").asLong(), jsonMessage.get("ownerId").asLong());
                reply.put("result", "successful");
                return mapper.writeValueAsString(reply);
            } else if (Objects.equals(jsonMessage.get("command").textValue(), "getAllOwners")) {
                List<OwnerDTO> result = service.getAllOwners();
                reply.put("result", "successful");
                reply.put("message", createArrayOwners(result));
                return mapper.writeValueAsString(reply);
            } else {
                throw new UnsupportedOperationException("Unsupported command");
            }
        } catch (Exception e) {
            reply.put("result", "error");
            reply.put("message", e.getMessage());
            return mapper.writeValueAsString(reply);
        }
    }

    private OwnerDTO createOwnerDTO(ObjectNode message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Long id = message.get("id").asLong();
        String name = message.get("name").textValue();
        List<Long> cats = new ArrayList<>();
        for (JsonNode node : message.get("cats")) {
            cats.add(Long.parseLong(node.textValue()));
        }

        return new OwnerDTO(id, name, cats);
    }

    private UserDTO createUserDTO(ObjectNode message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Long id = message.get("id").asLong();
        String username = message.get("username").textValue();
        String password = message.get("password").textValue();
        RoleDTO roleDTO = new RoleDTO(message.get("role").textValue());
        OwnerDTO ownerDTO = createOwnerDTO((ObjectNode) message.get("owner"));

        return new UserDTO(id, password, username, ownerDTO, roleDTO);
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
        ArrayNode arrayNode = mapper.createArrayNode();
        for (OwnerDTO ownerDTO : owners) {
            arrayNode.add(createJsonNodeOwner(ownerDTO));
        }
        return arrayNode;
    }
}
