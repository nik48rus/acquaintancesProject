package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import socialNetwork.PasswordGenerator;
import socialNetwork.TestData;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс контроллера добавления нового человека для newPeople.fxml
 */
public class NewPeopleController {

    @FXML
    private TextField fbText; // текст лицокниги

    @FXML
    private TextField hobbyText; // текст хобби

    @FXML
    private ChoiceBox<String> formarrigeText; // выбор для чего профиль

    @FXML
    private TextField surnameText; // фамилия

    @FXML
    private TextField loginText; // логин

    @FXML
    private TextField passwordText; // пароль

    @FXML
    private TextField vkText; // вк текст

    @FXML
    private DatePicker birthPick; // выбор ДР

    @FXML
    private ChoiceBox<String> genderText; // выбор пола

    @FXML
    private TextField nameText; // имя

    @FXML
    private Button saveButton; // кнопка сохранения

    @FXML
    private Button randomDataButton; // кнопка заполнения тестовыми данными

    private static final String url = "jdbc:mysql://localhost:3306/acq?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String user = "root";
    private static final String password = "";

    private static Connection con;
    private static Statement stmt;
    private static int rs;

    @FXML
    void initialize() {
        ObservableList<String> genderList = FXCollections.observableArrayList("Мужской", "Женский");
        ObservableList<String> forMarrigeList = FXCollections.observableArrayList("Да", "Нет");

        formarrigeText.setItems(forMarrigeList);
        genderText.setItems(genderList);

        // работает не трогай
        birthPick.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                birthPick.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        // обработчик кнопки рандомизации данных
        randomDataButton.setOnAction(event -> {
            TestData td = new TestData(); // тестовые данные
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useDigits(true).useLower(true).useUpper(true).build(); // генерим пароль
            genderText.setValue(rnd(1) == 1 ? "Мужской" : "Женский");
            nameText.setText(td.getRandomName(genderText.getValue()));
            surnameText.setText(td.getRandomSurname(genderText.getValue()));
            birthPick.setValue(LocalDate.parse(td.getRandomDate(), DateTimeFormatter.ofPattern("d.MM.yyyy")));
            vkText.setText(td.getRandomLink("vk"));
            fbText.setText(td.getRandomLink("fb"));
            formarrigeText.setValue(rnd(1) == 1 ? "Да" : "Нет");
            hobbyText.setText(td.getRandomHobby());
            loginText.setText(td.getRandomLogin(nameText.getText(), surnameText.getText()));
            passwordText.setText(passwordGenerator.generate(8));
        });

        // обработчик кнопки сохранения
        saveButton.setOnAction(event -> {
            String forMarrige = formarrigeText.getValue() == "Да" ? "1" : "0";
            // считываем инпуты
            String query = "INSERT INTO `users` (`login`, `password`, `name`, `surname`, `marrige`, `birthday`, `facebook`, `vk`, `hobby`, `gender`, `avatar`, `likes`) VALUES " + "('" + loginText.getText() + "','" + passwordText.getText() + "','" + nameText.getText() + "','" + surnameText.getText() + "'," + forMarrige + ",'" + birthPick.getValue() + "','" + fbText.getText() + "','" + vkText.getText() + "','" + hobbyText.getText() + "','" + genderText.getValue() + "', 'random-photos/1.jpg', 0);";
            // инсертим в базу
            try {
                con = DriverManager.getConnection(url, user, password);
                stmt = con.createStatement();
                rs = stmt.executeUpdate(query);

            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } finally {
                try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            }

            // закрываем окно
            Stage me = (Stage) saveButton.getScene().getWindow();
            me.close();
        });

    }

    public static int rnd(int max)
    {
        return (int) (Math.random() * ++max);
    }
}
