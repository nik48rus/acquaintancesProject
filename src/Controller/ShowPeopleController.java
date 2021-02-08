package Controller;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.neo4j.driver.v1.Record;
import socialNetwork.People;
import socialNetwork.TestingNeo4j;

import java.io.File;
import java.sql.*;
import java.util.List;

/**
 * Класс отображения человека для showPeople.fxml
 */
public class ShowPeopleController {

    public int shownId = 0;
    public int userId = 0;

    private static final String url = "jdbc:mysql://localhost:3306/acq?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String user = "root";
    private static final String password = "";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    @FXML
    public Label fullNameText; // имя и фамилия

    @FXML
    public Label fbText; // лицокнига

    @FXML
    public Label hobbyText; // хобби

    @FXML
    public Label vkText; // вк

    @FXML
    public Label forMarrigeText; // для отношений чи шо

    @FXML
    public Label ageText; // возраст

    @FXML
    public Label genderText; // пол

    @FXML
    public ImageView friend3Image;

    @FXML
    public ImageView friend1Image;

    @FXML
    public ImageView avatarImageView; // аватар

    @FXML
    public ImageView friend2Image;

    public SearchByParamController controller;
    public SearchByQueryController controller2;

    public void setParent (SearchByParamController controller){
        this.controller = controller;
    }
    public void setParent (SearchByQueryController controller){
        this.controller2 = controller;
    }

    @FXML
    void initialize() throws Exception {
//        List<Record> lr = null;
//        try ( TestingNeo4j greeter = new TestingNeo4j( "bolt://localhost:7687", "neo4j", "1234" ) )
//        {
//            lr = greeter.printOurFriend(shownId, userId);
//        }
//        try {
//            friend1Image.setImage(getFriendAvatar(lr.get(0).get("a").get("id").asString()));
//            friend2Image.setImage(getFriendAvatar(lr.get(1).get("a").get("id").asString()));
//            friend3Image.setImage(getFriendAvatar(lr.get(2).get("a").get("id").asString()));
//        } catch (IndexOutOfBoundsException e) {}
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
