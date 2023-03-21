package br.com.uburu.spring.searchTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

public class Tracker {

    private List<JSONObject> files;

    public Tracker() {
        files = new ArrayList<>();
    }

    /**
     * Realiza a pesquisa em todos os arquivos de um determinado diretório
     * @param String folderName
     * @param String search
     * @throws FileNotFoundException
     */
    public void listFilesForFolder(final File folder, String search) throws FileNotFoundException {
        String root = folder.getAbsolutePath() + "\\";

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, search);
            } else {
                searchInFile(root + fileEntry.getName(), search);
            }
        }
    }

    /**
     * Realiza a pesquisa em um arquivo específico
     * @param String fileName
     * @param String search
     * @throws FileNotFoundException
     */
    public void searchInFile(String fileName, String search) throws FileNotFoundException {
        final File file = new File(fileName);
        final Scanner scanner = new Scanner(file);

        int lineCount = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.contains(search)) {
                final FoundFile foundFile = new FoundFile();
                foundFile.setFilePath(fileName);
                foundFile.setLine(lineCount);

                this.files.add(foundFile.toJSON());
            }
            
            lineCount ++;
        }

        scanner.close();
    }

    /**
     * Retorna os arquivos encontrados
     * @return List<JSONObject>
     */
    public List<JSONObject> getFiles() {
        return files;
    }

}