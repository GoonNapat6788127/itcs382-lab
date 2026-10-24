
package com.bikeshare;

public enum MemberType {
    STUDENT(2),
    STAFF(3);

    private final int feePerMinute;

    MemberType(int feePerMinute) {
        this.feePerMinute = feePerMinute;
    }

    public int getFeePerMinute() {
        return feePerMinute;
    }

    public static MemberType fromString(String value) {
        return MemberType.valueOf(value.trim().toUpperCase());
    }
}
