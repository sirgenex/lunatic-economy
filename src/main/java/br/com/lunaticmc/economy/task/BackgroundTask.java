package br.com.lunaticmc.economy.task;

import br.com.lunaticmc.economy.Economy;
import br.com.lunaticmc.economy.menu.TopMenu;
import br.com.lunaticmc.economy.object.controller.EcoPlayerController;
import org.bukkit.scheduler.BukkitRunnable;

public class BackgroundTask extends BukkitRunnable {

    @Override
    public void run() {
        EcoPlayerController.getInstance().updateTop();
        TopMenu.getInstance().update();
        EcoPlayerController.getInstance().saveAll(Economy.getInstance().getDB());
    }

}
