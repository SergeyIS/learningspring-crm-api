package com.learningspring.crm.api.controller;

import com.learningspring.crm.api.exception.BadRequestApiException;
import com.learningspring.crm.api.model.UserModel;
import com.learningspring.crm.data.DataStorage;
import com.learningspring.crm.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;

@RestController
@RequestMapping("/api")
public class UserApiController {

    private final DataStorage db;
    private final Validator validator;

    @Autowired
    public UserApiController(DataStorage db, Validator validator) {
        this.db = db;
        this.validator = validator;
    }

    @PostMapping(value = {"/1.0/user/sign-up", "/2.0/user/sign-up"})
    public UserModel signUp(@RequestBody UserModel model) {
        if(!validator.validate(model).isEmpty()) {
            throw new BadRequestApiException();
        }

        User user = new User(model.getUsername(), model.getPassword(), DEFAULT_USER_AUTHORITY);
        Long id = db.putUser(user);

        model.setPassword("****");
        model.setId(id);

        return model;
    }

    private static final String DEFAULT_USER_AUTHORITY = "ROLE_USER";
}
