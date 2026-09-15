/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.LanguageRegistry;
import extrabiomes.helpers.ToolTipStringFormatter;

public class ItemKneeLog extends ItemBlock {

    public ItemKneeLog(final Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack itemForTooltip, EntityPlayer playerViewingToolTip, List listOfLines,
        boolean sneaking) {
        String line = LanguageRegistry.instance()
            .getStringLocalization("extrabiomes.elbow.crafting");

        if (!line.equals("extrabiomes.elbow.crafting")) {
            if (listOfLines.size() > 0 && ((String) listOfLines.get(0)).length() > 20) {
                ToolTipStringFormatter.Format(line, listOfLines, ((String) listOfLines.get(0)).length() + 5);
            } else {
                ToolTipStringFormatter.Format(line, listOfLines);
            }
        }
    }

}
