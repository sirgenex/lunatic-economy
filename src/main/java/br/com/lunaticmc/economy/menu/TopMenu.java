package br.com.lunaticmc.economy.menu;

import br.com.lunaticmc.economy.Economy;
import br.com.lunaticmc.economy.builder.ConfigItemBuilder;
import br.com.lunaticmc.economy.builder.InventoryBuilder;
import br.com.lunaticmc.economy.builder.SkullBuilder;
import br.com.lunaticmc.economy.object.EcoPlayer;
import br.com.lunaticmc.economy.object.controller.EcoPlayerController;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopMenu {

    @Getter private static final TopMenu instance = new TopMenu();

    private final HashMap<Integer, ItemStack> top = new HashMap<>();

    private final ItemStack decoration = new ConfigItemBuilder("top.decoration").setName().get();

    public final String title = Economy.getInstance().getConfig().getString("top.title");
    private final int hotbars = Economy.getInstance().getConfig().getInt("top.hotbars");
    private final String name = Economy.getInstance().getConfig().getString("top.item.name");
    private final List<String> lore = Economy.getInstance().getConfig().getStringList("top.item.lore");
    private final HashMap<Integer, Integer> slots = new HashMap<>();

    public void load(){

        if(slots.isEmpty()){
            for(int i = 1 ; i <= 10 ; i++) slots.put(i, Economy.getInstance().getConfig().getInt("top.slots."+i));
        }

    }

    public void update() {

        top.clear();
        for(int i = 1; i <= 10; i ++) {
            try {
                Map.Entry<String, EcoPlayer> e = EcoPlayerController.getInstance().top.pollFirstEntry();
                top.put(slots.get(i), new SkullBuilder(e.getKey(), e.getValue().getBalance(), i).setName(name).setLore(lore).get());
            }catch(Exception ignored){}
        }

    }

    public void open(Player p){
        if(!top.isEmpty()) new InventoryBuilder(hotbars, title).addItems(top).decorate(decoration).open(p);
        else p.sendMessage("§cNão há nenhuma conta registrada no banco de dados.");
    }

}
