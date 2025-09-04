package io.permasoft.archihexa.pretdebd.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
@ToString
@Getter
public class Bd  {
    private final UUID id;
    private final Isbn isbn;
    private final Proprietaire proprietaire;
    private final State state;

    public enum State { DISPONIBLE, EMPRUNTE;}

    public Bd(@NonNull UUID id, @NonNull Isbn isbn, @NonNull Proprietaire proprietaire) {
        this(id, isbn, proprietaire, State.DISPONIBLE);
    }

    /**
     * Canonical constructor to create a Bd with a given state.
     * When loading from a DB.
     */
    //@org.springframework.data.annotation.PersistenceCreator <= makes spring data use this constructor
    public Bd(@NonNull UUID id, @NonNull Isbn isbn, @NonNull Proprietaire proprietaire, @NonNull State disponible) {
        this.id = id;
        this.isbn = isbn;
        this.proprietaire = proprietaire;
        this.state = disponible;
    }
}
