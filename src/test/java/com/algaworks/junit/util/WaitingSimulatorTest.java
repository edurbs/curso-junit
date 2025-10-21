package com.algaworks.junit.util;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

class WaitingSimulatorTest {

    @Test
        //@Disabled("Not needed anymore.")
    void whenExecuteWait_thenFinishUntilOneSecond() {
        //Assumptions.assumeTrue("PROD".equals(System.getenv("ENV")),() -> "Tests aborted: should not be executed in production mode." );
        assertTimeout(Duration.ofSeconds(2), () -> WaitingSimulator.wait(Duration.ofSeconds(1)));
    }

    @Test
        //@EnabledIfEnvironmentVariable(named = "ENV", matches = "DEV")
    void whenExecuteWait_thenFinishUntilOneSecond2() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> WaitingSimulator.wait(Duration.ofSeconds(1)));
    }

}