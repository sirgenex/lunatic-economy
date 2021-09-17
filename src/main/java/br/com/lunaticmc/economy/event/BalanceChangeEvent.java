package br.com.lunaticmc.economy.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class BalanceChangeEvent extends Event implements Cancellable {

    private final String player;
    @Setter private boolean cancelled;
    private final double from;
    private final double to;

    public BalanceChangeEvent(String player, double from, double to) {
        this.player = player;
        setCancelled(false);
        this.from = from;
        this.to = to;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}