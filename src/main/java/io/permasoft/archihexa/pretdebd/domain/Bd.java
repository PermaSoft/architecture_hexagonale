package io.permasoft.archihexa.pretdebd.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
@ToString
@Getter
public class Bd {
    private final UUID id;
    private final Isbn isbn;
    private final Proprietaire proprietaire;
    private State state;
    private String emprunteur;

    public enum State {DISPONIBLE, EMPRUNTE;}

    public Bd(@NonNull UUID id, @NonNull Isbn isbn, @NonNull Proprietaire proprietaire) {
        this(id, isbn, proprietaire, State.DISPONIBLE, null);
    }

    /**
     * Canonical constructor to create a Bd with a given state.
     * When loading from a DB.
     */
    //@org.springframework.data.annotation.PersistenceCreator <= makes spring data use this constructor
    public Bd(@NonNull UUID id, @NonNull Isbn isbn, @NonNull Proprietaire proprietaire, @NonNull State disponible, String emprunteur) {
        this.id = id;
        this.isbn = isbn;
        this.proprietaire = proprietaire;
        this.state = disponible;
        this.emprunteur = emprunteur;
    }

    public boolean empruntePar(@NonNull String emprunteur) {
        if (State.DISPONIBLE.equals(state)) {
            state = State.EMPRUNTE;
            this.emprunteur = emprunteur;
            return true;
        }
        return false;
    }
}
