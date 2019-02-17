package br.com.finalcraft.pixelgenocide;

import br.com.finalcraft.pixelgenocide.commands.CommandRegisterer;
import br.com.finalcraft.pixelgenocide.config.ConfigManager;
import br.com.finalcraft.pixelgenocide.threads.ClearPokemonThread;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

@Plugin(id = "evernifebetterpixelgenocide",
		name = "BetterPixelGenocide",
		version = "1.0.1a",
		authors = "EverNife",
		dependencies = {
				@Dependency(id = "evernifecorespongy"),
				@Dependency(id = "pixelmon")
		})
public class BetterPixelGenocide {

	public static PluginContainer instance;

	@Inject private Logger logger;
	public Logger getLogger(){
		return logger;
	}

	public static void info(String msg){
		instance.getLogger().info("[Info] " + msg);
	}

	@Listener
	public void onServerStart(GameStartingServerEvent event) {
		instance = Sponge.getPluginManager().fromInstance(this).get();

		info("Iniciando o Plugin...");

		info("Carregando configurações...");
		ConfigManager.initialize(instance);

		info("Registrando comandos...");
		CommandRegisterer.registerCommands(instance);

		info("Plugin iniciado com sucesso!");
		ClearPokemonThread.initialize();
	}
}
