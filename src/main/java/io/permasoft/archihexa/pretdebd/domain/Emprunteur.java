package io.permasoft.archihexa.pretdebd.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.*;

@ToString
@EqualsAndHashCode(of = "name")
@Getter
public final class Emprunteur {
    public static final int MAX_BORROWED_BOOKS = 3;
    private final @NonNull String name;
    private final Set<UUID> bdsEmpruntees = new TreeSet<>();

    public Emprunteur(@NonNull String name, List<UUID> bdsEmpruntees) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Nom invalide : " + name);
        }
        this.name = name;
        if (bdsEmpruntees != null) {
            this.bdsEmpruntees.addAll(bdsEmpruntees);
            if (this.bdsEmpruntees.size() > 3) {
                throw new IllegalArgumentException("Nombre de livres empruntés maximum dépassé : " + bdsEmpruntees.size());
            }
        }
    }

    public boolean emprunte(UUID id) {
        if (bdsEmpruntees.size() < MAX_BORROWED_BOOKS) {
            if (bdsEmpruntees.add(id)) {
                return true;
            }
        }
        return false;
    }

}
