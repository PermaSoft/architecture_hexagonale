package io.permasoft.archihexa.pretdebd.domain;

import io.netty.util.internal.StringUtil;
import lombok.NonNull;

public record Isbn(@NonNull String isbn) {
    private static String isbnRegEx = "(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+";

    public Isbn {
        if (!isbn.matches(isbnRegEx)) {
                throw new IllegalArgumentException("Isbn invalide : " + isbn );
        }
    }
}
