package io.permasoft.archihexa.pretdebd;

import io.quarkus.runtime.Application;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class PretDeBdApp extends Application {
    protected PretDeBdApp(boolean auxiliaryApplication) {
        super(auxiliaryApplication);
    }

    public void run(String... args) {
        System.out.println(Arrays.stream(args).collect(joining(" ")));
    }

    @Override
    protected void doStart(String[] args) {
        System.out.println("Hello Quarkus");
    }

    @Override
    protected void doStop() {
        System.out.println("Bye Quarkus");

    }

    @Override
    public String getName() {
        return "EmprunteBd";
    }
}
