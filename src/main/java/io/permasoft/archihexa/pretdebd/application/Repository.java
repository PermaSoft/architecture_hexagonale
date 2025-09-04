package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.Bd;

import java.util.Optional;
import java.util.UUID;

public interface Repository {
     void save(Bd bd);
     Optional<Bd> load(UUID id);

}
