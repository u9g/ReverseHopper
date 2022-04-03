package dev.u9g.reversehopper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import redempt.redlib.blockdata.BlockDataManager;

public final class ReverseHopper extends JavaPlugin {
    public BlockDataManager manager;
    public static ReverseHopper INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        manager = BlockDataManager.createAuto(this, this.getDataFolder().toPath().resolve("blocks.db"), true, true);
//        new CommandParser(this.getResource("commands.rdcml")).parse().register("hoppa", new CommandHandler());
        Bukkit.getPluginManager().registerEvents(new EventHandlers(), this);
        HopperTicker.init();
        HopperRecipe.register();
    }

    @Override
    public void onDisable() {
        manager.saveAndClose();
    }
}
