package com.example.externalinterfaces.Services;

import com.example.externalinterfaces.Entity.Cat;
import com.example.externalinterfaces.Entity.Owner;
import com.example.externalinterfaces.Entity.User1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService implements UserDetailsService {
    @Autowired
    AmqpTemplate template;
    public String changeOwner(Owner owner) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "changeOwner");
        message.put("id", owner.getId());
        message.put("name", owner.getName());
        List<Long> catsId = owner.getCats().stream().map(Cat::getId).collect(Collectors.toList());
        ArrayNode arrayNode = mapper.valueToTree(catsId);
        message.put("cats", arrayNode);
        String reply = (String) template.convertSendAndReceive("owner", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public Owner findOwnerByID(Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findOwnerByID");
        message.put("id", id);
        String reply = (String) template.convertSendAndReceive("owner", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public ArrayList<Owner> getAllOwners() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "getAllOwners");
        String reply = (String) template.convertSendAndReceive("owner", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public List<Owner> findOwnersByName(String name) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findOwnersByName");
        message.put("name", name);
        String reply = (String) template.convertSendAndReceive("owner", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "loadUserByUsername");
        message.put("username", username);
        String reply = (String) template.convertSendAndReceive("owner", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public String saveUser(User1 user) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();

        message.put("command", "saveUser");
        message.put("id", user.getId());
        message.put("username", user.getUsername());
        message.put("password", user.getPassword());
        message.put("role", user.getRole().getName());

        ObjectNode owner = mapper.createObjectNode();

        owner.put("id", user.getOwner().getId());
        owner.put("name", user.getOwner().getName());
        List<Long> catsId = user.getOwner().getCats().stream().map(Cat::getId).collect(Collectors.toList());
        ArrayNode arrayNode = mapper.valueToTree(catsId);
        owner.put("cats", arrayNode);

        message.put("owner", owner);
        String reply = (String) template.convertSendAndReceive("owner", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public String deleteUser(Long ownerId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "deleteUser");
        message.put("ownerId", ownerId);
        String reply = (String) template.convertSendAndReceive("owner", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public String deleteCat(Long ownerId, Long catId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "deleteCat");
        message.put("ownerId", ownerId);
        message.put("catId", catId);
        String reply = (String) template.convertSendAndReceive("owner", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }

    public String addCat(Long ownerId, Long catId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "addCat");
        message.put("ownerId", ownerId);
        message.put("catId", catId);
        String reply = (String) template.convertSendAndReceive("owner", message.asText());
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
        throw new ExecutionControl.NotImplementedException("");
    }
}
