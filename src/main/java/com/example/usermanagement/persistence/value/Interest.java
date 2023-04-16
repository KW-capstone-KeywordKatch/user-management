package com.example.usermanagement.persistence.value;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 사용자의 관심 분야
 */
@Deprecated
public enum Interest {

    EMPTY(-1),
    ENTERTAIN(0),
    NATURAL_DISASTER(1),
    TRAFFIC_ACCIDENT(2),
    POLITICS(3),
    ECONOMIC(4),
    CRIME(5),
    SCIENCE_TECH(6);

    private final int value;

    private Interest(int value) {
        this.value = value;
    }
}
