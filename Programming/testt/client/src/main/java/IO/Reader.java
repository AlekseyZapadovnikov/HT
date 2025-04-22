package IO;


/**
 * Интерфейс для создания читающих объектов
 */
public interface Reader {
    /**
     * Читает целого объекта
     * @return прочитанный объект
     */
    String read();

    /**
     * Читает следующую строку
     * @return строка
     */
    String nextLine();
}
