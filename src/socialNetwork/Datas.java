package socialNetwork;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Класс данных для сериализации
 */
public class Datas implements Serializable {
    /** Уникализатор */
    private static final long serialVersionUID = 1L;

    /** Люди */
    private String[] ppls;
    /** Фотографии */
    private String[] phts;

    /**
     * Конструктор класса
     * @param ppls люди
     * @param phts фото
     */
    public Datas(String[] ppls, String[] phts) {
        this.ppls = ppls;
        this.phts = phts;
    }

    /**
     * Функция получения массива людей
     * @return массив людей
     */
    public String[] getPpls() {
        return ppls;
    }

    /**
     * Процедура определения массива людей
     * @param ppls массив людей
     */
    public void setPpls(String[] ppls) {
        this.ppls = ppls;
    }

    /**
     * Функция получения массива фотографий
     * @return массив фото
     */
    public String[] getPhts() {
        return phts;
    }

    /**
     * Процедура определения массива фотографий
     * @param phts массив фотографий
     */
    public void setPhts(String[] phts) {
        this.phts = phts;
    }

    /**
     * Переопределение метода перевода в строку
     * @return String данные об объекте
     */
    @Override
    public String toString() {
        return "Serializ{" +
                "peoples=" + Arrays.toString(ppls) +
                ", photos=" + Arrays.toString(phts) +
                '}';
    }
}
