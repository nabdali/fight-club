package com.fightclub.character_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "character_type")
public class CharacterType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int strength;

    @Column(nullable = false)
    private int health;

    public CharacterType() {}

    public CharacterType(String name, int strength, int health) {
        this.name = name;
        this.strength = strength;
        this.health = health;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public int getStrength() { return strength; }
    public int getHealth() { return health; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setStrength(int strength) { this.strength = strength; }
    public void setHealth(int health) { this.health = health; }
}
