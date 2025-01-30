import java.util.ArrayList;
import java.util.List;

public class BruteforceService {
    private static final int DUMMY_KEYS_COUNT = 100;
    public static final String FILE_IS_EMPTY_EXC_MESSAGE = "Файл с зашифрованными словами не может быть пустым";

    private FileManager fileManager = new FileManager();
    private CryptService cryptService = new CryptService();

    private List<String> encryptedStrings;
    private List<String> dictionaryStrings;
    private List<Integer> keys = new ArrayList<>();

    public BruteforceService() {
        encryptedStrings = fileManager.readFileAsStrings(Constants.ENCRYPTED_PATH);
        dictionaryStrings = fileManager.readFileAsStrings(Constants.DICTIONARY_PATH);
    }

    /**
     * Метод для определения возможных ключей
     * @return возможный ключ
     */
    public int bruteforce() {
        if (encryptedStrings.isEmpty()) {
            throw new IllegalArgumentException(FILE_IS_EMPTY_EXC_MESSAGE);
        }
        // Брутфорсим по 100 ключей пока не упремся в максимальный Integer
        int startPosition = 1;
        int endPosition = DUMMY_KEYS_COUNT;

        while (Integer.MAX_VALUE > endPosition) {
            int possibleKey = fillKeysAndBruteforce(startPosition, endPosition);
            if (checkPossibleKey(possibleKey)) {
                return possibleKey;
            }
            startPosition = endPosition + 1;
            endPosition = startPosition + DUMMY_KEYS_COUNT;
        }

        // Брутфорсим по 100 ключей пока не упремся в минимальный Integer
        startPosition = -1;
        endPosition = -DUMMY_KEYS_COUNT;
        while (Integer.MIN_VALUE < endPosition) {
            int possibleKey = fillKeysAndBruteforce(startPosition, endPosition);
            if (checkPossibleKey(possibleKey)) {
                return possibleKey;
            }
            startPosition = endPosition - 1;
            endPosition = startPosition - DUMMY_KEYS_COUNT;
        }
        return 0;
    }

    /**
     * Метод для заполнения списка ключами и перебора
     * @param startValue начальная позиция ключа
     * @param endValue конечная позиция ключа
     * @return возможный ключ
     */
    private int fillKeysAndBruteforce(int startValue, int endValue) {
        for (int i = startValue; i <= endValue; i++) {
            keys.add(i);
        }
        return bruteforce(keys);
    }

    /**
     * Меторд для перебора по списку ключей
     * @param keys список ключей
     * @return возможный ключ
     */
    private int bruteforce(List<Integer> keys) {
        int possibleKey = 0;
        for (Integer key : keys) {
            List<String> decryptedWords = new ArrayList<>();
            // Расшифровываем все слова по каждому ключу
            for (String encStr : encryptedStrings) {
                decryptedWords.add(cryptService.decrypt(key, encStr));
            }
            int countMatchingWords = 0;
            for (String dword : decryptedWords) {
                if (dictionaryStrings.contains(dword)) {
                    countMatchingWords++;
                }
            }
            if (countMatchingWords >= decryptedWords.size() / 5) {
                possibleKey = key;
                break;
            }
        }
        return possibleKey;
    }

    private boolean checkPossibleKey(int possibleKey) {
        return possibleKey != 0;
    }
}
