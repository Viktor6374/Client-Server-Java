package com.example.externalinterfaces.Services;

import com.example.externalinterfaces.Entity.Cat;
import com.example.externalinterfaces.Entity.Owner;
import com.example.externalinterfaces.Entity.Role;
import com.example.externalinterfaces.Entity.User1;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OwnerService implements UserDetailsService {
    @Autowired
    AmqpTemplate template;
    @Autowired
    CatService catService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public String changeOwner(Owner owner) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "changeOwner");
        message.put("id", owner.getId());
        message.put("name", owner.getName());
        List<Long> catsId = owner.getCats().stream().map(Cat::getId).collect(Collectors.toList());
        ArrayNode arrayNode = mapper.valueToTree(catsId);
        message.put("cats", arrayNode);
        String reply = (String) template.convertSendAndReceive("owner", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return jsonReply.get("message").textValue();
    }

    public Owner findOwnerByID(Long id) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findOwnerByID");
        message.put("id", id);
        String reply = (String) template.convertSendAndReceive("owner", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return parseOwnerReply(jsonReply.get("message"));
    }

    public List<Owner> getAllOwners() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "getAllOwners");
        String reply = (String) template.convertSendAndReceive("owner", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return parseArrayOwnerReply(jsonReply.get("message"));
    }

    public List<Owner> findOwnersByName(String name) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "findOwnersByName");
        message.put("name", name);
        String reply = (String) template.convertSendAndReceive("owner", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return parseArrayOwnerReply(jsonReply.get("message"));
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "loadUserByUsername");
        message.put("username", username);
        try {
            String reply = (String) template.convertSendAndReceive("owner", mapper.writeValueAsString(message));
            ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);
            if (Objects.equals(jsonReply.get("result").textValue(), "error")) {
                throw new Exception(jsonReply.get("message").textValue());
            }

            return parseUserReply(jsonReply.get("message"));
        } catch (Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public String saveUser(User1 user) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();

        message.put("command", "saveUser");
        message.put("id", user.getId());
        message.put("username", user.getUsername());
        message.put("password", bCryptPasswordEncoder.encode(user.getPassword()));
        message.put("role", user.getRole().getName());

        ObjectNode owner = mapper.createObjectNode();

        owner.put("id", user.getOwner().getId());
        owner.put("name", user.getOwner().getName());
        List<Long> catsId = user.getOwner().getCats().stream().map(Cat::getId).collect(Collectors.toList());
        ArrayNode arrayNode = mapper.valueToTree(catsId);
        owner.put("cats", arrayNode);

        message.put("owner", owner);
        String reply = (String) template.convertSendAndReceive("owner", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }

        return jsonReply.get("message").textValue();
    }

    public void deleteUser(Long ownerId) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "deleteUser");
        message.put("ownerId", ownerId);
        String reply = (String) template.convertSendAndReceive("owner", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }
    }

    public void deleteCat(Long ownerId, Long catId) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "deleteCat");
        message.put("ownerId", ownerId);
        message.put("catId", catId);
        String reply = (String) template.convertSendAndReceive("owner", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }
    }

    public void addCat(Long ownerId, Long catId) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode message = mapper.createObjectNode();
        message.put("command", "addCat");
        message.put("ownerId", ownerId);
        message.put("catId", catId);
        String reply = (String) template.convertSendAndReceive("owner", mapper.writeValueAsString(message));
        ObjectNode jsonReply = (ObjectNode) new ObjectMapper().readTree(reply);

        if (Objects.equals(jsonReply.get("result").textValue(), "error")){
            throw new Exception(jsonReply.get("message").textValue());
        }
    }

    private Owner parseOwnerReply(JsonNode owner_) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode owner = (ObjectNode) owner_;
        Long id = owner.get("id").asLong();
        String name = owner.get("name").textValue();
        List<Cat> cats = catService.findCatByOwnerId(id);

        return new Owner(id, name, cats);
    }

    private List<Owner> parseArrayOwnerReply(JsonNode arrayNode) throws Exception {
        List<Owner> result = new ArrayList<>();
        if (arrayNode == null){
            return result;
        }
        for (JsonNode owner : arrayNode){
            result.add(parseOwnerReply(owner));
        }

        return result;
    }

    private User1 parseUserReply(JsonNode user_) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode user = (ObjectNode) user_;
        Long id = user.get("id").asLong();
        String username = user.get("username").textValue();
        String password = user.get("password").textValue();
        Role role = new Role(user.get("role").textValue());
        Owner owner = parseOwnerReply(user.get("owner"));

        return new User1(id, password, username, owner, role);
    }
}
