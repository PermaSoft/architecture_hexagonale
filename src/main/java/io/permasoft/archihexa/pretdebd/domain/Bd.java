package io.permasoft.archihexa.pretdebd.domain;

import java.util.UUID;

public record Bd (UUID id, Isbn isbn, Proprietaire proprietaire, State disponible) {
    public enum State { DISPONIBLE, EMPRUNTE}
}
