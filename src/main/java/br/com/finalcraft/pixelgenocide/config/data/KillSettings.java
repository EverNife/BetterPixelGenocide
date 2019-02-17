package br.com.finalcraft.pixelgenocide.config.data;

import br.com.finalcraft.pixelgenocide.config.ConfigManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KillSettings {

    public static List<String> whitelist = new ArrayList<>();
    public static List<String> blacklist = new ArrayList<>();

    public static void initialize(){

        if (ConfigManager.getMainConfig().getInt("Version",0) != 1){
            ConfigManager.getMainConfig().setValue("Settings.whitelistPokemons", Arrays.asList(""));
            ConfigManager.getMainConfig().setValue("Settings.blacklistPokemons", Arrays.asList("Zubat","Geodude","Caterpie"));
            ConfigManager.getMainConfig().save();
        }

        whitelist   = ConfigManager.getMainConfig().getStringList("Settings.whitelistPokemons");
        blacklist   = ConfigManager.getMainConfig().getStringList("Settings.blacklistPokemons");
    }

}
