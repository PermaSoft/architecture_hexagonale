package io.permasoft.archihexa.pretdebd.infrastructure.sql;

import io.permasoft.archihexa.pretdebd.application.Repository;
import io.permasoft.archihexa.pretdebd.domain.Bd;
import io.permasoft.archihexa.pretdebd.domain.Classe;
import io.permasoft.archihexa.pretdebd.domain.Emprunteur;
import io.permasoft.archihexa.pretdebd.domain.Enfant;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MyMemeoryRepository implements Repository {
    Map<UUID, Bd> bds = new HashMap<>();
    Map<String, Emprunteur> emprunteurs = new HashMap<>();
    Classe classe = new Classe(
            new Enfant("Thomas"),
            new Enfant("Quentin"),
            new Enfant("Antoine"));

    @Override
    public void save(Bd bd) {
        bds.put(bd.getId(), bd);
        System.out.println("Save BD " + bd.getId()+" in "+bds.keySet());
    }

    @Override
    public Optional<Bd> load(UUID id) {
        System.out.println("Load BD " + id+" in "+bds.keySet());
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
}
