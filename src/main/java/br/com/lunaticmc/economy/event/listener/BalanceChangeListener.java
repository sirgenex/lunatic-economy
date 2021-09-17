package br.com.lunaticmc.economy.event.listener;

import br.com.lunaticmc.economy.event.BalanceChangeEvent;
import br.com.lunaticmc.economy.object.EcoPlayer;
import br.com.lunaticmc.economy.object.controller.EcoPlayerController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class BalanceChangeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBalanceChange(BalanceChangeEvent e){
        EcoPlayer player = EcoPlayerController.getInstance().get(e.getPlayer());
        player.setBalance(e.getTo());
    }

}
