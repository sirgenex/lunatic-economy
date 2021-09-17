package br.com.lunaticmc.economy;

import br.com.lunaticmc.economy.adapter.EcoPlayerAdapter;
import br.com.lunaticmc.economy.command.CoinsCommand;
import br.com.lunaticmc.economy.command.MagnataCommand;
import br.com.lunaticmc.economy.event.listener.BalanceChangeListener;
import br.com.lunaticmc.economy.hook.PlaceholderHook;
import br.com.lunaticmc.economy.hook.VaultHook;
import br.com.lunaticmc.economy.hook.nChatHook;
import br.com.lunaticmc.economy.menu.TopMenu;
import br.com.lunaticmc.economy.object.controller.EcoPlayerController;
import br.com.lunaticmc.economy.task.BackgroundTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Economy extends JavaPlugin {

    @Getter private static Economy instance;

    private File file = null;
    private FileConfiguration fileConfiguration = null;

    @Override
    public void onEnable() {
        getLogger().info("Starting plugin...");
        instance = this;

        getLogger().info("Loading config...");
        saveDefaultConfig();
        getLogger().info("Config loaded!");

        getLogger().info("Hooking into Vault...");
        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            Bukkit.getServicesManager().register(net.milkbowl.vault.economy.Economy.class, new VaultHook(), Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
            getLogger().info("Vault hooked successfully!");
        }else{
            getLogger().severe("Vault not found, disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Loading database...");
        File verifier = new File(getDataFolder(), "db.yml");
        if (!verifier.exists()) saveResource("db.yml", false);
        EcoPlayerAdapter.getInstance().adapt(getDB());
        getLogger().info("Database loaded!");

        getLogger().info("Registering commands...");
        getCommand("coins").setExecutor(new CoinsCommand());
        getCommand("magnata").setExecutor(new MagnataCommand());
        getLogger().info("Commands registered!");

        getLogger().info("Loading menus...");
        TopMenu.getInstance().load();
        getLogger().info("Menus loaded!");

        getLogger().info("Trying to hook into PlaceholderAPI...");
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook().register();
            getLogger().info("PlaceholderAPI hooked successfully.");
        } else getLogger().warning("PlaceholderAPI can't be found.");

        getLogger().info("Trying to hook into nChat...");
        if(Bukkit.getPluginManager().getPlugin("nChat") != null) {
            new nChatHook(this);
            getLogger().info("nChat hooked successfully.");
        } else getLogger().warning("nChat can't be found.");

        getLogger().info("Registering listeners...");
        Bukkit.getPluginManager().registerEvents(new BalanceChangeListener(), this);
        getLogger().info("Listeners registered!");

        getLogger().info("Starting background task...");
        new BackgroundTask().runTaskTimerAsynchronously(this, 0,(20L *getConfig().getInt("task_timer"))*60);
        getLogger().info("Background task started!");

        getLogger().info("Plugin started!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling plugin...");

        getLogger().info("Saving data...");
        EcoPlayerController.getInstance().saveAll(getDB());
        saveDB();
        reloadDB();
        getLogger().info("Data saved successfully!");

        getLogger().info("Plugin disabled!");
    }

    public FileConfiguration getDB() {
        if (this.fileConfiguration == null) {
            this.file = new File(getDataFolder(), "db.yml");
            this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        }
        return this.fileConfiguration;
    }

    public void saveDB() {
        try { getDB().save(this.file); } catch (Exception ignored) {}
    }

    public void reloadDB() {
        if (this.file == null) this.file = new File(getDataFolder(), "db.yml");
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        YamlConfiguration db = YamlConfiguration.loadConfiguration(this.file);
        this.fileConfiguration.setDefaults(db);
    }

}