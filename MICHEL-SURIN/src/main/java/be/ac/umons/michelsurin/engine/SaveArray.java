package be.ac.umons.michelsurin.engine;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaveArray {

    private Path path = Paths.get("Save.txt");
    private File file = path.toFile();


    public void Save(ArrayList<String> array) throws IOException {
        File selectedFile = file;
        if (selectedFile != null) {
            FileOutputStream file = new FileOutputStream(selectedFile);
            BufferedOutputStream buf = new BufferedOutputStream(file);
            ObjectOutputStream stream = new ObjectOutputStream(buf);
            stream.writeObject(array);
            stream.close();  // Thanks Mr. Pierre HAUWEELE! :D


        }
        else {
            throw new IllegalArgumentException("File does not exist!");
        }
    }
}
