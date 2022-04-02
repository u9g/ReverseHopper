package dev.u9g.reversehopper;

import com.github.u9g.u9gutils.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import redempt.redlib.blockdata.BlockDataManager;
import redempt.redlib.commandmanager.CommandHook;
import redempt.redlib.commandmanager.CommandParser;
import redempt.redlib.misc.EventListener;
import redempt.redlib.misc.Task;

import java.util.Objects;

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
