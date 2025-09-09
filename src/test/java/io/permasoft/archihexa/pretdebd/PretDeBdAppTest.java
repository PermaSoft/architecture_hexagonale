package io.permasoft.archihexa.pretdebd;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class PretDeBdAppTest {
    @Test
    void shouldPass() {
        new PretDeBdApp(false);
        assertThat(true).isTrue();
    }

    @Test
    @Disabled
    void shouldFail() {
        assertThat(false).isTrue();
    }

    @Test
    void shouldRun() {
        assertThatCode(() -> new PretDeBdApp(false).run("Hello", "World")).doesNotThrowAnyException();
    }
}
