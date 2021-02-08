package socialNetwork;

/**
 * Класс фотографии
 */
public class Photo {
    private String srcImage;
    private int likes;

    /**
     * Конструктор класса
     * @param srcImage путь до изображения
     * @param likes кол-во лайков
     */
    public Photo(String srcImage, int likes) {
        this.srcImage = srcImage;
        this.likes = likes;
    }

    /**
     * Стандартный конструктор
     */
    public Photo() {
        this.srcImage = "";
        this.likes = 0;
    }

    /**
     * Функция получения пути изображения
     * @return путь изображения
     */
    public String getSrcImage() {
        return srcImage;
    }

    /**
     * Функция получения лайков
     * @return кол-во лайков
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Перевод в строку
     * @return {путь, лайки}
     */
    public String toString() {
        return String.format("{%s, %d}", this.srcImage, this.likes);
    }
}

