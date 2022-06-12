package dev.u9g.reversehopper;

import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class ItemHelper {
    public static ItemWithSlot firstNonNull(ItemStack[] items) {
        // get item to transfer
        int slot = -1;
        ItemStack item = null;
        for (var currentItem : items) {
            slot++;
            if (currentItem == null) continue;
            item = currentItem;
            break;
        }
        return item == null ? null : new ItemWithSlot(item, slot);
    }

    public static ItemWithSlot firstSimilarOrNonNull(Iterator<ItemStack> iter, ItemStack needSimilarTo) {
        ItemStack addingToStack = null;
        int i = -1;
        while (iter.hasNext() && addingToStack == null) {
            i++;
            var stack = iter.next();
            if (needSimilarTo.isSimilar(stack) && stack.getAmount() != stack.getMaxStackSize()) {
                addingToStack = stack;
            }
        }
        return addingToStack == null ? null : new ItemWithSlot(addingToStack, i);
    }
}
