package com.relicum.pvpcore.Enums;

/**
 * Symbols stores a collection of UNICODE sysmbols that can be displayed in Minecraft.
 *
 * @author Relicum
 * @version 0.0.1
 */
public enum Symbols {

    WHITE_HEART('\u2661'),
    BLACK_HEART('\u2665'),
    ROTATED_HEART('\u2765'),
    TICK('\u2714'),
    CROSS('\u2718'),
    PLANE('\u2708'),
    ENVOLOPE('\u2709'),
    QUESTION_MARK('\u2753'),
    EXCLAMATION_MARK('\u2757'),
    VOTE('\u270D'),
    FEATHERED_ARROW('\u27B3'),
    CIRCLE_STAR('\u272A'),
    FLOWER('\u2698'),
    ASTERISK('\u2724'),
    SCISSORS('\u2704'),
    TELEPHONE('\u260E'),
    COFFEE('\u2615'),
    SNOWFLAKE('\u2744'),
    YIN_YANG('\u262D'),
    RADIOACTIVE('\u2622'),
    FLORETTE('\u2740');


    private final char symbol;

    Symbols(char symbol) {

        this.symbol = symbol;
    }

    /**
     * Get symbol as a char.
     *
     * @return the unicode char of the symbol
     */
    public char toChar() {

        return symbol;
    }

    /**
     * Get symbol as string.
     *
     * @return the unicode string of the symbol
     */
    public String asString() {

        return String.valueOf(symbol);
    }
}
