package br.com.finalcraft.pixelgenocide.commands;

import br.com.finalcraft.evernifecorespongy.FCSpongeUtil;
import br.com.finalcraft.evernifecorespongy.argumento.MultiArgumentos;
import br.com.finalcraft.evernifecorespongy.fancytext.FancyText;
import br.com.finalcraft.pixelgenocide.BetterPixelGenocide;
import br.com.finalcraft.pixelgenocide.PermissionNodes;
import br.com.finalcraft.pixelgenocide.config.ConfigManager;
import br.com.finalcraft.pixelgenocide.killthemall.PixelKilling;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class CoreCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource sender, CommandContext args) throws CommandException {

        MultiArgumentos argumentos = FCSpongeUtil.parseSpongeArgsToMultiArgumentos(args);

        if (!FCSpongeUtil.hasThePermission(sender, PermissionNodes.BETTERPIXELGENOCIDE_COMMAND_ADMIN)){
            return CommandResult.success();
        }

        switch (argumentos.get(0).toLowerCase()){
            case "help":
            case "?":
            case "":
                return help(sender,argumentos);
            case "clean":
                return clean(sender,argumentos);
            case "reload":
                return reload(sender,argumentos);
        }

        sender.sendMessage(Text.of("§cErro de parametros, por favor use §e/pixelgenocide help"));
        return CommandResult.success();
    }


    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Help
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static CommandResult help(CommandSource sender, MultiArgumentos argumentos){
        sender.sendMessage(Text.of("§6§m-------------§6( §b§lPixelGenocide§b §6)§m-------------"));

        FancyText.sendTo(sender, new FancyText("§3§l ▶ §a/pg clean [world]","§bLimpa todos os pokemons do servidor, ou caso especificado, de um mundo específico!","/pg clean", true));
        FancyText.sendTo(sender, new FancyText("§3§l ▶ §a/pg reload","§bAtualiza os pokemons na Whitelist|Blacklist do arquivo!","/pg reload", false));

        sender.sendMessage(Text.of("§3§oPasse o mouse em cima dos comandos para ver a descrição!"));
        sender.sendMessage(Text.of("§6§m-----------------------------------------------------"));
        return CommandResult.success();
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Clean
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static CommandResult clean(CommandSource sender, MultiArgumentos argumentos){

        if (FCSpongeUtil.isNotPlayer(sender)){
            return CommandResult.success();
        }

        if (argumentos.get(1).isEmpty()){
            FancyText.sendTo(sender, new FancyText("§3§l ▶ §a/pg clean (world)","§bLimpa todos os pokemons do servidor, ou caso especificado, de um mundo específico!","/pg clean", true));
            return CommandResult.success();
        }

        Optional<World> optWorld = Sponge.getServer().getWorld(argumentos.get(1).asString());

        if (!optWorld.isPresent()){
            sender.sendMessage(Text.of("§4§l ▶ §cNão existe nenhum mundo chamado [§e" + argumentos.get(1) + "§c]"));
            return CommandResult.success();
        }

        World world = optWorld.get();

        int quantity = PixelKilling.cleanWorld((net.minecraft.world.World) world);

        Sponge.getServer().getBroadcastChannel().send(Text.of("§c§l[O Ceifador]   §a" + quantity + " §epokemons (não relevantes) foram limpos do mapa!"));

        return CommandResult.success();
    }

    // -----------------------------------------------------------------------------------------------------------------------------//
    // Command Reload
    // -----------------------------------------------------------------------------------------------------------------------------//
    public static CommandResult reload(CommandSource sender, MultiArgumentos argumentos){

        ConfigManager.initialize(BetterPixelGenocide.instance);

        sender.sendMessage(Text.of("§2§l ▶ §aBetterPixelGenocide recarregado com sucesso!"));
        return CommandResult.success();
    }


}
