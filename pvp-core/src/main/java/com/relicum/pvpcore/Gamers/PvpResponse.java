package com.relicum.pvpcore.Gamers;

/**
 * PvpResponse is used to return a response by a method indicating its outcome.
 * <p>You can also set a message giving more specific details on the response.
 * <p>Null is accepted for the message where just a simple true or false is required.
 *
 * @author Relicum
 * @version 0.0.1
 */
public class PvpResponse {

    private final PvpResponse.ResponseType type;
    private final String message;

    /**
     * Instantiates a new PvpResponse.
     *
     * @param type    the outcome of the method
     * @param message the message used to provided more detail if needed, null is accepted.
     */
    public PvpResponse(ResponseType type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Response of the method outcome.
     *
     * @return true represents success, false represents failure.
     */
    public boolean response() {

        switch (this.type.getId()) {
            case 1: {
                return true;
            }
            default:
                return false;
        }
    }

    /**
     * Gets message containing any further details on the response.
     * <p>If null was set this will return an empty string.
     *
     * @return the message
     */
    public String getMessage() {

        if (message != null) {
            return message;
        }

        return "";
    }

    public enum ResponseType {

        SUCCESS(1),
        FAILURE(2);


        private int id;

        ResponseType(int id) {

            this.id = id;
        }

        int getId() {

            return this.id;
        }

    }
}
