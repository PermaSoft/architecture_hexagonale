package io.permasoft.archihexa.pretdebd.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class BdTest {

    @Test
    void invariantsBD() {
        assertThatCode(() -> new Bd(null, null, null)).isInstanceOf(NullPointerException.class);
        assertThatCode(() -> new Bd(UUID.randomUUID(), null, new Proprietaire("Quentin"))).isInstanceOf(NullPointerException.class);
        assertThatCode(() -> new Bd(null, new Isbn("978-2012101333"), new Proprietaire("Quentin"))).isInstanceOf(NullPointerException.class);
    }

    @Test
    void valideBD() {
        Proprietaire proprietaire = new Proprietaire("Quentin");
        Isbn isbn = new Isbn("978-2012101333");
        UUID id = UUID.randomUUID();

        Bd bd = new Bd(id, isbn, proprietaire);
        Bd expected = new Bd(id, isbn, proprietaire, Bd.State.DISPONIBLE, null);
        Bd failed = new Bd(id, isbn, proprietaire, Bd.State.EMPRUNTE, "anonymous");
        Assertions.assertAll(
                () -> assertThat(bd).as("correct field by field test").usingRecursiveComparison().isEqualTo(expected),
                () -> assertThat(bd).as("validate preceding test check all fields").usingRecursiveComparison().isNotEqualTo(failed),
                () -> assertThat(bd).as("validated that equal is on id only").isEqualTo(failed)
        );

    }

    @Test
    void emprunte_un_bd() {
        Proprietaire proprietaire = new Proprietaire("Quentin");
        Isbn isbn = new Isbn("978-2012101333");
        UUID id = UUID.randomUUID();
        Bd bd = new Bd(id, isbn, proprietaire);
        assertThat(bd.empruntePar("Antoine")).isTrue();
        assertThat(bd.getState()).isEqualTo(Bd.State.EMPRUNTE);
        // retrieve emprunteur to check it has the borrowed book in his list
    }

    @Test
    void emprunte_un_bd_deja_emprunte_est_refusé() {
        Proprietaire proprietaire = new Proprietaire("Quentin");
        Isbn isbn = new Isbn("978-2012101333");
        UUID id = UUID.randomUUID();
        Bd bd = new Bd(id, isbn, proprietaire);
        assertThat(bd.empruntePar("Antoine")).isTrue();
        assertThat(bd.empruntePar("Pierre")).isFalse();
        // retrieve emprunteur to check it has the borrowed book in his list
    }

}
