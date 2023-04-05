package com.example.cloud_storage.cotroller;

import com.example.cloud_storage.model.User;
import com.example.cloud_storage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        User saveUser = userRepository.save(user);
        URI userURI = URI.create("/user/" + saveUser.getUsername());
        return ResponseEntity.created(userURI).body(saveUser);
    }

    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }
}
