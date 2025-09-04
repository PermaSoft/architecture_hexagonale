package io.permasoft.archihexa.pretdebd.domain;

import lombok.NonNull;

public record Proprietaire(@NonNull String name) {
    public Proprietaire {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Nom invalide : " + name);
        }
    }
}
