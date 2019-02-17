package br.com.finalcraft.pixelgenocide.killthemall;

import br.com.finalcraft.pixelgenocide.PermissionNodes;
import br.com.finalcraft.pixelgenocide.config.data.KillSettings;
import com.flowpowered.math.vector.Vector3d;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PixelKilling {

    private static List<Vector3d> specialPlayersLocation = new ArrayList<Vector3d>();

    public static int cleanAllWorlds() {
        int quantity = 0;
        for (org.spongepowered.api.world.World world : Sponge.getServer().getWorlds()) {
            quantity += cleanWorld((World) world);
        }
        return quantity;
    }

    public static int cleanWorld(World world) {
        specialPlayersLocation.clear();
        for (Player player : Sponge.getServer().getOnlinePlayers()){
            if (player.hasPermission(PermissionNodes.BETTERPIXELGENOCIDE_KEEP_POKEMONS_NEARBY)) {
                specialPlayersLocation.add(player.getPosition());
            }
        }

        int quantity = 0;
        for (Entity entity : world.loadedEntityList) {
            if (entity instanceof EntityPixelmon) {
                EntityPixelmon pixelmon = (EntityPixelmon) entity;
                if (!(!pixelmon.canDespawn
                        || pixelmon.hasOwner()
                        || pixelmon.battleController != null
                        || pixelmon.getPokemonData().isInRanch()
                        || shouldKeepPokemon(pixelmon))) {

                    pixelmon.unloadEntity();
                    quantity++;
                }
            }
        }
        return quantity;
    }

    private static boolean shouldKeepPokemon(EntityPixelmon pixelmon) {
        String name = pixelmon.getPokemonName();
        if (KillSettings.whitelist.contains(name)) return true;
        if (KillSettings.blacklist.contains(name)) return false;

        return EnumSpecies.legendaries.contains(name)
                || pixelmon.isBossPokemon()
                || pixelmon.getPokemonData().isShiny()
                || pixelmon.getTags().contains("ultrabeast")
                || pixelmon.getPokerus().isPresent()
                || isNearSpecialPlayer(pixelmon);
    }

    private static boolean isNearSpecialPlayer(net.minecraft.entity.Entity entity) {
        if (specialPlayersLocation.size() > 0) {
            Vector3d pokemonLocation = new Vector3d(entity.posX, entity.posY, entity.posZ);
            for (Vector3d specialPlayerLocation : specialPlayersLocation) {
                if (pokemonLocation.distance(specialPlayerLocation) <= 150) {
                    return true;
                }
            }
        }
        return false;
    }

}
