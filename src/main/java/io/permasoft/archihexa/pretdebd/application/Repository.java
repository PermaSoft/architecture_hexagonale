package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.Bd;
import io.permasoft.archihexa.pretdebd.domain.Emprunteur;

import java.util.Optional;
import java.util.UUID;

public interface Repository {
     Optional<Bd> load(UUID id);
     void save(Bd bd);

    Emprunteur loadEmprunteur(String emprunteurId);
    void save(Emprunteur emprunteur);

}
