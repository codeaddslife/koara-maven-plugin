package com.codeaddslife.koara.maven;

import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConvertMojoTest {

    private ConvertMojo mojo;
    private File srcDir;
    private File targetDir;
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule public ExpectedException ex = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        srcDir = new File("src/test/resources");
        targetDir = temporaryFolder.newFolder("target");
        mojo = new ConvertMojo();
        mojo.setSourceDirectory(new File("src/test/resources"));
        mojo.setOutputDirectory(targetDir);
    }

    @Test
    public void execute() throws Exception {
        mojo.execute();

        File[] files = targetDir.listFiles();
        assertEquals(1, files.length);
        assertEquals("input.htm", files[0].getName());
        assertEquals("<!DOCTYPE html>\n<html>\n  <body>\n    <p>Hello World!</p>  </body>\n</html>\n", readFileAsString(files[0]));
    }

    @Test
    public void executeWithOutputFormatXml() throws Exception {
        mojo.setOutputFormat("xml");
        mojo.execute();

        File[] files = targetDir.listFiles();
        assertEquals(1, files.length);
        assertEquals("input.xml", files[0].getName());
        assertTrue(readFileAsString(files[0]).startsWith("<?xml version"));
    }

    @Test
    public void executeWithOutputFormatUnknown() throws Exception {
        ex.expect(MojoFailureException.class);
        ex.expectMessage("Outputformat 'bla' is unknown. Possible values: html5, xml");
        mojo.setOutputFormat("bla");
        mojo.execute();
    }

    @Test
    public void executeWithModulesSet() throws Exception {
        mojo.setModules(" HEADINGS");
        mojo.execute();
        assertEquals("<!DOCTYPE html>\n<html>\n  <body>\n    Hello World!  </body>\n</html>\n", readFileAsString(targetDir.listFiles()[0]));
    }

    @Test
    public void executeToDirNotSet() throws Exception {
        ex.expect(MojoFailureException.class);
        ex.expectMessage("outputDirectory must be set.");
        mojo.setOutputDirectory(null);
        mojo.execute();
    }

    private String readFileAsString(File file) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

}
