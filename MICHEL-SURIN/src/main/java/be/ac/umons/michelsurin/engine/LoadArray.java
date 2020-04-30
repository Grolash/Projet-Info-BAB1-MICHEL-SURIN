package be.ac.umons.michelsurin.engine;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LoadArray {

    private ArrayList<String> arrayList = new ArrayList<>();
    private Path path = Paths.get("");
    private File file = path.toFile();

    public ArrayList Load() throws IOException, ClassNotFoundException {
        File selectedFile = file;
        if (selectedFile != null) {
            FileInputStream file = new FileInputStream(selectedFile);
            BufferedInputStream buf = new BufferedInputStream(file);
            ObjectInputStream stream = new ObjectInputStream(buf);
            arrayList = (ArrayList<String>) stream.readObject();
            stream.close();
            return arrayList;
        }
        else {
            throw new IllegalArgumentException("File does not exist!");
        }


    }
}
