/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.handlers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.base.Optional;

import cpw.mods.fml.common.registry.GameRegistry;
import extrabiomes.Extrabiomes;
import extrabiomes.api.Stuff;
import extrabiomes.items.ItemCustomDye;
import extrabiomes.items.ItemCustomFood;
import extrabiomes.items.LogTurner;
import extrabiomes.lib.Element;
import extrabiomes.lib.ItemSettings;
import extrabiomes.lib.ModuleControlSettings;
import extrabiomes.lib.Reference;

public abstract class ItemHandler {

    public static void createItems() {
        createLogTurner();
        createDye();
        createFoods();
    }

    private static void createLogTurner() {
        if (!ModuleControlSettings.SUMMA.isEnabled() || !ItemSettings.LOGTURNER.getEnabled()) {
            return;
        }

        final LogTurner logTurner = new LogTurner();

        Stuff.logTurner = Optional.of(logTurner);

        logTurner.setUnlocalizedName("extrabiomes.logturner")
            .setCreativeTab(Extrabiomes.tabsEBXL);

        GameRegistry.registerItem(logTurner, "extrabiomes.logturner", Reference.MOD_ID);

        Element.LOGTURNER.set(new ItemStack(logTurner));
    }

    private static void createDye() {
        if (!ItemSettings.DYE.getEnabled()) {
            final String dyetype = "etfuturum:dye";
            if (Item.itemRegistry.containsKey(dyetype)) {
                final Item dye = (Item) Item.itemRegistry.getObject(dyetype);
                Element.DYE_BLACK.set(new ItemStack(dye, 1, 3));
                Element.DYE_BLUE.set(new ItemStack(dye, 1, 1));
                Element.DYE_BROWN.set(new ItemStack(dye, 1, 2));
                Element.DYE_WHITE.set(new ItemStack(dye, 1, 0));
            }
        } else {
            final ItemCustomDye dye = new ItemCustomDye();
            Stuff.dye = Optional.of(dye);
            dye.setUnlocalizedName("extrabiomes.dye")
                .setCreativeTab(Extrabiomes.tabsEBXL);
            GameRegistry.registerItem(dye, "extrabiomes.dye", Reference.MOD_ID);

            dye.init();
        }
    }

    private static void createFoods() {
        if (!ItemSettings.FOOD.getEnabled()) {
            return;
        }

        final ItemCustomFood food = new ItemCustomFood();
        Stuff.food = Optional.of(food);
        GameRegistry.registerItem(food, "extrabiomes.food", Reference.MOD_ID);
    }

}
