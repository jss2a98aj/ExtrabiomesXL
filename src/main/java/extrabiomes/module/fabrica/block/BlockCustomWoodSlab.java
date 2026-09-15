/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.fabrica.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extrabiomes.Extrabiomes;
import extrabiomes.lib.BlockSettings;

public class BlockCustomWoodSlab extends BlockWoodSlab {

    public enum BlockType {

        REDWOOD(0),
        FIR(1),
        ACACIA(2),
        CYPRESS(3),
        JAPANESE_MAPLE(4),
        RAINBOW_EUCALYPTUS(5),
        AUTUMN(6),
        BALD_CYPRESS(7);

        private final int metadata;

        BlockType(int metadata) {
            this.metadata = metadata;
        }

        public int metadata() {
            return metadata;
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    private final BlockSettings settings;
    private static Block singleSlab = null;

    private final IIcon[] textures = { null, null, null, null, null, null, null, null };

    public BlockCustomWoodSlab(BlockSettings settings, boolean isDouble) {
        super(isDouble);
        if (!isDouble) singleSlab = this;
        setHardness(2.0F);
        setResistance(5.0F);
        setStepSound(soundTypeWood);
        // Blocks.fire.setFireInfo(this, 5, 20);
        setLightOpacity(0);
        this.settings = settings;
        setCreativeTab(Extrabiomes.tabsEBXL);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        textures[0] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "planksredwood");
        textures[1] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "planksfir");
        textures[2] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "planksacacia");
        textures[3] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "plankscypress");
        textures[4] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "planksjapanesemaple");
        textures[5] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "planksrainboweucalyptus");
        textures[6] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "planksautumn");
        textures[7] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "planksbaldcypress");
    }

    @Override
    protected ItemStack createStackedBlock(int damage) {
        return new ItemStack(singleSlab, 2, damage & 7);
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return textures[metadata & 7];
    }

    @Override
    /* Deobf name: getFullSlabName */
    public String func_150002_b(int metadata) {
        String woodType = switch (metadata & 7) {
            case 1 -> BlockType.FIR.toString();
            case 2 -> BlockType.ACACIA.toString();
            case 3 -> BlockType.CYPRESS.toString();
            case 4 -> BlockType.JAPANESE_MAPLE.toString();
            case 5 -> BlockType.RAINBOW_EUCALYPTUS.toString();
            case 6 -> BlockType.AUTUMN.toString();
            case 7 -> BlockType.BALD_CYPRESS.toString();
            default -> BlockType.REDWOOD.toString();
        };

        return super.getUnlocalizedName() + "." + woodType;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List itemList) {
        if (this == singleSlab) {
            for (final BlockType type : BlockType.values()) {
                itemList.add(new ItemStack(item, 1, type.metadata()));
            }
        }
    }

    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return ItemBlock.getItemFromBlock(singleSlab);
    }

    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, int x, int y, int z) {
        return Item.getItemFromBlock(this);
    }
}
