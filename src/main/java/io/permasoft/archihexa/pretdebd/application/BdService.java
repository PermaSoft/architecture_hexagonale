package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BdService {
    private final Repository repo;

    public BdService(Repository repo) {
        this.repo = repo;
    }

    public void enregistrer(UUID id, Isbn isbn, Proprietaire proprietaire) {
        repo.save(new Bd(id, isbn, proprietaire));
    }

    public Optional<Bd> byId(UUID id) {
        return repo.load(id);
    }

    public void emprunte(UUID id, String emprunteurId) throws BookNotFoundExeption {
        Bd bd = repo.load(id).orElseThrow(() -> new BookNotFoundExeption("id:"+id));
        Emprunteur emprunteur = repo.loadEmprunteur(emprunteurId);
        if (!repo.estDansLaClasse(emprunteurId)) {
            throw new RuntimeException("emprunteur n'est pas dans la classe");
        }
        if (!bd.empruntePar(emprunteurId)) {
            throw new RuntimeException("bd n'est pas empruntable");
        }
        if (!emprunteur.emprunte(bd.getId())) {
            throw new RuntimeException("emprunteur ne peut pas emprunter");
        }
        repo.save(emprunteur);
        repo.save(bd);
    }

    public void retourne(UUID bdId) throws BookNotFoundExeption {
        Bd bd = repo.load(bdId).orElseThrow(() -> new BookNotFoundExeption("id"));
        Emprunteur emprunteur = repo.loadEmprunteur(bd.getEmprunteur());
        if (!bd.retourne()) {
            throw new RuntimeException("bd n'est pas retournable");
        }
        if (!emprunteur.retourne(bdId)) {
            throw new RuntimeException("emprunteur ne peut pas retourner");
        }
        repo.save(bd);
        repo.save(emprunteur);
    }
}
