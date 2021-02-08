package socialNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс генерации пароля
 */
public final class PasswordGenerator {

    // данные из которых генерируем
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";

    // опциональность генерации
    private boolean useLower;
    private boolean useUpper;
    private boolean useDigits;
    private boolean usePunctuation;

    /**
     * Конструктор класса (пустой)
     */
    private PasswordGenerator() {
        throw new UnsupportedOperationException("Empty constructor is not supported.");
    }

    /**
     * Конструктор класса
     * @param builder
     */
    private PasswordGenerator(PasswordGeneratorBuilder builder) {
        this.useLower = builder.useLower;
        this.useUpper = builder.useUpper;
        this.useDigits = builder.useDigits;
        this.usePunctuation = builder.usePunctuation;
    }

    /**
     * Подкласс PasswordGenerator
     */
    public static class PasswordGeneratorBuilder {

        // опциональность
        private boolean useLower;
        private boolean useUpper;
        private boolean useDigits;
        private boolean usePunctuation;

        /**
         * Конструктор класса
         */
        public PasswordGeneratorBuilder() {
            this.useLower = false;
            this.useUpper = false;
            this.useDigits = false;
            this.usePunctuation = false;
        }

        /**
         * Задайте true если хотите использовать нижний регистр
         * (abc...xyz). Стандартно false.
         *
         * @param useLower true если хотите использовать нижний регистр
         * доступны (abc...xyz). Стандартно false.
         * @return конструктор для скрепления.
         */
        public PasswordGeneratorBuilder useLower(boolean useLower) {
            this.useLower = useLower;
            return this;
        }

        /**
         * Задайте true если хотите использовать верхний регистр
         * (ABC...XYZ). Стандартно false.
         *
         * @param useUpper true если хотите использовать верхний регистр
         * доступны (ABC...XYZ). Стандартно false.
         * @return конструктор для скрепления.
         */
        public PasswordGeneratorBuilder useUpper(boolean useUpper) {
            this.useUpper = useUpper;
            return this;
        }

        /**
         * Задайте true если хотите использовать цифры (123..).
         * Стандартно false.
         *
         * @param useDigits true если хотите использовать цифры (123..). Стандартно false.
         * @return конструктор для скрепления.
         */
        public PasswordGeneratorBuilder useDigits(boolean useDigits) {
            this.useDigits = useDigits;
            return this;
        }

        /**
         * Задайте true если хотите использовать пунктуационные знаки
         * (!@#..). Стандартно false.
         *
         * @param usePunctuation true если хотите использовать пунктуационные знаки (!@#..). Стандартно false.
         * @return конструктор для скрепления.
         */
        public PasswordGeneratorBuilder usePunctuation(boolean usePunctuation) {
            this.usePunctuation = usePunctuation;
            return this;
        }

        /**
         * Получения объекта для использования.
         *
         * @return объект PasswordGenerator.
         */
        public PasswordGenerator build() {
            return new PasswordGenerator(this);
        }
    }

    /**
     * Этот метод генерирует пароль в зависимости
     * от определённых вами опций.
     *
     * @param length длина пароля, который вы хотите создать.
     * @return сгенерированный пароль.
     */
    public String generate(int length) {
        // Валидация аргумента.
        if (length <= 0) {
            return "";
        }

        // Переменные.
        StringBuilder password = new StringBuilder(length);
        Random random = new Random(System.nanoTime());

        // Получаем опции для пользования.
        List<String> charCategories = new ArrayList<>(4);
        if (useLower) {
            charCategories.add(LOWER);
        }
        if (useUpper) {
            charCategories.add(UPPER);
        }
        if (useDigits) {
            charCategories.add(DIGITS);
        }
        if (usePunctuation) {
            charCategories.add(PUNCTUATION);
        }

        // Генерируем пароль.
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        return new String(password);
    }
}
