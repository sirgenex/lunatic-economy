package br.com.lunaticmc.economy.command;

import br.com.lunaticmc.economy.config.ConfigurationData;
import br.com.lunaticmc.economy.object.EcoPlayer;
import br.com.lunaticmc.economy.object.controller.EcoPlayerController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MagnataCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        EcoPlayer magnata = EcoPlayerController.getInstance().getMagnata() == null ? new EcoPlayer("Nenhum", 0, true) : EcoPlayerController.getInstance().getMagnata();

        ConfigurationData.getInstance().magnata_message.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", magnata.getName()).replace("{amount}", magnata.getFormatted())));

        return false;
    }
}
