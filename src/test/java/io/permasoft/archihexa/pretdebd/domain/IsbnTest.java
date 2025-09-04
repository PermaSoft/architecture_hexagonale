package io.permasoft.archihexa.pretdebd.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class IsbnTest {

    @Test
    void nullIsbn() {
        assertThatCode(() -> new Isbn(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void invalideIsbn() {
        assertThatCode(() -> new Isbn("")).isInstanceOf(IllegalArgumentException.class);
        assertThatCode(() -> new Isbn("invalide")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void valideIsbn() {
        assertThat(new Isbn("978-2012101333")).extracting(Isbn::isbn).isEqualTo("978-2012101333");
    }

}
