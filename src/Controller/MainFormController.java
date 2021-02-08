package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import socialNetwork.DeSerialData;
import socialNetwork.ImageWorker;

/**
 * Класс контроллера формы профиля для mainForm.fxml
 */
public class MainFormController {

    public int userId = 0;

    @FXML
    public ImageView avatarIV; // аватар

    @FXML
    public TextField hobbyText; // текст хобби

    @FXML
    private Button searchByParam; // кнопка поиска по параметрам

    @FXML
    public TextField vkText; // текст вк

    @FXML
    public DatePicker birthPick; // выбор ДР

    @FXML
    private Button searchByAll; // кнопка поиска по строке

    @FXML
    private Button addPeople; // кнопка добавления нового пользователя

    @FXML
    public ChoiceBox<String> genderText; // выбор пола

    @FXML
    public TextField nameText; // имя

    @FXML
    public TextField fbText; // текст лицокниги

    @FXML
    public ChoiceBox<String> formarrigeText; // выбор для чего мы создали профиль

    @FXML
    public TextField surnameText; // фамилия

    @FXML
    private Button saveButton; // кнопка сохранения изменённых данных

    @FXML
    private Button pickAvatarButton; // кнопка выбора аватара

    @FXML
    public ImageView friend1Image;

    @FXML
    public ImageView friend2Image;

    @FXML
    public ImageView friend3Image;

    private static final String url = "jdbc:mysql://localhost:3306/acq?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String user = "root";
    private static final String password = "";

    private static Connection con;
    private static Statement stmt;
    private static int rs;

    private AuthController controller;

    public void setParent (AuthController controller){
        this.controller = controller;
    }

    final FileChooser fileChooser = new FileChooser(); // для выбора файла аватара

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        // десерилизация
        new DeSerialData().start();

        // обработчик выбора аватара
        pickAvatarButton.setOnAction(event -> {
            // открыть диалоговое окно выбора файла
            File file = fileChooser.showOpenDialog(null);
            String newFile = "";
            if (file != null) {
                newFile = "src/images/uploads/" + System.currentTimeMillis() / 1000L + ".jpg"; // новое имя файла обработанного изображения
                try {
                    // обрезаем изображение так как вставить мы можем только квадратное изображение
                    new ImageWorker(file.toString(), newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(newFile);

                // записать новый путь картинки в базу
                String query = "UPDATE `users` SET `avatar`='" + newFile + "' WHERE `id` = " + userId;
                System.out.println(query);

                // инсертим в базу
                try {
                    con = DriverManager.getConnection(url, user, password);
                    stmt = con.createStatement();
                    rs = stmt.executeUpdate(query);
                } catch (SQLException sqlEx) {
                    sqlEx.printStackTrace();
                } finally {
                    // закрываем соединение
                    try { con.close(); } catch(SQLException se) { /*can't do anything */ }
                    try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
                }

                // обновить картинку на форме
                File fileImage = new File(newFile);
                Image ava = new Image(fileImage.toURI().toString());
                avatarIV.setImage(ava);
            }

        });

        // обработчик кнопки поиска по параметрам
        searchByParam.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/searchByParam.fxml"));
                Parent formSearchByParam = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(formSearchByParam, 456, 444));
                stage.setTitle("Проект \"Знакомства\" - поиск по параметру");
                // ниже объясняю почему сложно добавить общих друзей (удалить)
                // в общем надо как-то передавать в сёрчбайпарамконтроллер id текущего пользователя, чтобы потом, при показе окна вставлять в демонический запрос id-шник и находить грёбанных общих друзей
                stage.show();
            } catch (Exception e){
                System.out.println(e);
            }
        });

        // обработчик кнопки поиска по строке
        searchByAll.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("../FXML/searchByQuery.fxml"));
                Parent formSearchByQuery = (Parent) fxmlLoader2.load();
                Stage stage2 = new Stage();
                stage2.setScene(new Scene(formSearchByQuery, 456, 395));
                stage2.setTitle("Проект \"Знакомства\" - поиск по строке");
                stage2.show();
            } catch (Exception e){
                System.out.println(e);
            }
        });

        // обработчик кнопки добавления человека
        addPeople.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("../FXML/newPeople.fxml"));
                Parent formNewPeople = (Parent) fxmlLoader3.load();
                Stage stage3 = new Stage();
                stage3.setScene(new Scene(formNewPeople, 415, 535));
                stage3.setTitle("Проект \"Знакомства\" - поиск по параметру");
                stage3.show();
            } catch (Exception e){
                System.out.println(e);
            }
        });

        // обработчик выбора ДР
        birthPick.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            // хз что это, но работает не трогай
            {
                birthPick.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        // обработчик кнопки сохранения
        saveButton.setOnAction(event -> {
            String forMarrige = formarrigeText.getValue() == "Да" ? "1" : "0";
            String query = "UPDATE `users` SET `name`='" + nameText.getText() + "',`surname`='" + surnameText.getText() + "',`marrige`=" + forMarrige + ",`birthday`='" + birthPick.getValue() + "',`facebook`='" + fbText.getText() + "',`vk`='" + vkText.getText() + "',`hobby`='" + hobbyText.getText() + "',`gender`='" + genderText.getValue() + "' WHERE `id` = " + userId + ";";
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
        });
    }
}
