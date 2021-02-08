package socialNetwork;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Класс обработчика изображения
 */
public class ImageWorker {

    /**
     * Конструктор класса ImageWorker
     * @param pathImageIn путь к исходному изображению
     * @param pathImageOut путь куда записать обработанное изображение
     * @throws IOException
     */
    public ImageWorker(String pathImageIn, String pathImageOut) throws IOException {
        //загружаем изображение
        BufferedImage image = ImageIO.read(new File(pathImageIn));
        int x = 0, y = 0, w = 0, h = 0;

        if(image.getWidth() > image.getHeight()) { // ширина > высоты
            x = image.getWidth()/2 - image.getHeight()/2;
            y = 0;
            w = image.getHeight();
            h = image.getHeight();
        } else { // наоборот
            x = 0;
            y = image.getHeight()/2 - image.getWidth()/2;
            w = image.getWidth();
            h = image.getWidth();
        }

        BufferedImage image2 = image.getSubimage( x, y, w, h); // обрезаем
        ImageIO.write(image2, "jpg", new File(pathImageOut)); // сохраняем
    }
}
