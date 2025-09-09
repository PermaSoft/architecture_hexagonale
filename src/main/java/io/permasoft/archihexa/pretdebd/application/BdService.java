package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.*;

import java.util.Optional;
import java.util.UUID;

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

    public boolean emprunte(UUID id, String emprunteurId) throws BookNotFoundExeption {
        // tx begin
        Bd bd = repo.load(id).orElseThrow(() -> new BookNotFoundExeption("id"));
        Emprunteur emprunteur = repo.loadEmprunteur(emprunteurId);
        if (bd.empruntePar(emprunteurId)) {
            if (emprunteur.emprunte(bd.getId())) {
                repo.save(emprunteur);
                repo.save(bd);
                // tx commit
                return true;
            }
        }
        // tx rollback
        return false;
    }
}
