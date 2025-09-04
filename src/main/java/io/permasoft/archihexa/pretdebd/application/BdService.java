package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.Bd;
import io.permasoft.archihexa.pretdebd.domain.Isbn;
import io.permasoft.archihexa.pretdebd.domain.Proprietaire;

import java.util.UUID;

public class BdService {
    public BdService(Repository repo) {
    }

    public void enregistrer(UUID id, Isbn isbn, Proprietaire proprietaire) {

    }

    public Bd byId(UUID id) {
        return null;
    }
}
