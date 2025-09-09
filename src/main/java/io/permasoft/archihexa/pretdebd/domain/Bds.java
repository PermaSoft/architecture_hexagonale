package io.permasoft.archihexa.pretdebd.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Bds {
    private final List<Bd> initialBds = new ArrayList<>();

    public Bds(List<Bd> initialBds) {
        this.initialBds.addAll(initialBds);
    }

    public boolean add(Bd bd) {
        return initialBds.add(bd);
    }

    public Optional<Bd> byId(UUID id) {
        return initialBds.stream()
                .filter(bd -> bd.getId().equals(id))
                .findFirst();
    }
}
