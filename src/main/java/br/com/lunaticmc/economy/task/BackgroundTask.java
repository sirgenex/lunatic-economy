package br.com.lunaticmc.economy.task;

import br.com.lunaticmc.economy.EconomyPlugin;
import br.com.lunaticmc.economy.menu.TopMenu;
import br.com.lunaticmc.economy.controller.EcoPlayerController;
import org.bukkit.scheduler.BukkitRunnable;

public class BackgroundTask extends BukkitRunnable {

    @Override
    public void run() {
        EcoPlayerController.getInstance().update();
        TopMenu.getInstance().update();
        EcoPlayerController.getInstance().save(EconomyPlugin.getInstance().getDB());
    }

}
