/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;

import extrabiomes.lib.Element;

public class BlockNewLeaves extends BlockLeafEbxl {

    public enum BlockType {

        BALD_CYPRESS(0),
        JAPANESE_MAPLE(1),
        JAPANESE_MAPLE_SHRUB(2),
        RAINBOW_EUCALYPTUS(3);

        private final int metadata;
        private ItemStack sapling = new ItemStack(Blocks.sapling);
        private static boolean loadedCustomBlocks = false;

        static BlockType fromMetadata(int metadata) {
            for (final BlockType type : BlockType.values()) {
                if (type.metadata() == (metadata & 3)) return type;
            }
            return null;
        }

        private static void loadCustomBlocks() {
            if (Element.SAPLING_BALD_CYPRESS.isPresent()) BALD_CYPRESS.sapling = Element.SAPLING_BALD_CYPRESS.get();
            if (Element.SAPLING_JAPANESE_MAPLE.isPresent())
                JAPANESE_MAPLE.sapling = Element.SAPLING_JAPANESE_MAPLE.get();
            if (Element.SAPLING_JAPANESE_MAPLE_SHRUB.isPresent())
                JAPANESE_MAPLE_SHRUB.sapling = Element.SAPLING_JAPANESE_MAPLE_SHRUB.get();
            if (Element.SAPLING_RAINBOW_EUCALYPTUS.isPresent())
                RAINBOW_EUCALYPTUS.sapling = Element.SAPLING_RAINBOW_EUCALYPTUS.get();

            loadedCustomBlocks = true;
        }

        BlockType(int metadata) {
            this.metadata = metadata;
        }

        Item getSaplingItem() {
            if (!loadedCustomBlocks) {
                loadCustomBlocks();
            }
            return sapling.getItem();
        }

        Block getSaplingBlock() {
            return Block.getBlockFromItem(sapling.getItem());
        }

        int getSaplingMetadata() {
            if (!loadedCustomBlocks) {
                loadCustomBlocks();
            }

            return sapling.getItemDamage();
        }

        public int metadata() {
            return metadata;
        }
    }

    public BlockNewLeaves() {
        super("leavesbaldcypress", "leavesjapanesemaple", "leavesjapanesemapleshrub", "leavesrainboweucalyptus");
    }

    @Override
    public int getRenderColor(int metadata) {
        return switch (metadata & 3) {
            case 0, 2 -> ColorizerFoliage.getFoliageColor(1.0F, 0.5F);
            case 1 -> 0xffffff;
            default -> ColorizerFoliage.getFoliageColor(1.0F, 0.0F);
        };
    }

    @Override
    public int colorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z) {
        final int metadata = iBlockAccess.getBlockMetadata(x, y, z) & 3;
        if (metadata == BlockType.JAPANESE_MAPLE.metadata() || metadata == BlockType.JAPANESE_MAPLE_SHRUB.metadata()) {
            return getRenderColor(metadata);
        } else {
            return calcSmoothedBiomeFoliageColor(iBlockAccess, x, z);
        }
    }

    @Override
    public int damageDropped(int metadata) {
        final BlockType type = BlockType.fromMetadata(metadata);
        return type != null ? type.getSaplingMetadata() : 0;
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int par3) {
        final BlockType type = BlockType.fromMetadata(metadata);
        return type != null ? type.getSaplingItem() : Item.getItemFromBlock(Blocks.sapling);
    }

    // Better Foliage (?) compat
    public float getSpawnChanceFallingLeaves(int metadata) {
        return 0.01F;
    }

    private static int calcSmoothedBiomeFoliageColor(IBlockAccess iBlockAccess, int x, int z) {
        int red = 0;
        int green = 0;
        int blue = 0;

        for (int z1 = -1; z1 <= 1; ++z1) {
            for (int x1 = -1; x1 <= 1; ++x1) {
                final int foliageColor = iBlockAccess.getBiomeGenForCoords(x + x1, z + z1)
                    .getBiomeFoliageColor(x + x1, 96, z + z1);
                red += (foliageColor & 16711680) >> 16;
                green += (foliageColor & 65280) >> 8;
                blue += foliageColor & 255;
            }
        }

        return (red / 9 & 255) << 16 | (green / 9 & 255) << 8 | blue / 9 & 255;
    }

}
