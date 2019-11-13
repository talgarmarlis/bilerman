package com.artatech.bilerman.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String definition;

    private String body;

    public Post(String name, String definition, String body){
        this.name = name;
        this.definition = definition;
        this.body = body;
    }
}
