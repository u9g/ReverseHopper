package dev.u9g.reversehopper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Objects;

public class HopperRecipe {
    public static void register() {
        var chest = Objects.requireNonNull(NamespacedKey.fromString("reverse_hoppers:recipe_with_chest"));
        var barrel = Objects.requireNonNull(NamespacedKey.fromString("reverse_hoppers:recipe_with_barrel"));
        Bukkit.addRecipe(new ShapedRecipe(chest, Constants.item)
                .shape( "X X",
                        "XYX",
                        " X ")
                .setIngredient('X', Material.COPPER_INGOT)
                .setIngredient('Y', Material.CHEST));
        Bukkit.addRecipe(new ShapedRecipe(barrel, Constants.item)
                .shape( "X X",
                        "XYX",
                        " X ")
                .setIngredient('X', Material.COPPER_INGOT)
                .setIngredient('Y', Material.BARREL));
    }
}
