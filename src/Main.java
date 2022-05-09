import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String installPath = "C://Games/JCoreHW3/Games";
        String savesFolder = installPath + "/savegames";

        openZip(savesFolder + "/zip.zip", savesFolder);

        System.out.println("Загружаем сохранение...");
        GameProgress loadedProgress = openProgress(savesFolder + "/save1.dat");

        System.out.println(loadedProgress!=null?(loadedProgress.toString() + " загружен успешно"):"возникла ошибка");

    }

    private static void openZip(String zipFilePath, String pathToUnzip) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                FileOutputStream fout = new FileOutputStream(pathToUnzip + "/" + entry.getName());
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                };
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static GameProgress openProgress(String pathToProgress) {
        try (FileInputStream fis = new FileInputStream(pathToProgress);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
