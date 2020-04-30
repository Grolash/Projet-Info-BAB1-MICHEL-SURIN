package be.ac.umons.michelsurin.engine;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SaveText {
    private PrintWriter out = new PrintWriter("Save.txt");
    private String saveText;

    public SaveText() throws FileNotFoundException {
        saveText= "";
        out.println(saveText);
        out.close();
    }
}
