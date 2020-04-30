package be.ac.umons.michelsurin.engine;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SaveArray {

    private ArrayList<String> array;
    private Path path = Paths.get("");
    private File file = path.toFile();

    public SaveArray(ArrayList<String> array) {
        this.array = array;
    }


    public void Save() throws IOException {
        File selectedFile = file;
        if (selectedFile != null) {
            FileOutputStream file = new FileOutputStream(selectedFile);
            BufferedOutputStream buf = new BufferedOutputStream(file);
            ObjectOutputStream stream = new ObjectOutputStream(buf);
            stream.writeObject(array);
            stream.close();  // Thanks Mr. Pierre HAUWEELE! :D


        }
    }
}
