package br.com.uburu.spring.searchTools;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;

public class SearchEngineTest {

    public SearchEngine searchEngine;

    @Test
    void getFilesTest() {
        final File file = new File("src\\test\\java\\br\\com\\uburu\\spring\\searchTools\\test.txt");
        String path = file.getAbsolutePath();
        String search = "Uburu AND utilizada";

        searchEngine = new SearchEngine(path, search);
        var files = searchEngine.getFiles();

        assertNotNull(files);
    }

    @Test
    void getFilesFolderTest() {
        final File file = new File("src\\test\\java\\br\\com\\uburu\\spring\\searchTools");
        String path = file.getAbsolutePath();
        String search = "Uburu AND utilizada";

        searchEngine = new SearchEngine(path, search);
        var files = searchEngine.getFiles();

        assertNotNull(files);
    }
    
}
