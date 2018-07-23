package com.sample.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.models.User;

@RestController
public class UserController {
	
	private List<User> users;

    public UserController() {
    	users = new LinkedList<>();
    	users.add(new User(1, "Test1"));
    	users.add(new User(2, "Test2"));
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/json")     
    public User getUser(@PathVariable int id) {
    	System.out.println("HI "+id);
        Optional<User> user = users.stream().filter(user1 -> user1.getId() == id).findFirst();

        return user.get();
    }
    
}
