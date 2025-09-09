package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.Bd;
import io.permasoft.archihexa.pretdebd.domain.Isbn;
import io.permasoft.archihexa.pretdebd.domain.Proprietaire;
import org.junit.jupiter.api.AssertionFailureBuilder;

import java.util.UUID;

public class BdMother {
    public static Bd.BdBuilder aBook() {
        return Bd.builder() //new Bd(id, isbn, proprietaire, Bd.State.DISPONIBLE, null);
                .id(UUID.randomUUID())
                .proprietaire(new Proprietaire("Quentin"))
                .isbn(new Isbn("978-2012101333"))
                .state(Bd.State.DISPONIBLE)
                .emprunteur(null);
    }
}
