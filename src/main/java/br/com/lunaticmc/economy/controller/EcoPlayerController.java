package br.com.lunaticmc.economy.controller;

import br.com.lunaticmc.economy.comparators.ValueComparator;
import br.com.lunaticmc.economy.config.ConfigurationData;
import br.com.lunaticmc.economy.object.EcoPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.TreeMap;

public class EcoPlayerController {

    @Getter
    private static final EcoPlayerController instance = new EcoPlayerController();

    private final HashMap<String, EcoPlayer> cache = new HashMap<>();
    private final ValueComparator bvc = new ValueComparator(cache);
    public TreeMap<String, EcoPlayer> top = new TreeMap<>(bvc);
    @Getter
    @Setter
    private EcoPlayer magnata;

    public void create(EcoPlayer model) {
        cache.put(model.getName(), model);
    }

    public EcoPlayer get(String name) {
        return hasAccount(name) ? cache.get(name) : new EcoPlayer(name, 0, false);
    }

    public boolean hasAccount(String name) {
        return cache.containsKey(name);
    }

    public void update() {
        top.clear();
        top.putAll(cache);
        if (!top.isEmpty()) {
            EcoPlayer value = top.firstEntry().getValue();
            if (magnata != null) {
                if (!magnata.equals(value)) {
                    getMagnata().setMagnata(false);
                    ConfigurationData.getInstance().magnata_update.forEach(msg -> Bukkit.broadcastMessage(msg.replace("&", "§").replace("{player}", value.getName()).replace("{amount}", value.getFormatted())));
                    setMagnata(top.firstEntry().getValue());
                    getMagnata().setMagnata(true);
                }
            } else {
                ConfigurationData.getInstance().magnata_update.forEach(msg -> Bukkit.broadcastMessage(msg.replace("&", "§").replace("{player}", value.getName()).replace("{amount}", value.getFormatted())));
                setMagnata(top.firstEntry().getValue());
                getMagnata().setMagnata(true);
            }
        }
    }

    public void save() {
        //TODO: Save data
    }

}
