package extrabiomes.blocks;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface ICropType {

    IIcon getStageIIcon(int stage);

    void setStageIIcons(ArrayList<IIcon> IIcons);

    ItemStack getSeedItem();

    Item getCropItem();

    void setSeedItem(ItemStack seed);

    void setCropItem(Item crop);

    int getRenderType();

    String name();
}
