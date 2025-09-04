package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.Bd;
import io.permasoft.archihexa.pretdebd.domain.Bds;
import io.permasoft.archihexa.pretdebd.domain.Isbn;
import io.permasoft.archihexa.pretdebd.domain.Proprietaire;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BdServiceTest {
    // each test is run after creating a new instance of the test class, so instance variables are new for each tests.
    Repository repo = new Repository() {
        Bds bds = new Bds(List.of());

        @Override
        public void save(Bd bd) {
            bds = bds.add(bd);
        }

        @Override
        public Optional<Bd> load(UUID id) {
            return bds.byId(id);
        }
    };

    @Test
    void enregitre_une_bd_et_la_consulte() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Proprietaire proprietaire = new Proprietaire("Quentin");
        Isbn isbn = new Isbn("978-2012101333");
        //WHEN
        BdService bdService = new BdService(repo);
        bdService.enregistrer(id, isbn, proprietaire);
        //THEN
        Bd bd = bdService.byId(id)
                .orElseThrow(() -> new AssertionError("bd not found"));

        assertThat(new Bd(id, isbn, proprietaire, Bd.State.DISPONIBLE))
                .usingRecursiveComparison()
                .isEqualTo(bd);
    }
}
