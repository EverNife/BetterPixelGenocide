package br.com.finalcraft.pixelgenocide.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;


public class CommandRegisterer {

    public static void registerCommands(PluginContainer pluginInstance) {

        CommandSpec commandCoreCommand = CommandSpec.builder()
                .description(Text.of("Limpa todos os pokemons do mapa ou de algum mundo específico!"))
                .arguments(GenericArguments.none(),
                        GenericArguments.optional(GenericArguments.remainingRawJoinedStrings(Text.of("allArgs"))))
                .executor(new CoreCommand())
                .build();

        Sponge.getCommandManager().register(pluginInstance, commandCoreCommand        , "betterpixelgenocide","pixelgenocide","bpg");
    }

}
