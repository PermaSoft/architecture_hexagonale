package io.permasoft.archihexa.pretdebd.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class EmprunteurTest {

    @Test
    void emprunter_un_livre() {
        Emprunteur emprunteur = new Emprunteur("Quentin", null);
        UUID livreId = UUID.randomUUID();
        assertThat(emprunteur.emprunte(livreId)).isTrue();
        assertThat(emprunteur.getBdsEmpruntees())
                .containsExactlyInAnyOrder(livreId);
    }

    @Test
    void emprunter_plusieurs_livres() {
        UUID livreId1 = UUID.randomUUID();
        Emprunteur emprunteur = new Emprunteur("Quentin", List.of(livreId1));
        UUID livreId2 = UUID.randomUUID();
        assertThat(emprunteur.emprunte(livreId2)).isTrue();
        assertThat(emprunteur.getBdsEmpruntees())
                .containsExactlyInAnyOrder(livreId1, livreId2);
    }

    @Test
    void emprunter_3_livres_maximum() {
        UUID livreId1 = UUID.randomUUID();
        UUID livreId2 = UUID.randomUUID();
        UUID livreId3 = UUID.randomUUID();
        Emprunteur emprunteur = new Emprunteur("Quentin", List.of(livreId1, livreId2, livreId3));
        UUID livreId4 = UUID.randomUUID();
        assertThat(emprunteur.emprunte(livreId4)).isFalse();
        assertThat(emprunteur.getBdsEmpruntees())
                .containsExactlyInAnyOrder(livreId1, livreId2, livreId3);
    }
}
