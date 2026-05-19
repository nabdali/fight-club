package com.fightclub.character_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "character",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "character_type_id"}))
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "character_type_id", nullable = false)
    private CharacterType characterType;

    @Column(nullable = false)
    private int level = 1;

    @Column(nullable = false)
    private int experience = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Character() {}

    public Character(String name, Long userId, CharacterType characterType) {
        this.name = name;
        this.userId = userId;
        this.characterType = characterType;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getUserId() { return userId; }
    public CharacterType getCharacterType() { return characterType; }
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setCharacterType(CharacterType characterType) { this.characterType = characterType; }
    public void setLevel(int level) { this.level = level; }
    public void setExperience(int experience) { this.experience = experience; }
}
