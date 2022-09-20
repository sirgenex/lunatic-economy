package br.com.lunaticmc.economy.menu;

import br.com.lunaticmc.economy.object.EcoPlayer;
import br.com.lunaticmc.economy.controller.EcoPlayerController;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TopMenu {

    private static final HashMap<Integer, EcoPlayer> top = new HashMap<>();
    private static final HashMap<Integer, Integer> slots = new HashMap<>();

    public static void load(FileConfiguration c){
        for(int i = 1 ; i <= 10 ; i++)
            slots.put(i, c.getInt("top.slots."+i));
    }

    public void update() {
        top.clear();
        for(int i = 1; i <= 10; i ++) {
            try {
                Map.Entry<String, EcoPlayer> e = EcoPlayerController.getInstance().top.pollFirstEntry();
                top.put(slots.get(i), e.getValue());
            }catch(Exception ignored){}
        }
    }

    public void open(Player p){
        if(!top.isEmpty()) {

        } else p.sendMessage("§cNão há nenhuma conta registrada no banco de dados.");
    }

}
