package br.com.lunaticmc.economy.builder;

import br.com.lunaticmc.economy.manager.FormatManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class SkullBuilder {

    private final ItemStack item;
    private final double balance;
    private final String player;
    private final int position;

    public SkullBuilder(String owner, double balance, int position){
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
        meta.setOwner(owner);
        itemStack.setItemMeta(meta);
        this.item = itemStack;
        this.balance = balance;
        this.player = owner;
        this.position = position;
    }

    public SkullBuilder setName(String name){
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name.replace("&", "§").replace("{position}", String.valueOf(position)).replace("{player}", player));
        this.item.setItemMeta(meta);
        return this;
    }

    public SkullBuilder setLore(List<String> lines){
        ArrayList<String> lore = new ArrayList<>();
        lines.forEach(line -> lore.add(line.replace("&", "§").replace("{amount}", FormatManager.getInstance().format(balance))));
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(lore);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack get(){
        return this.item;
    }

}
