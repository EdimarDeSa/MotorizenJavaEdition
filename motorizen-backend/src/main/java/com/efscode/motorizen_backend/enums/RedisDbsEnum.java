package com.efscode.motorizen_backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RedisDbsEnum {
    USERS(0),
    TOKENS(1),
    VEHICLES(2),
    REGISTERS(3),
    BRANDS(4),
    REPORTS(5),
    SESSIONS(6),
    FUEL_TYPES(7),
    SYNC_INITIAL(8),
    SYNC_UPDATES(9);

    private int index;
}
