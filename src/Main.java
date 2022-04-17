import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    private static final String PATH = "D://Alexey/JavaCore/Games/savegames";

    public static void main(String[] args) {
        openZip(PATH + "/savegame.zip", PATH);

        System.out.println(openProgress(PATH + "/" + "savegame1.dat"));
        System.out.println(openProgress(PATH + "/" + "savegame2.dat"));
        System.out.println(openProgress(PATH + "/" + "savegame3.dat"));
    }

    private static void openZip(String pathZip, String path) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(path + "/" + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static GameProgress openProgress(String savePath) {
        GameProgress gameProgress = null;

        try (FileInputStream fis = new FileInputStream(savePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameProgress;
    }
}
