package io.permasoft.archihexa.pretdebd.domain;

import java.util.UUID;

/**
 * records are exposed data structures without encapsulation and shows state here that is noise from a caller point of view.
 */
public record Bd (UUID id, Isbn isbn, Proprietaire proprietaire, State disponible) {
    public enum State { DISPONIBLE, EMPRUNTE;}

    /**
     * Auxiliary constructor to create a Bd with default state DISPONIBLE.
     * When loading a new bd from user.
     */
    public Bd(UUID id, Isbn isbn, Proprietaire proprietaire) {
        this(id, isbn, proprietaire, State.DISPONIBLE);
    }

    /**
     * Canonical constructor to create a Bd with a given state.
     * When loading from a DB.
     */
    //@org.springframework.data.annotation.PersistenceCreator <= makes spring data use this constructor
    public Bd(UUID id, Isbn isbn, Proprietaire proprietaire, State disponible) {
        this.id = id;
        this.isbn = isbn;
        this.proprietaire = proprietaire;
        this.disponible = disponible;
    }
}
