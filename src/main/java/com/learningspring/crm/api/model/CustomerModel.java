package com.learningspring.crm.api.model;

import javax.validation.constraints.*;

public class CustomerModel {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String lastName;


    @NotNull
    @NotEmpty
    @Size(max = 50)
    @Email
    private String email;

    public CustomerModel() {

    }

    public CustomerModel(Long id,
                         @NotNull @NotEmpty @Size(max = 50) String firstName,
                         @NotNull @NotEmpty @Size(max = 50) String lastName,
                         @NotNull @NotEmpty @Size(max = 50) @Email String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
