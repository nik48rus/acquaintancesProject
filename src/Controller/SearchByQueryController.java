package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import socialNetwork.People;
import socialNetwork.SearchPeople;

import java.io.File;
import java.util.List;

/**
 * Класс поиска по строке для searchByQuery.fxml
 */
public class SearchByQueryController {
    @FXML
    private Button searchButton; // кнопка поиска

    @FXML
    private TextField searchTextField; // данные для поиска

    @FXML
    private ListView<People> resultListView; // отображение полученных данных

    private ShowPeopleController children;

    @FXML
    void initialize() {
        // обработчик кнопки поиска
        searchButton.setOnAction(event -> doQuery());

        // обработчик поиска если нажали на Enter в поле
        searchTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                doQuery();
            }
        });

        // слушатель для открытия по нажатию на пункт меню
        resultListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<People>() {

            @Override
            public void changed(ObservableValue<? extends People> observable, People oldValue, People ppl) {
                if (ppl == null) {return;}
                // открыть окно
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/showPeople.fxml"));

                try {
                    root = loader.load();
                    children = loader.getController();
                    children.fullNameText.setText(ppl.getName() + " " + ppl.getSurname());
                    children.genderText.setText(ppl.getGender());

                    children.vkText.setText("VK: " + ppl.getSocialMedia().get(1));
                    children.fbText.setText("FB:" + ppl.getSocialMedia().get(0));
                    if (ppl.getReadyForMarriage()) {
                        children.forMarrigeText.setText("Знакомство для отношений");
                    } else {
                        children.forMarrigeText.setText("Знакомство не для отношений");
                    }
                    children.hobbyText.setText("Хобби: " + SearchPeople.formatListToString(ppl.getHobby()));
                    File file = new File("src/images/" + ppl.getAvatar().getSrcImage());
                    Image ava = new Image(file.toURI().toString());
                    children.avatarImageView.setImage(ava);
//                    System.out.println(file.toURI().toString());
//                        List<Record> lr = null;
//                        try ( TestingNeo4j greeter = new TestingNeo4j( "bolt://localhost:7687", "neo4j", "1234" ) )
//                        {
//                            lr = greeter.printGreeting( "2" );
//                        }
//                        try {
//                            children.friend1Image.setImage(getFriendAvatar(lr.get(0).get("a").get("id").asString()));
//                            children.friend2Image.setImage(getFriendAvatar(lr.get(1).get("a").get("id").asString()));
//                            children.friend3Image.setImage(getFriendAvatar(lr.get(2).get("a").get("id").asString()));
//                        } catch (IndexOutOfBoundsException e) {}
                    children.ageText.setText(String.valueOf(ppl.getAge()) + " лет");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                assert root != null;
                stage.setScene(new Scene(root, 600, 235));
                stage.setTitle("Профиль пользователя");
                stage.show();
            }
        });
    }

    /**
     * Функция выполнения запроса
     */
    private void doQuery(){
        if(searchTextField.getText().isEmpty()){ // обрабатываем пустого запроса
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Информация");
            alert.setHeaderText(null);
            alert.setContentText("Введите хоть какой-то запрос");

            alert.showAndWait();
        } else { // делаем запрос
            List<People> finded = SearchPeople.searchAllPeople(searchTextField.getText());
            System.out.println(finded);
            ObservableList<People> ppls = FXCollections.observableList(finded);
            resultListView.setItems(ppls);
        }
    }
}
