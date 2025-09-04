package io.permasoft.archihexa.pretdebd.domain;

public record Bd (Isbn isbn, Proprietaire proprietaire, State disponible) {
    public enum State { DISPONIBLE, EMPRUNTE}
}
