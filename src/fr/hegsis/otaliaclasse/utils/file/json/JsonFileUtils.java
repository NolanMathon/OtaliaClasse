package fr.hegsis.otaliaclasse.utils.file.json;

import java.io.*;

public class JsonFileUtils {

    public static void createJsonFile(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    // Use to save a json file
    public static void saveJson(File file, String text) {
        try {
            createJsonFile(file);
            final FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Use to load content from json file
    public static String loadJson(File file) {
        if (!file.exists()) return "";

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final StringBuilder text = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                text.append(line);
            }

            reader.close();
            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
