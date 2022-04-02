package dev.u9g.reversehopper;

import org.bukkit.entity.Player;
import redempt.redlib.commandmanager.CommandHook;

public class CommandHandler {
    @CommandHook("admingive")
    public void onAdminGive(Player player) {
        player.getInventory().addItem(Constants.item);
    }
}
