package dev.u9g.reversehopper;

import com.github.u9g.u9gutils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class Constants {
    public static NamespacedKey key = NamespacedKey.fromString("rev_hopper:item");
    public static ItemStack item = ItemBuilder.of(Material.LIGHTNING_ROD).name(Component.text("Reverse Hopper").color(NamedTextColor.LIGHT_PURPLE)).set(key, true).build();
    public static String blockKey = "rev_hopper:block";
}
