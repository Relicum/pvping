package com.relicum.duel.Kits;

import com.relicum.duel.Configs.LoadOutLoader;
import com.relicum.duel.Duel;
import com.relicum.pvpcore.Kits.LoadOut;
import com.relicum.utilities.Files.FileUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * KitManager
 *
 * @author Relicum
 * @version 0.0.1
 */
public class KitManager {

    protected final String BASE_DIR;

    protected Map<UUID, LoadOut> kitLoadOuts;

    protected Map<String, UUID> nameToUUID;

    protected LoadOutLoader loader;

    private Duel plugin;

    public KitManager(Duel plugin) {

        this.plugin = plugin;
        this.nameToUUID = new HashMap<>();
        BASE_DIR = plugin.getDataFolder().toString() + File.separator + "kits" + File.separator;

        try {
            FileUtils.createDirectory(BASE_DIR);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        this.kitLoadOuts = new HashMap<>();
        loader = new LoadOutLoader(BASE_DIR, "kits");
        if (!plugin.isFirstLoad()) {

            this.init();
        }
    }

    protected void init() {

        List<Path> paths;
        try {
            paths = FileUtils.getAllFilesInDirectory(BASE_DIR, "json");

            if (paths.size() > 0) {
                for (Path path : paths) {

                    loader.setPath(path);

                    LoadOut data = loader.load();

                    kitLoadOuts.put(data.getUuid(), data);
                    nameToUUID.put(data.getLoadoutName(), data.getUuid());
                    plugin.getLogger().info("Kit: " + data.getLoadoutName() + " successfully loaded");

                }
            }
            else

            {
                plugin.getLogger().info("No Kits were found for loading");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Add new Kit {@link LoadOut}.
     *
     * @param data the kit {@link LoadOut}
     * @return true if it was successfully added, false if there was a error.
     */
    public boolean addKit(LoadOut data) {

        try {
            loader.setPath(Paths.get(BASE_DIR + data.getLoadoutName() + ".json"));
            loader.save(data);
            LoadOut ki = loader.load();
            kitLoadOuts.put(ki.getUuid(), ki);
            nameToUUID.put(ki.getLoadoutName(), ki.getUuid());
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * Get a {@link LoadOut} by name.
     *
     * @param uuid the {@link UUID} of the kit
     * @return a cloned copy of {@link LoadOut}, or null if kit wasn't found
     */
    @Nullable
    public LoadOut getKit(UUID uuid) {

        if (containsKit(uuid)) {

            return kitLoadOuts.get(uuid);
        }
        return null;

    }

    /**
     * Get a {@link LoadOut} by name.
     *
     * @param uuid the {@link UUID} of the kit
     * @return a cloned copy of {@link LoadOut}, or null if kit wasn't found
     */
    @Nullable
    public LoadOut getKit(String name) {

        return getKit(nameToUUID.get(name));

    }

    /**
     * Get number of kits.
     *
     * @return the total number of stored kits
     */
    public int getNumberOfKits() {

        return kitLoadOuts.size();
    }


    /**
     * Has kits lets you know if there are any kits made.
     * <p>Useful to use to stop exceptions being thrown by calling this first to see if there are ANy kits at all.
     *
     * @return true and there are kits false and there are not.
     */
    public boolean hasKits() {

        return kitLoadOuts.size() > 0;
    }

    /**
     * Get all kits.
     *
     * @return an entry set of all kits
     */
    public Set<Map.Entry<UUID, LoadOut>> getAllKits() {

        return kitLoadOuts.entrySet();
    }

    /**
     * Get list of kit {@link UUID}.
     *
     * @return the list of kit {@link UUID}
     */
    public List<UUID> getKitUuids() {

        return kitLoadOuts.keySet().stream().collect(Collectors.toList());
    }

    /**
     * Get list of kit names.
     *
     * @return the list of kit names
     */
    public List<String> getKitNames() {

        return kitLoadOuts.values().stream().map(LoadOut::getLoadoutName).collect(Collectors.toList());
    }

    /**
     * Save the {@link LoadOut} object.
     * <p>The {@link LoadOut} is re added after the save has completed.
     *
     * @param name the  of the kit.
     */
    public void saveKit(String name) {

        saveKit(kitLoadOuts.get(nameToUUID.get(name)));

    }


    /**
     * Save the {@link LoadOut} object.
     * <p>The {@link LoadOut} is re added after the save has completed.
     *
     * @param uuid the {@link UUID} of the kit.
     */
    public void saveKit(UUID uuid) {

        saveKit(kitLoadOuts.get(uuid));

    }

    /**
     * Save kit.
     *
     * @param data the {@link LoadOut} object to save
     */
    public void saveKit(LoadOut data) {

        loader.setPath(Paths.get(BASE_DIR + data.getLoadoutName() + ".json"));
        loader.save(data);

        nameToUUID.put(data.getLoadoutName(), data.getUuid());

    }


    /**
     * Remove a {@link LoadOut} from the internal map.
     *
     * @param uuid the {@link UUID} of kit
     * @return the {@link LoadOut} that has been removed.
     */
    @Nullable
    public LoadOut removeKit(UUID uuid) {

        if (containsKit(uuid)) {

            return kitLoadOuts.remove(uuid);
        }
        return null;
    }

    /**
     * Remove a {@link LoadOut} from the internal map.
     *
     * @param uuid the {@link UUID} of kit
     * @return the {@link LoadOut} that has been removed.
     */
    @Nullable
    public LoadOut removeKit(String name) {

        if (containsKit(name)) {

            return kitLoadOuts.remove(nameToUUID.get(name));
        }
        return null;
    }

    @Nullable
    public LoadOut deleteLoadOutFile(UUID name) {

        if (deleteFile(kitLoadOuts.get(name).getLoadoutName())) {
            LoadOut loadOut = kitLoadOuts.remove(name);
            nameToUUID.remove(loadOut.getLoadoutName());
            return loadOut;
        }

        else {
            return null;
        }
    }

    @Nullable
    public LoadOut deleteLoadOutFile(String name) {

        if (deleteFile(name)) {
            LoadOut loadOut = kitLoadOuts.remove(nameToUUID.get(name));
            nameToUUID.remove(loadOut.getLoadoutName());
            return loadOut;
        }

        else {
            return null;
        }
    }

    /**
     * Checks to see if a {@link LoadOut} is stored.
     *
     * @param name the kit name
     * @return true if it is, false if not.
     */
    public boolean containsKit(UUID name) {

        return kitLoadOuts.containsKey(name);
    }

    /**
     * Checks to see if a {@link LoadOut} is stored.
     *
     * @param name the kit name
     * @return true if it is, false if not.
     */
    public boolean containsKit(String name) {

        return kitLoadOuts.containsKey(nameToUUID.get(name));
    }

    /**
     * Save and remove all {@link LoadOut} clearing the maps on completion.
     */
    public void saveAndRemoveAll() {

        plugin.getLogger().info("Saving all Kits and Data....");

        kitLoadOuts.values().forEach(this::saveKit);
        nameToUUID.clear();
        kitLoadOuts.clear();
        kitLoadOuts = null;
        nameToUUID = null;
    }

    /**
     * Gets the instance of {@link LoadOutLoader}.
     *
     * @return the {@link loader} gson object.
     */
    public LoadOutLoader getkitLoader() {
        return loader;
    }

    /**
     * Gets the base directory that the kits are saved to.
     *
     * @return the base directory.
     */
    public String getBaseDir() {
        return BASE_DIR;
    }

    /**
     * Delete file.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean deleteFile(String name) {

        try {
            return Files.deleteIfExists(Paths.get(getBaseDir() + name + ".json"));
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public Duel getPlugin() {

        return plugin;
    }
}
