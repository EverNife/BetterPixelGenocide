package br.com.finalcraft.pixelgenocide.threads;

import br.com.finalcraft.pixelgenocide.BetterPixelGenocide;
import br.com.finalcraft.pixelgenocide.killthemall.PixelKilling;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;

public class ClearPokemonThread extends Thread {

    public ClearPokemonThread() {
        setName("BetterPixelmonGenocide - ClearPokemonThread");
    }

    public static ClearPokemonThread clearPokemonThread;

    public static void initialize(){
        if (clearPokemonThread == null){
            clearPokemonThread = new ClearPokemonThread();
            clearPokemonThread.start();
        }
    }

    public static void shutdown(){
        if (clearPokemonThread != null) {
            clearPokemonThread.interrupt();
        }
        clearPokemonThread = null;
    }


    public boolean sleepFor(int millis){
        try {
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException ignored) {}
        return false;
    }


    private static void broadcastMessage(String message){
        Sponge.getServer().getBroadcastChannel().send(Text.of(message));
    }

    @Override
    public void run() {
        try {
            //Server is still starting, dont need to do anything now :/
            if ( !sleepFor(60)) return;                     // 60 Segundos

            while (true) {
                if ( !sleepFor(1000 * 60 * 29)) return;     // 29 Min

                broadcastMessage("§c§l[O Ceifador]   §cTodos os pokemons que não são especiais §b(shiny, boses, lend, etc) §cserão removidos em 1 minuto.");
                if ( !sleepFor(1000 * 55)) return;          // 55 Segundos

                for (int i = 5; i > 0; i--){
                    broadcastMessage("§c§l[O Ceifador]   §cTodos os pokemons (não relevantes) serão removidos em " + i + " segundo" + (i == 1 ? "" : "s")  +  ".");
                    if ( !sleepFor(1000)) return;           // 1 segundo
                }

                Sponge.getScheduler().createTaskBuilder().execute(new Runnable() {
                    @Override
                    public void run() {
                        int quantity = PixelKilling.cleanAllWorlds();
                        Sponge.getServer().getBroadcastChannel().send(Text.of("§c§l[O Ceifador]   §a" + quantity + " §epokemons (não relevantes) foram limpos do mapa!"));
                    }
                }).submit(BetterPixelGenocide.instance);
            }


        }catch (Exception ignored){}
    }
}
