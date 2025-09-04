package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.Bd;

import java.util.UUID;

public interface Repository {
     void save(Bd bd);
     Bd load(UUID id);

}
