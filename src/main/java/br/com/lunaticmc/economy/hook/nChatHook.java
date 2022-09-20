package br.com.lunaticmc.economy.hook;

import br.com.lunaticmc.economy.config.ConfigurationData;
import br.com.lunaticmc.economy.object.EcoPlayer;
import br.com.lunaticmc.economy.controller.EcoPlayerController;
import com.nickuc.chat.api.events.PublicMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class nChatHook implements Listener {

    public nChatHook(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(PublicMessageEvent e){
        EcoPlayer player = EcoPlayerController.getInstance().get(e.getSender().getName());
        if(player.isMagnata()) e.setTag("magnata", ConfigurationData.getInstance().magnata_tag.replace("&", "§"));
    }

}
