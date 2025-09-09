package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class BdServiceTest {
    // each test is run after creating a new instance of the test class, so instance variables are new for each tests.
    Repository repo = new Repository() {
        Map<UUID, Bd> bds = new HashMap<>();
        Map<String, Emprunteur> emprunteurs = new HashMap<>();

        @Override
        public void save(Bd bd) {
            bds.put(bd.getId(), bd);
        }

        @Override
        public Optional<Bd> load(UUID id) {
            return bds.get(id) == null ? Optional.empty() : Optional.of(bds.get(id));
        }

        @Override
        public Emprunteur loadEmprunteur(String emprunteurId) {
            return emprunteurs.get(emprunteurId);
        }

        @Override
        public void save(Emprunteur emprunteur) {
            emprunteurs.put(emprunteur.getName(), emprunteur);
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

        assertThat(new Bd(id, isbn, proprietaire, Bd.State.DISPONIBLE, null))
                .usingRecursiveComparison()
                .isEqualTo(bd);
    }

    @Test
    void emprunte_une_bd_et_la_consulte() throws BookNotFoundExeption {
        // GIVEN
        UUID id = UUID.randomUUID();
        Proprietaire proprietaire = new Proprietaire("Quentin");
        Isbn isbn = new Isbn("978-2012101333");
        repo.save(new Bd(id, isbn, proprietaire, Bd.State.DISPONIBLE, null));
        Emprunteur emprunteur = new Emprunteur("Antoine", List.of());
        repo.save(emprunteur);
        //WHEN
        BdService bdService = new BdService(repo);
        assertThat(bdService.emprunte(id, emprunteur.getName())).isTrue();
        //THEN
        Bd bd = bdService.byId(id)
                .orElseThrow(() -> new AssertionError("bd not found"));

        assertThat(new Bd(id, isbn, proprietaire, Bd.State.EMPRUNTE, emprunteur.getName()))
                .usingRecursiveComparison()
                .isEqualTo(bd);
        assertThat(new Emprunteur("Antoine", List.of(id)))
                .usingRecursiveComparison()
                .isEqualTo(emprunteur);
    }

    @Test
    void emprunte_une_bd_deja_emprunter_est_refuse() throws BookNotFoundExeption {
        // GIVEN
        UUID id = UUID.randomUUID();
        Proprietaire proprietaire = new Proprietaire("Quentin");
        Isbn isbn = new Isbn("978-2012101333");
        Emprunteur premier = new Emprunteur("Pierre", List.of(id));
        Emprunteur deuxieme = new Emprunteur("Antoine", List.of());
        repo.save(premier);
        repo.save(deuxieme);
        Bd bd = new Bd(id, isbn, proprietaire, Bd.State.EMPRUNTE, premier.getName());
        repo.save(bd);
        //WHEN
        BdService bdService = new BdService(repo);
        assertThat(bdService.emprunte(id, deuxieme.getName())).isFalse();
        //THEN
        Bd bdActual = bdService.byId(id)
                .orElseThrow(() -> new AssertionError("bd not found"));
        Emprunteur deuxiemeActual = repo.loadEmprunteur(deuxieme.getName());

        assertThat(new Bd(id, isbn, proprietaire, Bd.State.EMPRUNTE, premier.getName()))
                .usingRecursiveComparison()
                .isEqualTo(bdActual);
        assertThat(new Emprunteur("Antoine", List.of()))
                .usingRecursiveComparison()
                .isEqualTo(deuxiemeActual);
    }
}
