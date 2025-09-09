package io.permasoft.archihexa.pretdebd.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode(of = "name")
@ToString
@Getter
public class Enfant {
    private String name;
    public Enfant(@NonNull String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Nom invalide : " + name);
        }
        this.name = name;
    }
}
