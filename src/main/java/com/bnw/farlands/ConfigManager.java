package com.bnw.farlands;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.nio.file.Paths;



/**
 * Configuration manager of this mod, which reads from and writes to this mod's
 * configuration file.
 * <p>
 * This is a Singleton class. Only one instance of this class may be created
 * per runtime.
 * <p>
 * The methods that change this mod's configuration do not automatically write
 * those changes to the configuration file on disk. Instead, they only update
 * the configuration in memory. To write any changes, use the {@link #save()}
 * method.
 *
 * @author Leo
 */
public class ConfigManager {

    private static final double DEFAULT_SCALE = 684.412D; // this is vanilla scale that is used by default in the config

    private final BooleanValue farlandsEnabled;

    private final DoubleValue overworldScale;

    private final DoubleValue netherScale;

    private final DoubleValue endScale;


    /**
     * The only instance of this class
     */
    private static final ConfigManager INSTANCE;

    /**
     * The {@link ForgeConfigSpec} instance for this mod's configuration
     */
    private static final ForgeConfigSpec SPEC;

    /**
     * {@link Path} to the configuration file of this mod
     */
    private static final Path CONFIG_PATH =
            Paths.get("config", FarlandsForge.MODID + ".toml");

    static {
        Pair<ConfigManager, ForgeConfigSpec> specPair =
                new ForgeConfigSpec.Builder().configure(ConfigManager::new);
        INSTANCE = specPair.getLeft();
        SPEC = specPair.getRight();
        CommentedFileConfig config = CommentedFileConfig.builder(CONFIG_PATH)
                .sync()
                .autoreload()
                .writingMode(WritingMode.REPLACE)
                .build();
        config.load();
        config.save();
        SPEC.setConfig(config);
    }



    /**
     * Implementation of Singleton design pattern, which allows only one
     * instance of this class to be created.
     */
    private ConfigManager(ForgeConfigSpec.Builder configSpecBuilder) {

        farlandsEnabled = configSpecBuilder
                .translation("farlandsforge.configGui.farlandsEnabled.title")
                .define("farlandsEnabled", true);

        overworldScale = configSpecBuilder
                .translation("farlandsforge.configGui.overworldScale.title")
                .defineInRange("overworldScale", DEFAULT_SCALE, 0, Double.MAX_VALUE);
        netherScale = configSpecBuilder
                .translation("farlandsforge.configGui.netherScale.title")
                .defineInRange("netherScale", DEFAULT_SCALE, 0, Double.MAX_VALUE);
        endScale = configSpecBuilder
                .translation("farlandsforge.configGui.endScale.title")
                .defineInRange("endScale", DEFAULT_SCALE, 0, Double.MAX_VALUE);
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    public static ConfigManager getInstance() {
        return INSTANCE;
    }



    // Query Operations

    public boolean isFarlandsEnabled() {
        return farlandsEnabled.get();
    }

    public double getOverworldScale() {
        return overworldScale.get();
    }

    public double getNetherScale() {
        return netherScale.get();
    }

    public double getEndScale() {
        return endScale.get();
    }


    // Modification Operations

    public void setFarlandsEnabled(boolean newValue) {
        farlandsEnabled.set(newValue);
    }


    // these are unused... these can be used when we add more settings to gui
    public void setOverworldScale(double newValue) {
        overworldScale.set(validate(newValue));
    }
    public void setNetherScale(double newValue) {

        netherScale.set(validate(newValue));
    }
    public void setEndScale(double newValue) {

        netherScale.set(validate(newValue));
    }

    // Validations

    public double validate(double value){ // too check if the entered value is good or bad... it will crash game tho if its bad
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("invalid farlandsforge config value for scale: " +
                    + value);
        }
        return value;
    }


    /**
     * Saves changes to this mod's configuration.
     */
    public void save() {
        SPEC.save();
    }
}