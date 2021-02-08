package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.neo4j.driver.v1.Record;
import socialNetwork.People;
import socialNetwork.Photo;
import socialNetwork.SearchPeople;
import socialNetwork.TestingNeo4j;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Класс контроллера аутентификации пользователя для Auth.fxml
 */
public class AuthController {

    // JDBC URL, логин и пароль MySQL сервера
    private static final String url = "jdbc:mysql://localhost:3306/acq?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String user = "root";
    private static final String password = "";

    // JDBC переменные для открытия и управления соединением
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    @FXML
    private TextField loginText; // Поле ввода логина

    @FXML
    private Button signupButton;

    @FXML
    private TextField passwordText; // Поле ввода пароля

    @FXML
    private Button authButton; // Кнопка авторизации

    @FXML
    private ImageView bgImage; // Изображение справа, для разнообразия
    private MainFormController children; // для открытия главной формы (профиля)

    @FXML
    void initialize() {
        // ставим изображение справа
        bgImage.setImage(new Image(new File("src/images/assets/bg.png").toURI().toString()));

        // обрабатываем нажание на кнопку аутентификации
        authButton.setOnAction(event -> {
            // собираем данные для авторизации
            String login = loginText.getText(); // логин
            String pass = passwordText.getText(); // пароль
            People ppl = null;
            ObservableList<String> genderList = FXCollections.observableArrayList("Мужской", "Женский"); // для выбора в мейн форме
            ObservableList<String> forMarrigeList = FXCollections.observableArrayList("Да", "Нет"); // для выбора в мейн форме 2.0

            // обработка пустого ввода
            if((loginText.getText().isEmpty() || passwordText.getText().isEmpty()) || (loginText.getText().isEmpty() && passwordText.getText().isEmpty())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Информация");
                alert.setHeaderText(null);
                alert.setContentText("Введите логин и пароль");

                alert.showAndWait();
            } else {
                // составляем запрос
                String query = "SELECT * FROM `users` WHERE `login` = '" + login + "' AND `password` = '" + pass + "';";

                try { // соединяемся
                    con = DriverManager.getConnection(url, user, password);
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
                    int countStr = 0;

                    // кешируем данные в ppl
                    while (rs.next()) {
                        ppl = new People(rs.getString("name"),
                                rs.getString("surname"),
                                rs.getString("birthday"),
                                rs.getString("gender"),
                                SearchPeople.formatStringToList(rs.getString("facebook") + ", " + rs.getString("vk")),
                                SearchPeople.formatStringToList(rs.getString("hobby")),
                                rs.getBoolean("marrige"),
                                new Photo(rs.getString("avatar"), rs.getInt("likes")));
                        countStr++;
                        ppl.setId(rs.getInt("id"));
                    }

                    if (countStr == 0){ // обрабатываем отсутвие пользователя
                        Alert alert = new Alert(Alert.AlertType.ERROR);

                        alert.setTitle("Ошибка");
                        alert.setHeaderText(null);
                        alert.setContentText("Такого пользователя нет");

                        alert.showAndWait();
                        return; // TODO: протестировать
                    }

                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } finally {
                    // закрываем соединение
                    try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                    try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
                    try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
                }

                // открывыаем окно
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/mainForm.fxml"));

                try { // заполняем данными пользователя формы
                    root = loader.load();
                    children = loader.getController();
                    children.setParent(this);
                    children.nameText.setText(ppl.getName());
                    children.surnameText.setText(ppl.getSurname());
                    String[] bufDate = ppl.getBirthday().split("-");
                    children.birthPick.setValue(LocalDate.of(Integer.parseInt(bufDate[0]), Integer.parseInt(bufDate[1]), Integer.parseInt(bufDate[2])));
                    children.genderText.setItems(genderList);
                    switch (ppl.getGender()) {
                        case "Мужской":
                            children.genderText.setValue("Мужской");
                            break;
                        case "Женский":
                            children.genderText.setValue("Женский");
                            break;
                    }
                    children.vkText.setText(ppl.getSocialMedia().get(1));
                    children.fbText.setText(ppl.getSocialMedia().get(0));
                    children.formarrigeText.setItems(forMarrigeList);
                    if (ppl.getReadyForMarriage()) {
                        children.formarrigeText.setValue("Да");
                    } else {
                        children.formarrigeText.setValue("Нет");
                    }
                    children.hobbyText.setText(SearchPeople.formatListToString(ppl.getHobby()));
                    File file = new File("src/images/" + ppl.getAvatar().getSrcImage());
                    Image ava = new Image(file.toURI().toString());
                    children.avatarIV.setImage(ava);

//                    List<Record> lr = null;
//                    try ( TestingNeo4j greeter = new TestingNeo4j( "bolt://localhost:7687", "neo4j", "1234" ) )
//                    {
//                        lr = greeter.printGreeting( String.valueOf(ppl.getId()) );
//                    }
//                    try {
//                        children.friend1Image.setImage(getFriendAvatar(lr.get(0).get("a").get("id").asString()));
//                        children.friend2Image.setImage(getFriendAvatar(lr.get(1).get("a").get("id").asString()));
//                        children.friend3Image.setImage(getFriendAvatar(lr.get(2).get("a").get("id").asString()));
//                    } catch (IndexOutOfBoundsException e) {}

                    children.userId = ppl.getId();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                assert root != null;
                stage.setScene(new Scene(root, 600, 506));
                stage.setTitle("Проект \"Знакомства\" - моя страница");
                stage.show();

                // закрыть окно авторизации
                Stage me = (Stage) authButton.getScene().getWindow();
                me.close();
            }
        });
//        bgImage.setOnMouseClicked(event -> {
//            loginText.setText("nik48rus");
//            passwordText.setText("nk2000");
//        }); TO DO: убрать эту фичу в продакшене
    }

    private Image getFriendAvatar(String id) {
        String query = "SELECT `avatar` FROM `users` WHERE `id` = " + id + ";";

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                return new Image(new File("src/images/" + rs.getString(1)).toURI().toString());
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return new Image("src/images/random-photos/1.jpg");
    }
}
