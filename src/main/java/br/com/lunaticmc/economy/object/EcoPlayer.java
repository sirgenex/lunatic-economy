package br.com.lunaticmc.economy.object;

import br.com.lunaticmc.economy.event.BalanceChangeEvent;
import br.com.lunaticmc.economy.manager.FormatManager;
import br.com.lunaticmc.economy.object.controller.EcoPlayerController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

@Getter
@Setter
@AllArgsConstructor
public class EcoPlayer {

    private String name;
    private double balance;
    private boolean magnata;

    public void add(double amount){
        if(!EcoPlayerController.getInstance().hasAccount(name)) EcoPlayerController.getInstance().create(this);
        BalanceChangeEvent event = new BalanceChangeEvent(name, getBalance(), getBalance()+amount);
        Bukkit.getPluginManager().callEvent(event);
    }

    public void remove(double amount){
        if(!EcoPlayerController.getInstance().hasAccount(name)) EcoPlayerController.getInstance().create(this);
        BalanceChangeEvent event = new BalanceChangeEvent(name, getBalance(), getBalance()-amount);
        Bukkit.getPluginManager().callEvent(event);
    }

    public void set(double amount){
        if(!EcoPlayerController.getInstance().hasAccount(name)) EcoPlayerController.getInstance().create(this);
        BalanceChangeEvent event = new BalanceChangeEvent(name, getBalance(), amount);
        Bukkit.getPluginManager().callEvent(event);
    }

    public boolean has(double amount){
        return getBalance() >= amount;
    }

    public String getFormatted(){
        return FormatManager.getInstance().format(getBalance());
    }

}
