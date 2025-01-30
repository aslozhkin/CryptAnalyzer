public class Validator {

    public void validateOption(Options option) {
        if (option == null) {
            throw new IllegalArgumentException("Опции: " + option + " не существует, необходимо выбрать из предложенных");
        }
    }
}
