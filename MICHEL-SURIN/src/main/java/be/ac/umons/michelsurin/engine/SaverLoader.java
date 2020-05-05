package be.ac.umons.michelsurin.engine;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Save and load games, has path in memory
 */
public class SaverLoader {

    private static Path path = Paths.get("./src/main/java/be/ac/umons/michelsurin/engine/Save.txt");
    private static File file = path.toFile();


    public static void save(Game game) throws IOException {
        File selectedFile = file;
        if (selectedFile != null) {
            FileOutputStream file = new FileOutputStream(selectedFile);
            BufferedOutputStream buf = new BufferedOutputStream(file);
            ObjectOutputStream stream = new ObjectOutputStream(buf);
            stream.writeObject(game);
            stream.close();  // Thanks Mr. Pierre HAUWEELE! :D


        }
        else {
            throw new FileNotFoundException("File does not exist!");
        }
    }

    public static Game load() throws IOException, ClassNotFoundException {
        File selectedFile = file;
        Game game;
        if (selectedFile != null) {
            FileInputStream file = new FileInputStream(selectedFile);
            BufferedInputStream buf = new BufferedInputStream(file);
            ObjectInputStream stream = new ObjectInputStream(buf);

            game = (Game) stream.readObject();
            stream.close();
            return game;

        }
        else {
            throw new FileNotFoundException("File does not exist!");
        }
    }
}
