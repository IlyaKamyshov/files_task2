import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        //1. Создать три экземпляра класса GameProgress.
        GameProgress progress1 = new GameProgress(125, 125, 2, 400);
        GameProgress progress2 = new GameProgress(78, 99, 5, 114);
        GameProgress progress3 = new GameProgress(37, 200, 9, 1);

        //2. Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
        String save1 = "/Users/kames/Desktop/games/savegames/save1.dat";
        String save2 = "/Users/kames/Desktop/games/savegames/save2.dat";
        String save3 = "/Users/kames/Desktop/games/savegames/save3.dat";

        saveGame(save1, progress1);
        saveGame(save2, progress2);
        saveGame(save3, progress3);

        //3. Созданные файлы сохранений из папки savegames запаковать в архив zip.
        String archive = "/Users/kames/Desktop/games/savegames/archive.zip";
        List<String> savesList = new ArrayList<>();
        savesList.add(save1);
        savesList.add(save2);
        savesList.add(save3);

        zipFiles(archive, savesList);

        //4. Удалить файлы сохранений, лежащие вне архива.
        delArchivedFiles(savesList);

    }

    public static void saveGame(String address, GameProgress gameProgress) {

        try (FileOutputStream fos = new FileOutputStream(address);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void zipFiles(String address, List<String> list) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(address))) {

            for (String s : list) {
                File file = new File(s);
                FileInputStream fis = new FileInputStream(s);
                ZipEntry entry = new ZipEntry(file.getName());
                zout.putNextEntry(entry);
                // считываем содержимое файла в массив byte
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                // добавляем содержимое к архиву
                zout.write(buffer);
                // закрываем текущую запись для новой записи
                zout.closeEntry();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void delArchivedFiles(List<String> list) {
        for (String s : list) {
            File file = new File(s);
            file.deleteOnExit();
        }
    }

}