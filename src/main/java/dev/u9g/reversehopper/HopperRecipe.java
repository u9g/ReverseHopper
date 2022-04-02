package dev.u9g.reversehopper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class HopperRecipe {
    public static void register() {
        Bukkit.addRecipe(new ShapedRecipe(Constants.key, Constants.item)
                .shape( "X X",
                        "XYX",
                        " X ")
                .setIngredient('X', Material.COPPER_INGOT)
                .setIngredient('Y', Material.CHEST));
    }
}
