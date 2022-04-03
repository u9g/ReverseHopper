package dev.u9g.reversehopper;

import com.github.u9g.u9gutils.NBTUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import redempt.redlib.blockdata.DataBlock;
import redempt.redlib.misc.Task;

import java.util.Objects;

public class EventHandlers implements Listener {
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (!event.hasBlock() || !Objects.equals(event.getHand(), EquipmentSlot.HAND)) return;
        Block block = Objects.requireNonNull(event.getClickedBlock());
        if (!Checks.isRevHopper(block)) return;
        var db = ReverseHopper.INSTANCE.manager.getDataBlock(block, false);
        if (db == null) return;
        var bool = db.getBoolean(Constants.blockKey);
        if (bool != null && bool) {
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                onBlockBreak(db, block, event.getPlayer());
                event.setCancelled(true);
            } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getPlayer().isSneaking()) {
                DataBlockUtil.showInventoryTo(db, event.getPlayer());
                event.setCancelled(true);
            }
        }
        ReverseHopper.INSTANCE.manager.save();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        var block = event.getBlock();
        if (!Checks.isRevHopper(block)) return;
        onBlockBreak(ReverseHopper.INSTANCE.manager.getDataBlock(block), block, event.getPlayer());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (NBTUtil.getAsBoolean(event.getItemInHand().getItemMeta(), Constants.key).orElse(false)) {
            event.getItemInHand().setAmount(event.getItemInHand().getAmount()-1);
            var loc = event.getBlock().getLocation();
            Task.syncDelayed(() -> {
                ReverseHopper.INSTANCE.manager.getDataBlock(loc.getBlock(), true).set(Constants.blockKey, true);
                ReverseHopper.INSTANCE.manager.save();
            });
        }
    }

    public void onBlockBreak (DataBlock db, Block block, Player player) {
        DataBlockUtil.dropAt(db, block.getLocation());
        block.setType(Material.AIR);
        player.getInventory().addItem(Constants.item);
        ReverseHopper.INSTANCE.manager.remove(db);
        ReverseHopper.INSTANCE.manager.save();
    }
}
