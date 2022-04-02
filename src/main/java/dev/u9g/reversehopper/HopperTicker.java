package dev.u9g.reversehopper;

import com.github.u9g.u9gutils.MoreItemUtils;
import org.bukkit.block.Container;
import redempt.redlib.misc.Task;

public class HopperTicker {
    public static void init() {
        Task.syncRepeating(() -> {
            ReverseHopper.INSTANCE.manager.getAllLoaded().forEach(block -> {
                var location = block.getBlock().getLocation();
                var upOne = location.clone().add(0,1,0).getBlock();
                var downOne = location.add(0,-1,0).getBlock();
            // if the block below the hopper is a container we have to TAKE a block to the container
                if (downOne.getState() instanceof Container downContainer) {
                    DataBlockUtil.containerFillThisHopper(block, MoreItemUtils.firstNonEmpty(downContainer.getInventory()));
                }
            // if the block below this hopper is a hopper, we have to TAKE from the lower hopper
                else if (Checks.isRevHopper(downOne)) {
                    var downHopper = ReverseHopper.INSTANCE.manager.getDataBlock(downOne, false);
                    DataBlockUtil.hopperFillThisHopper(downHopper, block);
                }
            // if the block above the hopper is a container we have to ADD a block to the container
                if (upOne.getState() instanceof Container upContainer) {
                    DataBlockUtil.thisContainerFillHopper(block, upContainer.getInventory());
                }
            });
        }, 0, 3);
    }
}
