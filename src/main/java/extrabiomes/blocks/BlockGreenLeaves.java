/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.blocks;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;

import extrabiomes.lib.Element;

public class BlockGreenLeaves extends BlockLeafEbxl {

    public enum BlockType {

        FIR(0),
        REDWOOD(1),
        ACACIA(2),
        CYPRESS(3);

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
            if (Element.SAPLING_FIR.isPresent()) FIR.sapling = Element.SAPLING_FIR.get();
            if (Element.SAPLING_REDWOOD.isPresent()) REDWOOD.sapling = Element.SAPLING_REDWOOD.get();
            if (Element.SAPLING_ACACIA.isPresent()) ACACIA.sapling = Element.SAPLING_ACACIA.get();
            if (Element.SAPLING_CYPRESS.isPresent()) CYPRESS.sapling = Element.SAPLING_CYPRESS.get();

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

    public BlockGreenLeaves() {
        super("leavesfir", "leavesredwood", "leavesacacia", "leavescypress");
    }

    @Override
    public int getRenderColor(int metadata) {
        return switch (metadata & 3) {
            case 0 -> ColorizerFoliage.getFoliageColorPine();
            case 1 -> ColorizerFoliage.getFoliageColorBasic();
            case 2 -> ColorizerFoliage.getFoliageColor(0.9F, 0.1F);
            default -> 0xe5fff3;
        };
    }

    @Override
    public int colorMultiplier(IBlockAccess iBlockAccess, int x, int y, int z) {
        final int metadata = iBlockAccess.getBlockMetadata(x, y, z) & 3;
        return (metadata == BlockType.REDWOOD.metadata()) ? calcSmoothedBiomeFoliageColor(iBlockAccess, x, z)
            : getRenderColor(metadata);
    }

    @Override
    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
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

    // Better Foliage (?) compat
    public float getSpawnChanceFallingLeaves(int metadata) {
        return 0.01F;
    }

}
