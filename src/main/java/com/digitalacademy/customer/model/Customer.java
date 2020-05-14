package com.digitalacademy.customer.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Customer {

//    public Customer(@NotNull @Size(min = 1, max = 100, message = "Please type your first name size between 1-100") String firstName, @NotNull @Size(min = 1, max = 100, message = "Please type your first name size between 1-100") String lastName, @Email(message = "Please type valid email") String email, @NotNull String phoneNumber, Integer age) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.age = age;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100, message = "Please type your first name size between 1-100")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 100, message = "Please type your first name size between 1-100")
    private String lastName;

    @Email(message = "Please type valid email")
    private String email;

    @NotNull
    private String phoneNumber;

    private Integer age;

}
