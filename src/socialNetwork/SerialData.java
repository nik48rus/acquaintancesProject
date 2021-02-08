package socialNetwork;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Класс сериализации данных
 */
public class SerialData {
    public void main() throws IOException {

        TestData td = new TestData();

        //создаем наш объект
        String[] ppls = {"Alex", "Masha"};
        String[] phts = {"alex.png", "masha.jpg"};

        Datas dts = new Datas(ppls, phts);

        // создаем 2 потока для сериализации объекта и сохранения его в файл
        FileOutputStream outputStream = new FileOutputStream("datas.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // сохраняем объект в файл
        objectOutputStream.writeObject(dts);

        // закрываем поток и освобождаем ресурсы
        objectOutputStream.close();
    }
}
