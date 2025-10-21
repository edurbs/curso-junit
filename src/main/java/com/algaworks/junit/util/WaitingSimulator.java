package com.algaworks.junit.util;

import java.time.Duration;

public class WaitingSimulator {

    private WaitingSimulator() {

    }

    public static void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (Exception ignored) {

        }
    }

}
