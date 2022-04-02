package dev.u9g.reversehopper;

import org.bukkit.block.Block;

public class Checks {
    public static boolean isRevHopper(Block block) {
        if (block == null) {
            return false;
        }
        return ReverseHopper.INSTANCE.manager.getDataBlock(block, false) != null;
    }
}
