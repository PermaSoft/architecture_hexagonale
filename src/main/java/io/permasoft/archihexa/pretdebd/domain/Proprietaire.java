package io.permasoft.archihexa.pretdebd.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Objects;

@EqualsAndHashCode(of = "name")
@ToString
@Getter
public final class Proprietaire extends Enfant {
    public Proprietaire(@NonNull String name) {
        super(name);
    }
}
