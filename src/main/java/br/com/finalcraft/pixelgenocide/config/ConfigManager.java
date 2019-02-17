package br.com.finalcraft.pixelgenocide.config;

import br.com.finalcraft.evernifecorespongy.config.Config;
import br.com.finalcraft.pixelgenocide.config.data.KillSettings;
import org.spongepowered.api.plugin.PluginContainer;

public class ConfigManager {

    public static Config mainConfig;

    public static Config getMainConfig(){
        return mainConfig;
    }

    public static void initialize(PluginContainer instance){
        mainConfig  = new Config(instance,"config.yml"      ,false);

        KillSettings.initialize();
    }
}
