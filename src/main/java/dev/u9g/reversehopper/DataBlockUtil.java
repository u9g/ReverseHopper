package dev.u9g.reversehopper;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import redempt.redlib.blockdata.DataBlock;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.itemutils.ItemUtils;
import redempt.redlib.misc.Task;

public class DataBlockUtil {
    private static String getSlotName(int slot) {
        return "rev_hopper:slot_"+slot;
    }

    private static ItemStack[] getInventory(DataBlock block) {
        ItemStack[] items = new ItemStack[9];
        for (int i = 0; i < 9; i++) {
            var itemStr = block.getString(getSlotName(i));
            if (itemStr == null) continue;
            if (itemStr.equals("empty")) items[i] = null;
            else {
                ItemStack item = ItemUtils.fromString(itemStr);
                items[i] = item;
            }
        }
        return items;
    }

    public static void containerFillThisHopper(DataBlock block, ItemStack toTakeFrom) {
        if (toTakeFrom == null) return;
        var inv = getInventory(block);
        for (int i = 0; i < 9; i++) {
            if (inv[i] == null) {
                setSlot(block, i, toTakeFrom.asQuantity(1));
                toTakeFrom.setAmount(toTakeFrom.getAmount()-1);
                break;
            } else if (inv[i].getAmount() != 64 && toTakeFrom.isSimilar(inv[i])) {
                inv[i].setAmount(inv[i].getAmount()+1);
                setSlot(block, i, inv[i]);
                toTakeFrom.setAmount(toTakeFrom.getAmount()-1);
                break;
            }
        }
    }

    public static void hopperFillThisHopper(DataBlock downHopper, DataBlock upHopper) {
        var itemWithSlot = ItemHelper.firstNonNull(getInventory(downHopper));
        if (itemWithSlot == null) return;
        int slot = itemWithSlot.slot();
        ItemStack toTakeFrom = itemWithSlot.item();
        if (toTakeFrom == null) return;
        var inv = getInventory(upHopper);
        for (int i = 0; i < 9; i++) {
            if (inv[i] == null) {
                setSlot(upHopper, i, toTakeFrom.asQuantity(1));
                toTakeFrom.setAmount(toTakeFrom.getAmount()-1);
                setSlot(downHopper, slot, toTakeFrom);
                break;
            } else if (inv[i].getAmount() != 64 && toTakeFrom.isSimilar(inv[i])) {
                inv[i].setAmount(inv[i].getAmount()+1);
                setSlot(upHopper, i, inv[i]);
                toTakeFrom.setAmount(toTakeFrom.getAmount()-1);
                setSlot(downHopper, slot, toTakeFrom);
                break;
            }
        }
    }

    public static void thisContainerFillHopper(DataBlock block, Inventory chest) {
    // get inventory to add to
        var items = getInventory(block);
    // get item to transfer
        var itemWithSlot = ItemHelper.firstNonNull(items);
        if (itemWithSlot == null) return;
        int slot = itemWithSlot.slot();
        ItemStack transferItem = itemWithSlot.item();
    // find stack to add to
        var iws = ItemHelper.firstSimilarOrNonNull(chest.iterator(), transferItem);
//        if (iws == null) return;
        var addingToStack = iws == null ? null : iws.item();
    // can't find stack to add to, so lets find the first empty stack
        if (addingToStack == null) {
            int firstEmpty = chest.firstEmpty();
            if (firstEmpty == -1) return;
    // transfer it
            chest.setItem(firstEmpty, transferItem.asQuantity(1));
        } else { // addingToStack is not null
            addingToStack.setAmount(addingToStack.getAmount()+1);
        }
    // take from old stack
        transferItem.setAmount(transferItem.getAmount()-1);
        setSlot(block, slot, transferItem);
    }

    private static void setSlot(DataBlock block, int slot, ItemStack newStack) {
        if (newStack.getAmount() != 0)
            block.set(getSlotName(slot), ItemUtils.toString(newStack));
        else
            block.set(getSlotName(slot), "empty");
        ReverseHopper.INSTANCE.manager.save();
    }

    public static void dropAt (DataBlock block, Location location) {
        for (var item : DataBlockUtil.getInventory(block)) {
            if (item == null) continue;
            location.getWorld().dropItemNaturally(location, item);
        }
    }

    public static void showInventoryTo (DataBlock block, Player player) {
        var gui = new InventoryGUI(Bukkit.createInventory(null, 9, Component.text("Reverse Hopper")));
        Task.asyncRepeating((task) -> {
            if (gui.getInventory().getViewers().size() == 0) {
                task.cancel();
                return;
            }
            fillInventory(gui, getInventory(block));
        }, 0, 1);
        gui.open(player);
    }

    private static void fillInventory (InventoryGUI gui, ItemStack[] items) {
        int i = 0;
        for (var item : items) {
            var inv = gui.getInventory();
            inv.setItem(i++, item);
        }
    }
}

record ItemWithSlot(ItemStack item, int slot){}