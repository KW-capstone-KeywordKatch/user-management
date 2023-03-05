package com.example.usermanagement.persistence.value;

/**
 * 사용자 권한
 */
public enum Role {
    ADMIN("ADMIN");

    private String value;

    private Role(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.value;
    }
}
