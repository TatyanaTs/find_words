package find_words;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class find_words {

    public static void main(String[] args) {
        ArrayList<File> fileList = new ArrayList<>();
        searchFiles(new File(args[1]), fileList);
        for (File file : fileList) {
            parsing(file, args[0], args[2]);
        }
    }

    private static void searchFiles(File rootFile, List<File> fileList) {
        if (rootFile.isDirectory()) {
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        searchFiles(file, fileList);
                    } else {
                        fileList.add(file);
                    }
                }
            }
        }
    }

    public static void parsing(File f, String word, String file_name) {
        try (BufferedReader buff = new BufferedReader(new FileReader(f))) {
            String line;
            String lineSep = System.getProperty("line.separator");
            String dir = !file_name.contains(".") ? file_name : file_name.substring(0, file_name.indexOf("."));
            String theDir = f.getParent() + "\\" + dir;
            Path path = Paths.get(theDir);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            FileWriter writer = new FileWriter(path + "\\" + file_name, true);
            while ((line = buff.readLine()) != null) {
                if (line.contains(word)) {
                    writer.write(line + lineSep);
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}