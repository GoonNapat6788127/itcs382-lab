package parcelpoint;

public enum ParcelSize {
    SMALL(1), MEDIUM(2), LARGE(3);

    private final int code;

    ParcelSize(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean fitsIn(ParcelSize lockerSize) {
        return compareTo(lockerSize) <= 0;
    }

    public static ParcelSize fromNumber(int number) {
        switch (number) {
            case 1 -> {
                return SMALL;
            }
            case 2 -> {
                return MEDIUM;
            }
            case 3 -> {
                return LARGE;
            }
            default -> throw new IllegalArgumentException("Size must be 1, 2 or 3: " + number);
        }
    }
}