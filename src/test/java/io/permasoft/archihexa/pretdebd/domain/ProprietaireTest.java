package io.permasoft.archihexa.pretdebd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ProprietaireTest {

    @Test
    void nullProprietaire() {
        assertThatCode(() -> new Proprietaire(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void invalideProprietaire() {
        assertThatCode(() -> new Proprietaire("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void valideProprietaire() {
        assertThat(new Proprietaire("Quentin"))
                .extracting(Proprietaire::name)
                .isEqualTo("Quentin");
    }

}
