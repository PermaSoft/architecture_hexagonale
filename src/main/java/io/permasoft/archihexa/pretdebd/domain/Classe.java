package io.permasoft.archihexa.pretdebd.domain;

import java.util.HashSet;
import java.util.Set;

public class Classe {
    Set<Enfant> etudiant = new HashSet<>();

    public Classe(Enfant... enfants) {
        etudiant.addAll(Set.of(enfants));
    }

    public boolean aPourEleve(String name) {
        return etudiant.stream()
                .anyMatch(e -> e.getName().equals(name));
    }
}
