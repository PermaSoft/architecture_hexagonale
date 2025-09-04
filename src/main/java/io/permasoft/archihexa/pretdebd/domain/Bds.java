package io.permasoft.archihexa.pretdebd.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Bds {
    private final List<Bd> initialBds;

    public Bds(List<Bd> initialBds) {
        this.initialBds = initialBds;
    }

    public Bds add(Bd bd) {
        var actualList = new ArrayList<>(initialBds);
        actualList.add(bd);
        return new Bds(actualList);
    }

    public Optional<Bd> byId(UUID id) {
        return initialBds.stream()
                .filter(bd -> bd.id().equals(id))
                .findFirst();
    }
}
