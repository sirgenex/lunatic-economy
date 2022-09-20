package br.com.lunaticmc.economy.utils;

import br.com.lunaticmc.economy.EconomyPlugin;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
@Data
public class Message {

    public final List<String> message;

    public static HashMap<String, List<String>> messages = new HashMap<>();

    public static void reload(FileConfiguration c){
        messages.clear();
        c.getConfigurationSection("").getKeys(true).forEach(path -> messages.put(path, c.getStringList(path)));
    }

    public static Message of(String path){
        return new Message(messages.getOrDefault(path, Collections.singletonList("&cErro! Contate um administrador e informe que a mensagem "+path+" do "+
                EconomyPlugin.getInstance().getName()+" não está presente.")));
    }

    public void send(CommandSender sender, String... replacements){
        if(sender instanceof Player) {
            message.forEach(msg -> {
                for (String replacement : replacements) {
                    String[] s = replacement.split(", ");
                    msg = msg.replace(s[0], s[1]);
                }
                sender.sendMessage(msg.replace("&", "§"));
            });
        }
    }

    public void sendAll(String... replacements){
        Bukkit.getOnlinePlayers().forEach(player -> send(player, replacements));
    }

}
