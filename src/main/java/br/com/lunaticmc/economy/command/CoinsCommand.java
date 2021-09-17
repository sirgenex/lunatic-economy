package br.com.lunaticmc.economy.command;

import br.com.lunaticmc.economy.config.ConfigurationData;
import br.com.lunaticmc.economy.manager.FormatManager;
import br.com.lunaticmc.economy.menu.TopMenu;
import br.com.lunaticmc.economy.object.EcoPlayer;
import br.com.lunaticmc.economy.object.controller.EcoPlayerController;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class CoinsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final ConfigurationData c = ConfigurationData.getInstance();

        EcoPlayer player = EcoPlayerController.getInstance().get(sender.getName());

        if(args.length == 0) c.balance_you.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{balance}", player.getFormatted())));
        else{

            if(args[0].equalsIgnoreCase("top")){
                if(sender instanceof Player) TopMenu.getInstance().open((Player)sender);
                else sender.sendMessage("§cComando apenas para jogadores.");
                return true;
            }

            if(args[0].equalsIgnoreCase("help")){
                if(sender.hasPermission(c.permission)) c.help_admin.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
                else c.help_player.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
                return true;
            }

            if(args.length == 3) {
                OfflinePlayer jg = Bukkit.getOfflinePlayer(args[1]);
                double amount;
                try {
                    amount = Double.parseDouble(args[2]);
                } catch (Exception ignored) {
                    c.invalid_integer.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
                    return true;
                }
                if (!jg.hasPlayedBefore()) {
                    c.inexistent_player.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
                    return true;
                }
                if (amount < 0 || args[2].equalsIgnoreCase("NaN") || args[2].equalsIgnoreCase("null")) {
                    c.invalid_integer.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
                    return true;
                }
                if (args[0].equalsIgnoreCase("adicionar") || args[0].equalsIgnoreCase("remover") || args[0].equalsIgnoreCase("definir")) {
                    if (!sender.hasPermission(c.permission)) {
                        c.without_permission.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
                        return true;
                    }
                }
                EcoPlayer ecoJg = EcoPlayerController.getInstance().get(jg.getName());
                if (args[0].equalsIgnoreCase("enviar")) {
                    if (player.has(amount)) {
                        player.remove(amount);
                        ecoJg.add(amount);
                        c.sent.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", jg.getName()).replace("{amount}", FormatManager.getInstance().format(amount)).replace("{balance}", player.getFormatted())));
                    }else{
                        c.without_money.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("definir")) {
                    ecoJg.set(amount);
                    c.set.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", jg.getName()).replace("{amount}", FormatManager.getInstance().format(amount)).replace("{balance}", ecoJg.getFormatted())));
                    return true;
                }
                if (args[0].equalsIgnoreCase("adicionar")) {
                    ecoJg.add(amount);
                    c.add.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", jg.getName()).replace("{amount}", FormatManager.getInstance().format(amount)).replace("{balance}", ecoJg.getFormatted())));
                    return true;
                }
                if (args[0].equalsIgnoreCase("remover")) {
                    if(ecoJg.has(amount)) {
                        ecoJg.remove(amount);
                        c.remove.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", jg.getName()).replace("{amount}", FormatManager.getInstance().format(amount)).replace("{balance}", ecoJg.getFormatted())));
                        return true;
                    }else c.player_without_money.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", jg.getName())));
                    return true;
                }
            }
            OfflinePlayer jg = Bukkit.getOfflinePlayer(args[0]);
            if(jg.hasPlayedBefore()) c.balance_others.forEach(msg -> sender.sendMessage(msg.replace("&", "§").replace("{player}", jg.getName()).replace("{balance}", EcoPlayerController.getInstance().get(jg.getName()).getFormatted())));
            else c.inexistent_player.forEach(msg -> sender.sendMessage(msg.replace("&", "§")));
        }

        return false;
    }
}
