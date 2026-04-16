package fightclub.characterservice;

import org.springframework.boot.SpringApplication;

public class TestCharacterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(CharacterServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
