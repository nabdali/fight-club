package com.fight_club.arena_service.service;

import com.fight_club.arena_service.dto.CharacterDTO;
import org.springframework.stereotype.Component;

/**
 * Resolves fight outcomes using type advantages and simulated rounds.
 *
 * Type cycle: Assassin > Mage > Tank > Archer > Assassin
 * Adjacent pairs: advantage ×1.5 / disadvantage ×0.75
 * Non-adjacent pairs (Assassin/Tank, Mage/Archer): neutral ×1.0
 *
 * Resolution: winner = fewest rounds to kill opponent.
 * Tie-breaker: most health remaining before the final round.
 */
@Component
public class CombatResolver {

    private static final double ADVANTAGE    = 1.5;
    private static final double NEUTRAL      = 1.0;
    private static final double DISADVANTAGE = 0.75;

    public Long resolve(Long id1, CharacterDTO char1, Long id2, CharacterDTO char2) {
        double damage1 = char1.type().strength() * multiplier(char1.type().name(), char2.type().name());
        double damage2 = char2.type().strength() * multiplier(char2.type().name(), char1.type().name());

        int roundsFor1 = (int) Math.ceil(char2.type().health() / damage1);
        int roundsFor2 = (int) Math.ceil(char1.type().health() / damage2);

        if (roundsFor1 < roundsFor2) return id1;
        if (roundsFor2 < roundsFor1) return id2;

        // Same number of rounds: most health remaining going into the final round wins
        double char1Remaining = char1.type().health() - (roundsFor1 - 1) * damage2;
        double char2Remaining = char2.type().health() - (roundsFor1 - 1) * damage1;
        return char1Remaining >= char2Remaining ? id1 : id2;
    }

    private double multiplier(String attacker, String defender) {
        if (attacker.equals(defender)) return NEUTRAL;
        return switch (attacker) {
            case "Assassin" -> switch (defender) {
                case "Mage"   -> ADVANTAGE;
                case "Archer" -> DISADVANTAGE;
                default       -> NEUTRAL;
            };
            case "Mage" -> switch (defender) {
                case "Tank"     -> ADVANTAGE;
                case "Assassin" -> DISADVANTAGE;
                default         -> NEUTRAL;
            };
            case "Tank" -> switch (defender) {
                case "Archer" -> ADVANTAGE;
                case "Mage"   -> DISADVANTAGE;
                default       -> NEUTRAL;
            };
            case "Archer" -> switch (defender) {
                case "Assassin" -> ADVANTAGE;
                case "Tank"     -> DISADVANTAGE;
                default         -> NEUTRAL;
            };
            default -> NEUTRAL;
        };
    }
}
