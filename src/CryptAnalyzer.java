import java.util.Scanner;

public class CryptAnalyzer {

    public static void main(String[] args) {
        Menu menu = new Menu();
        CryptService cryptService = new CryptService();
        BruteforceService bruteforceService = new BruteforceService();
        Validator validator = new Validator();

        menu.printOptions();

        Scanner scanner = new Scanner(System.in);
        Options option = Options.getOptionByValue(scanner.nextInt());
        validator.validateOption(option);

        switch (option) {
            case ENCRYPT -> {
                menu.printEnterKey();
                int key = scanner.nextInt();
                cryptService.encrypt(key);
            }
            case DECRYPT -> {
                menu.printEnterKey();
                int key = scanner.nextInt();
                cryptService.decrypt(key);
            }
            case BRUTEFORCE -> {
                int probableKey = bruteforceService.bruteforce();
                if (probableKey != 0) {
                    System.out.println("Вероятный ключ: " + probableKey);
                } else {
                    System.out.println("Ключ не был подобран");
                }
            }
        }
    }
}