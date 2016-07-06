package io.koara.maven;

import io.koara.Parser;
import io.koara.ast.Document;
import io.koara.html.Html5Renderer;
import io.koara.xml.XmlRenderer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Mojo(name = "convert", requiresProject = false)
public class ConvertMojo extends AbstractMojo {

    @Parameter(property = "koara.sourceDirectory", defaultValue = "${basedir}/src/main/koara")
    private File sourceDirectory;

    @Parameter(property = "koara.outputDirectory", defaultValue = "${project.build.directory}/generated-docs")
    private File outputDirectory;

    @Parameter(property = "koara.outputFormat", defaultValue = "html5")
    private String outputFormat;

    @Parameter(property = "koara.modules", defaultValue = "paragraphs,headings,lists,links,images,formatting,blockquote,code")
    private String modules;

    private String[] formattedModules = {"paragraphs", "headings", "lists", "links", "images", "formatting", "blockquote", "code"};

    public void execute() throws MojoExecutionException, MojoFailureException {
        validateAttributes();

        try {
            for (File file : sourceDirectory.listFiles()) {
                if (file.getName().endsWith(".kd")) {
                    parse(sourceDirectory, file.getName());
                }
            }
        } catch(IOException e) {
            throw new MojoFailureException("IOException: " + e.getMessage());
        }
    }

    private void parse(File srcdir, String file) throws IOException, MojoFailureException {
        Parser parser = new Parser();
        parser.setModules(formattedModules);
        Document document = parser.parseFile(new File(srcdir, file));
        String name = file.substring(0, file.length() - 3);
        if(outputFormat == null || outputFormat.equalsIgnoreCase("html5")) {

            Html5Renderer renderer = new Html5Renderer();
            document.accept(renderer);
            writeToFile(new File(outputDirectory, name + ".htm"), renderer.getOutput());
        } else if(outputFormat.equalsIgnoreCase("xml")) {
            XmlRenderer renderer = new XmlRenderer();
            renderer.setDeclarationTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            document.accept(renderer);
            writeToFile(new File(outputDirectory, name + ".xml"), renderer.getOutput());
        } else {
            throw new MojoFailureException("Outputformat '" + outputFormat + "' is unknown. Possible values: html5, xml");
        }
    }

    private void writeToFile(File file, String content) throws MojoFailureException {
        try {
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
        } catch(IOException e) {
            throw new MojoFailureException("Could not write to file " + file.getAbsolutePath() + ".");
        }
    }

    private void validateAttributes() throws MojoFailureException {
        if (outputDirectory == null) {
            throw new MojoFailureException("outputDirectory must be set.");
        }

        if(modules != null) {
            String[] temp = modules.split(",");
            for(int i=0; i < temp.length; i++) {
                temp[i] = temp[i].trim().toLowerCase();
            }
            formattedModules = temp;
        }
    }


    public void setSourceDirectory(File sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }
}