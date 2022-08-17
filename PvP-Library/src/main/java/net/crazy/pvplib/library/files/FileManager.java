package net.crazy.pvplib.library.files;

import net.crazy.pvplib.library.MCLogger;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    private final File file;

    public FileManager(File file) {
        this.file = file;
    }

    public FileManager(String path) {
        this.file = new File(path);
    }

    /**
     * @return files existence
     */
    public boolean fileExists() {
        return file.exists();
    }

    /**
     * Creates the file or resets it, if it already exists
     */
    public void createFile() {
        try {
            file.createNewFile();
        } catch (IOException exception) {
            MCLogger.log(MCLogger.LogType.ERROR, exception.getMessage());
        }
    }

    /**
     * Gets the content of the file as string
     * Use #getJSON for JSON response
     */
    public String getContent() {
        StringBuilder builder = new StringBuilder();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                builder.append(scanner.nextLine());
        } catch (IOException exception) {
            MCLogger.log(MCLogger.LogType.ERROR, exception.getMessage());
        }
        return builder.toString();
    }

    /**
     * Returns the content of the file as JSON
     */
    public JSONObject getJSON() {
        return new JSONObject(getContent());
    }

    /**
     * Writes to the file
     * IMPORTANT: This replaces everything in the file! If you just want to append something, use #append
     * @param content - entire content of the file
     */
    public void write(String content) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException exception) {
            MCLogger.log(MCLogger.LogType.ERROR, exception.getMessage());
        }
    }

    /**
     * Appends a string to the content of the file
     */
    public void append(String append) {
        String content = getContent();
        write(content + append);
    }
}
