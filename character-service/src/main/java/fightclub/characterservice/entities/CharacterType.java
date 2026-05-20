package fightclub.characterservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "character_type")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class CharacterType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int strength;

    @Column(nullable = false)
    private int health;

    public CharacterType(String name, int strength, int health) {
        this.name = name;
        this.strength = strength;
        this.health = health;
    }
}
