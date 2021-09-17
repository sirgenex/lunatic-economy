package br.com.lunaticmc.economy.builder;

import br.com.lunaticmc.economy.Economy;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class ConfigItemBuilder {

    private final ItemStack item;
    private final FileConfiguration c = Economy.getInstance().getConfig();
    private final String path;

    public ConfigItemBuilder(String path){
        String[] split = c.getString(path+".item").split(";");
        int data = Integer.parseInt(split[1]);
        int type = Integer.parseInt(split[0]);
        this.path = path;
        this.item = new ItemStack(Material.getMaterial(type), 1, (short)data);
    }

    public ConfigItemBuilder setName(){
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(c.getString(this.path+".name").replace("&", "§"));
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack get(){
        return this.item;
    }

}
