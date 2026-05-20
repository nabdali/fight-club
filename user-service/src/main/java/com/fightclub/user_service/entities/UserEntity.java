package com.fightclub.user_service.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String pseudo;

    private String password;

    @Column(name = "victory_counter")
    private Integer victoryCounter;

    @Column(name = "defeat_counter")
    private Integer defeatCounter;

}