package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BdServiceTest {
    // each test is run after creating a new instance of the test class, so instance variables are new for each tests.
    Repository repo = new Repository() {
        Map<UUID, Bd> bds = new HashMap<>();
        Map<String, Emprunteur> emprunteurs = new HashMap<>();
        Classe classe = new Classe(
                new Enfant("Thomas"),
                new Enfant("Quentin"),
                new Enfant("Antoine"));

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

        @Override
        public boolean estDansLaClasse(String name) {
            return classe.aPourEleve(name);
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
        Bd bdOrigine = BdMother.aBook().build();
        repo.save(bdOrigine);
        Emprunteur emprunteur = new Emprunteur("Antoine", List.of());
        repo.save(emprunteur);
        //WHEN
        BdService bdService = new BdService(repo);
        bdService.emprunte(bdOrigine.getId(), emprunteur.getName());
        //THEN
        Bd bd = bdService.byId(bdOrigine.getId())
                .orElseThrow(() -> new AssertionError("bd not found"));

        assertThat(Bd.State.EMPRUNTE)
                .isEqualTo(bd.getState());
        assertThat(emprunteur.getName())
                .isEqualTo(bd.getEmprunteur());
        assertThat(new Emprunteur("Antoine", List.of(bdOrigine.getId())))
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
        assertThatThrownBy(() -> bdService.emprunte(id, deuxieme.getName()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("bd n'est pas empruntable");
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

    @Test
    void emprunte_une_bd_hors_classe_est_refuse() throws BookNotFoundExeption {
        // GIVEN
        UUID id = UUID.randomUUID();
        Proprietaire proprietaire = new Proprietaire("Quentin");
        Isbn isbn = new Isbn("978-2012101333");
        Emprunteur emprunteur = new Emprunteur("Nicolas_hors_classe", List.of());
        repo.save(emprunteur);
        Bd bd = new Bd(id, isbn, proprietaire, Bd.State.DISPONIBLE, null);
        repo.save(bd);
        //WHEN
        BdService bdService = new BdService(repo);
        assertThatThrownBy(() -> bdService.emprunte(id, emprunteur.getName()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("emprunteur n'est pas dans la classe");
        //THEN
        Bd bdAttendue = bdService.byId(id)
                .orElseThrow(() -> new AssertionError("bd not found"));
        Emprunteur emprunteurAttendu = repo.loadEmprunteur(emprunteur.getName());

        assertThat(new Bd(id, isbn, proprietaire, Bd.State.DISPONIBLE, null))
                .usingRecursiveComparison()
                .isEqualTo(bdAttendue);
        assertThat(new Emprunteur("Nicolas_hors_classe", List.of()))
                .usingRecursiveComparison()
                .isEqualTo(emprunteurAttendu);
    }
    @Test
    void retourne_une_bd_empruntee() throws BookNotFoundExeption {
        Bd bdEmpruntee = BdMother.aBook()
                .emprunteur("Antoine")
                .state(Bd.State.EMPRUNTE)
                .build();
        repo.save(bdEmpruntee);
        repo.save(new Emprunteur("Antoine", List.of(bdEmpruntee.getId())));
        BdService bdService = new BdService(repo);
        bdService.retourne(bdEmpruntee.getId());

        var bdAttendue = repo.load(bdEmpruntee.getId()).get();
        assertThat(bdAttendue.getState()).isEqualTo(Bd.State.DISPONIBLE);
        assertThat(bdAttendue.getEmprunteur()).isNull();
        var emprunteurAttendu = repo.loadEmprunteur("Antoine");
        assertThat(emprunteurAttendu.getBdsEmpruntees()).doesNotContain(bdEmpruntee.getId());
    }
}
