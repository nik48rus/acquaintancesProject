package socialNetwork;

import java.io.*;

/**
 * Класс десериализации
 */
public class DeSerialData {
    public void start() throws IOException, ClassNotFoundException { // поехали
        FileInputStream fileInputStream = new FileInputStream("datas.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Datas dts = (Datas) objectInputStream.readObject();

        System.out.println(dts);
    }
}
