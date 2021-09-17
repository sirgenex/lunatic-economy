package br.com.lunaticmc.economy.adapter;

import br.com.lunaticmc.economy.object.EcoPlayer;
import br.com.lunaticmc.economy.object.controller.EcoPlayerController;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class EcoPlayerAdapter {

    @Getter private static final EcoPlayerAdapter instance = new EcoPlayerAdapter();

    public void adapt(FileConfiguration c){

        String magnata = c.getString("magnata") == null ? "" : c.getString("magnata");

        c.getConfigurationSection("players").getKeys(false).forEach(player -> {

            double balance = c.getDouble("players."+player+".balance");

            EcoPlayer model = new EcoPlayer(player, balance, magnata.equalsIgnoreCase(player));

            if(model.isMagnata()) EcoPlayerController.getInstance().setMagnata(model);

            EcoPlayerController.getInstance().create(model);

        });

    }

}
