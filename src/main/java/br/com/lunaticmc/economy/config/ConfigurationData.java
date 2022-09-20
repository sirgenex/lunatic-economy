package br.com.lunaticmc.economy.config;

import br.com.lunaticmc.economy.EconomyPlugin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigurationData {

    @Getter private static final ConfigurationData instance = new ConfigurationData();

    private final FileConfiguration c = EconomyPlugin.getInstance().getConfig();

    public String permission = c.getString("permission");

    public String magnata_tag = c.getString("magnata.tag");

    public List<String> balance_you = c.getStringList("messages.balance.you");
    public List<String> balance_others = c.getStringList("messages.balance.others");
    public List<String> help_player = c.getStringList("messages.help.normal");
    public List<String> help_admin = c.getStringList("messages.help.admin");
    public List<String> without_permission = c.getStringList("messages.without_permission");
    public List<String> inexistent_player = c.getStringList("messages.inexistent_player");
    public List<String> set = c.getStringList("messages.setted");
    public List<String> add = c.getStringList("messages.added");
    public List<String> remove = c.getStringList("messages.removed");
    public List<String> invalid_integer = c.getStringList("messages.invalid_integer");
    public List<String> sent = c.getStringList("messages.sent");
    public List<String> without_money = c.getStringList("messages.without_money");
    public List<String> player_without_money = c.getStringList("messages.player_without_money");
    public List<String> magnata_update = c.getStringList("messages.magnata_update");
    public List<String> magnata_message = c.getStringList("messages.magnata_message");

}
