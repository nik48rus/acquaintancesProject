package socialNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static Controller.Main.rnd;

/**
 * Класс генерации тестовых данных
 */
public class TestData {

    // задаём фундамент для генерации
    private List<String> maleNameData = new LinkedList<>();
    private List<String> femaleNameData = new LinkedList<>();
    private List<String> surnameData = new LinkedList<>();
    private List<String> hobbyData = new LinkedList<>();

    // примитивные тестовые данные
    private String[] maleNames = {"Артём", "Александр", "Максим", "Дмитрий ", "Иван", "Кирилл", "Никита", "Михаил", "Егор"};
    private String[] femaleNames = {"София", "Анастасия", "Мария", "Анна", "Виктория", "Полина", "Елизавета", "Екатерина", "Ксения"};
    private String[] surnames = {"Романов", "Бестужев", "Воронцов", "Пушкин", "Калинин", "Бахметьев", "Артемов"};
    private String[] hobbys = {"Дизайн", "Программирование", "Музыка"};

    /**
     * Конструктор класса
     */
    public TestData() {
        Collections.addAll(maleNameData, maleNames);
        Collections.addAll(femaleNameData, femaleNames);
        Collections.addAll(surnameData, surnames);
        Collections.addAll(hobbyData, hobbys);
    }

    /**
     * Функция получения случайного имени
     * @param gender пол
     * @return имя
     */
    public String getRandomName(String gender) {
        if(gender == "Мужской") {
            return maleNameData.get(rnd(maleNameData.size()-1));
        }
        return femaleNameData.get(rnd(femaleNameData.size()-1));
    }

    /**
     * Функция получения случайной фамилии
     * @param gender пол
     * @return фамилия
     */
    public String getRandomSurname(String gender) {
        return surnameData.get(rnd(surnameData.size()-1)) + (gender.equals("Женский") ? "а" : "");
    }

    /**
     * Функция получения случайнго логина на основе имени и фамилии
     * @param name имя (латиница)
     * @param surname фаамилия (латиница)
     * @return логин
     */
    public String getRandomLogin(String name, String surname){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(surname);
        return Translit.cyr2lat(sb.toString());
    }

    /**
     * Функция получения случайного хобби
     * @return хобби
     */
    public String getRandomHobby() {
        return hobbyData.get(rnd(hobbyData.size()-1));
    }

    /**
     * Функция получения случайной даты (год-месяц-день)
     * @return дата год-месяц-день
     */
    public String getRandomDate() {
        String day = String.valueOf(rnd(27)+1);
        String month = String.valueOf(rnd(11)+1);
        return String.format("%d-%s-%s", rnd(40)+1975, month.length() == 1 ? "0" + month : month, day.length() == 1 ? "0" + day : day);
    }

    /**
     * Функция получения случайного фото (female, male, random)
     * @param type female, male, random
     * @return объект фото
     */
    public Photo getRandomPhoto(String type) {
        int photoNum = rnd(2)+1;
        return new Photo(type + "-photos/" + photoNum + ".jpg", 0);
    }

    /**
     * Функция получения случайной ссылки (vk, fb)
     * @param type vk, fb
     * @return ссылка типа https://site.com/id666
     */
    public String getRandomLink(String type) {
        switch (type) {
            case "vk":
                return "http://vk.com/id" + rnd(3999);
            case "fb":
                return "https://www.facebook.com/profile.php?id=" + rnd(3999);
        }
        return "http://google.com";
    }

    /**
     * Функция генерации случайного человека
     * @return объект человека
     */
    public People getRandomPeople(){
        return new People(this.getRandomName("Мужской"),
                this.getRandomSurname("Мужской"),
                this.getRandomDate(),
                "Мужской",
                new ArrayList<String>(),
                new ArrayList<String>(),
                false, this.getRandomPhoto("random"));
    }
}