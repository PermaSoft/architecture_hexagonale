package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.Bd;
import io.permasoft.archihexa.pretdebd.domain.Isbn;
import io.permasoft.archihexa.pretdebd.domain.Proprietaire;

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
}
