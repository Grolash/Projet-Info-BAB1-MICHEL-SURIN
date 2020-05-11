package be.ac.umons.michelsurin.engine;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import me.tongfei.progressbar.*;

/**
 * Save and load games, has path in memory
 */
public class SaverLoader {

    /**
     * Path to the save file in engine package.
     */
    private static final Path path = Paths.get("./misc/saves/Save.data");
    /**
     * File in memory.
     */
    private static File file = path.toFile();

    /**
     * Save method. Writes a game object in a file following the path in attribute of the class.
     * @param game a Game object as in engine package.
     * @throws IOException if the process is interrupted.
     */
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
            throw new FileNotFoundException("File does not exist!"); //Should not happen.
        }
    }

    /**
     * Load method. Reads and returns a game object in a file following the path in the attribute of the class.
     * @return a Game object as in engine package.
     * @throws IOException if the process is interrupted.
     * @throws ClassNotFoundException if the method found no Game.
     */
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
