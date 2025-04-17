package org.example.psychologicalcounseling.utils;

import java.util.Optional;

public class TimeStampUtil {

    static public Optional<String> timestampBetweenCheck(Long startTimestamp, Long endTimestamp) {
        // check if startTime and endTime are null
        if (startTimestamp == null || endTimestamp == null) {
            return Optional.of("timestamp can't be null");
        }

        // check if startTime and endTime are negative
        if (startTimestamp < 0 || endTimestamp < 0) {
            return Optional.of("timestamp can't be negative");
        }

        // check if startTime and endTime are in the future
        if (startTimestamp > endTimestamp) {
            return Optional.of("startTime can't be greater than endTime");
        }

        return Optional.empty();
    }

    static public void initialTimestamp(Long startTimestamp, Long endTimestamp) {
        if (startTimestamp == null) {
            startTimestamp = 0L;
        }
        if (endTimestamp == null) {
            endTimestamp = System.currentTimeMillis();
        }
    }
}
