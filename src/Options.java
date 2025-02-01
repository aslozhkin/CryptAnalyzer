import java.util.Arrays;

public enum Options {
    ENCRYPT(1),
    DECRYPT(2),
    BRUTEFORCE(3);

    private final int value;

    Options(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Options getOptionByValue(int value) {
        return Arrays.stream(Options.values())
                .filter(o -> o.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Опции: " + value + " не существует, необходимо выбрать из предложенных"));
    }
}
