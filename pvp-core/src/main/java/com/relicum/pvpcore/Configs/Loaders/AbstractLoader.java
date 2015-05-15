package com.relicum.pvpcore.Configs.Loaders;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.xlib.gson.Gson;
import com.massivecraft.massivecore.xlib.gson.stream.JsonReader;
import com.massivecraft.massivecore.xlib.gson.stream.JsonWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * AbstractLoader used to create Type specific Gson loaders and savers.
 *
 * @author Relicum
 * @version 0.0.1
 */
public abstract class AbstractLoader<T> {

    protected static final Gson GSON = MassiveCore.gson;
    @Getter
    protected Type token;
    @Getter
    @Setter
    protected Path path;

    @Getter
    @Setter
    protected String dirPath;


    /**
     * Instantiates a new Gson loader.
     *
     * @param paramPath the the full path including file name.
     */
    public AbstractLoader(String paramPath) {

        this.path = Paths.get(paramPath);
    }

    /**
     * Instantiates a new Gson loader.
     *
     * @param directoryPath the directory the file or files are in.
     * @param fileName      the file name, do not include the extension as .json is automatically added
     */
    public AbstractLoader(String directoryPath, String fileName) {
        this.dirPath = directoryPath;
        this.path = Paths.get(directoryPath + fileName + ".json");
    }

    /**
     * This should create a new {@link com.google.gson.reflect.TypeToken} and
     * set the field {@link #token} to the objects Type.
     */
    public abstract void setToken();

    /**
     * Save the object to file.
     *
     * @param type the object to save.
     */
    public void save(T type) {

        if (!Files.exists(getPath())) {
            try {
                Files.createFile(getPath());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (JsonWriter writer = new JsonWriter(Files.newBufferedWriter(getPath(), Charset.defaultCharset()))) {
            writer.setIndent("    ");
            GSON.toJson(type, getToken(), writer);
            writer.flush();
            writer.close();

        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Load the object from file
     *
     * @return instance of the object
     */
    public T load() {

        if (!Files.exists(getPath())) {
            try {
                Files.createFile(getPath());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }


        T obj = null;
        try (JsonReader reader = new JsonReader(Files.newBufferedReader(getPath(), Charset.defaultCharset()))) {
            obj = GSON.fromJson(reader, getToken());
            reader.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * Change the file name to be loaded or saved, it will use the directory that is currently set.
     *
     * @param fileName the file name,do not include the extension as .json is automatically added
     */
    public void newFile(String fileName) {
        setPath(Paths.get(dirPath + fileName + ".json"));
    }

}
