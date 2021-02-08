package socialNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.*;

/**
 * Класс для демонстрации StreamAPI
 */
public class Streams {
    static TestData td = new TestData();
    static People ppl1 = td.getRandomPeople();
    static People ppl2 = td.getRandomPeople();
    static People ppl3 = td.getRandomPeople();
    static Collection<People> peoples = new ArrayList<>(Arrays.asList(ppl1, ppl2, ppl3));

    public static void start(){
        // Выбрать мужчин-военнообязанных (от 18 до 27 лет)
        List<People> militaryService = peoples.stream().filter((p)-> p.getAge() >= 18 && p.getAge() < 27
                && p.getGender().equals("Мужской")).collect(Collectors.toList());
        System.out.println("Военнобязанные = " + militaryService);

        // Найти средний возраст среди мужчин
        double manAverageAge = peoples.stream().filter((p) -> p.getGender().equals("Мужской"))
                .mapToInt(People::getAge).average().getAsDouble();
        System.out.println("Средний возраст мужчин = " + manAverageAge);

        // Найти кол-во потенциально работоспособных людей в выборке (т.е. от 18 лет и учитывая что женщины выходят в 55 лет, а мужчина в 60)
        long peopleHowCanWork = peoples.stream().filter((p) -> p.getAge() >= 18).filter(
                (p) -> (p.getGender().equals("Женский") && p.getAge() < 55) || (p.getGender().equals("Мужской") && p.getAge() < 60)).count();
        System.out.println("Работоспособны = " + peopleHowCanWork);
    }

}
