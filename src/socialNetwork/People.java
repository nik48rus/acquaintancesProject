package socialNetwork;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Класс профиля пользователя
 */
public class People {

    private String birthday;
    private String gender;
    private List<String> socialMedia;
    private List<String> hobby;
    private Boolean readyForMarriage;
    private Photo avatar;
    private String name;
    private String surname;
    private int id;

    /**
     * Конструктор класса
     * @param name имя
     * @param surname фамилия
     * @param birthday ДР
     * @param gender пол
     * @param socialMedia соц.сети
     * @param hobby хобби
     * @param readyForMarriage для отношений
     * @param avatar аватар
     */
    public People(String name, String surname, String birthday, String gender, List<String> socialMedia, List<String> hobby, Boolean readyForMarriage, Photo avatar) {
        this.birthday = birthday;
        this.gender = gender;
        this.socialMedia = socialMedia;
        this.hobby = hobby;
        this.readyForMarriage = readyForMarriage;
        this.avatar = avatar;
        this.name = name;
        this.surname = surname;
    }

    public People() {}

    /**
     * Функция получения ДР
     * @return ДР
     */
    public String getBirthday() { return birthday; }

    /**
     * Функция получения пола
     * @return пол
     */
    public String getGender() { return gender; }

    /**
     * Функция получения листа соц. сетей
     * @return лист соц. сетей
     */
    public List<String> getSocialMedia() { return socialMedia; }

    /**
     * Функция получения листа хобби
     * @return лист хобби
     */
    public List<String> getHobby() { return hobby; }

    /**
     * Функция получения готовности к отношениям
     * @return bool готов / не готов
     */
    public Boolean getReadyForMarriage() { return readyForMarriage; }

    /**
     * Функция получения объекта аватара
     * @return объект аватара
     */
    public Photo getAvatar() { return avatar; }

    /**
     * Функция получения имени
     * @return имя
     */
    public String getName() { return name; }

    /**
     * Функция получения фамилии
     * @return фамилия
     */
    public String getSurname() { return surname; }

    /**
     * Функция получения ID
     * @return ID
     */
    public int getId() { return id; }

    /**
     * Процедура определения ID
     * @param id ID
     */
    public void setId(int id) { this.id = id; }

    /**
     * Функция получения возраста
     * @return возраст
     */
    public int getAge() {
        Date date = new Date(); // this object contains the current date value
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = dateFormat.parse(this.getBirthday());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = date.getTime() - date1.getTime();
        return (int) (milliseconds / (24 * 60 * 60 * 1000)) / 365;
    }

//    public void randomUser() {
//        testData td = new testData();
//        this.birthday = td.getRandomDate();
//        this.gender = rnd(1) == 1 ? "male" : "female";
//        this.readyForMarriage = rnd(1) == 1;
//        if(hobby == null) { hobby = new LinkedList<String>(); }
//        this.hobby.add(td.getRandomHobby());
//        this.name = td.getRandomName(this.gender);
//        this.surname = td.getRandomSurname(this.gender);
//        Photo ph = td.getRandomPhoto(gender);
//        this.avatar = ph;
//        if(photos == null) { photos = new LinkedList<Photo>(); }
//        this.photos.add(ph);
//        this.photos.add(td.getRandomPhoto("random"));
//        if(socialMedia == null) { socialMedia = new LinkedList<String>(); }
//        this.socialMedia.add(td.getRandomLink("vk"));
//        this.socialMedia.add(td.getRandomLink("fb"));
//    }


    /**
     * Функция печати данных
     * @return
     */
    public String printData() {
        return String.format("Имя: %s\nФамилия: %s\nПол: %s\nДата рождения: %s\nСоц. сети: %s\nХобби: %s\nГотовность к отношениям: %s\nАватар: %s\n\n",
                this.name, this.surname, this.gender, this.birthday, SearchPeople.formatListToString(this.socialMedia), SearchPeople.formatListToString(this.hobby), this.readyForMarriage ? "да" : "нет", this.avatar);
    }

    /**
     * Перевод в строку
     * @return Имя, Фамилия, Возраст
     */
    public String toString() {
        return String.format("%s %s, %d", this.name, this.surname, this.getAge());
    }
}

