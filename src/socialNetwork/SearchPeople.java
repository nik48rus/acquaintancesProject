package socialNetwork;

import socialNetwork.People;
import socialNetwork.Photo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс поиска людей
 */
public class SearchPeople {

    private static final String url = "jdbc:mysql://localhost:3306/acq?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String user = "root";
    private static final String password = "";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    /**
     * Метод поиска по параметрам
     * @param type параметр
     * @param strSearch строка поиска
     * @return лист найденных людей
     */
    public static List<People> searchPeople(String type, String strSearch){
        List<People> findedPpl = new ArrayList<People>();
        String query = "";
        // определяем что мы ищем
        switch (type){
            case "Имя Фамилия":
                // составляем соответствующий запрос
                query = "select * from users where name like '%" + strSearch + "%' OR surname like '%" + strSearch + "%'";
                break;
            case "Пол":
                query = "select * from users where gender = '" + strSearch + "'";
                break;
            case "Возраст":
                char[] sybls = {'>', '<', '='};
                boolean hasSymbl = false;
                for(char s : sybls){ if (strSearch.indexOf(s) != -1) { hasSymbl = true; break; } }
                if(!hasSymbl){ strSearch = "=" + strSearch; }
                query = "select * from users where ( (YEAR(CURRENT_DATE) - YEAR(birthday)) - (DATE_FORMAT(CURRENT_DATE, '%m%d') < DATE_FORMAT(birthday, '%m%d')) ) " + strSearch.charAt(0) + " " + strSearch.substring(1) + "";
                break;
            case "Соц. сети":
                query = "select * from users where facebook = '" + strSearch + "' or vk = '" + strSearch + "'";
                break;
            case "Хобби":
                query = "select * from users where hobby like '%" + strSearch + "%'";
                break;
            case "Готовность к отношениям":
                System.out.println(strSearch);
                if(strSearch.equals("Да")) {
                    query = "select * from users where marrige = 1";
                }
                if (strSearch.equals("Нет")){
                    query = "select * from users where marrige = 0";
                }
                break;
        }

        // отправляем запрос
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                People ppl = new People(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("birthday"),
                        rs.getString("gender"),
                        formatStringToList(rs.getString("facebook") + ", " + rs.getString("vk")),
                        formatStringToList(rs.getString("hobby")),
                        rs.getBoolean("marrige"),
                        new Photo(rs.getString("avatar"), rs.getInt("likes")));
                // добавляем найденного человека в лист
                findedPpl.add(ppl);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }



        // распарсиваем запрос
        // возвращаем соответсвующих запросу людей

        return findedPpl;
    }

    /**
     * Метод поиска по строке
     * @param strSearch строка поиска для распарсивания
     * @return лист найденных людей
     */
    public static List<People> searchAllPeople(String strSearch){
        List<People> findedPpl = new ArrayList<People>();
        String query = "select * from users where ";

        String[] splitedSearch = strSearch.split(" ");

        for(int i = 0; i < splitedSearch.length; i++){
            switch (splitedSearch[i]){
                case "девушку":
                    query += "gender like '%Женский%'";
                    break;
                case "парня":
                    query += "gender like '%Мужской%'";
                    break;
                case "лет":
                    String HighOrLowAge = "=";
                    switch (splitedSearch[i-2]) {
                        case "старше":
                            HighOrLowAge = ">=";
                            break;
                        case "младше":
                            HighOrLowAge = "<=";
                            break;
                    }
                    query += " AND ( (YEAR(CURRENT_DATE) - YEAR(birthday)) - (DATE_FORMAT(CURRENT_DATE, '%m%d') < DATE_FORMAT(birthday, '%m%d')) ) " + HighOrLowAge + " " + splitedSearch[i-1];
                    break;
            }
        }

        System.out.println(query);

        // отправляем запрос
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                People ppl = new People(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("birthday"),
                        rs.getString("gender"),
                        formatStringToList(rs.getString("facebook") + ", " + rs.getString("vk")),
                        formatStringToList(rs.getString("hobby")),
                        rs.getBoolean("marrige"),
                        new Photo(rs.getString("avatar"), rs.getInt("likes")));
                // добавляем найденного человека в лист
                findedPpl.add(ppl);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return findedPpl;
    }

    /**
     * Метод перевода строки в лист по разделителю ","
     * @param str строка типа "элемент, элемент, ..."
     * @return
     */
    public static List<String> formatStringToList(String str){
        List<String> bufList = new ArrayList<>();
        Collections.addAll(bufList, str.split(", "));
        return bufList;
    }

    /**
     * Метод перевода листа в строку
     * @param list лист для распарсивания
     * @return строка типа "элемент, элемент, ..."
     */
    public static String formatListToString(List<String> list){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++){
            sb.append(list.get(i));
            if(i < list.size() - 1){
                sb.append(", ");
            }
        }
        return sb.toString();
    }

}
