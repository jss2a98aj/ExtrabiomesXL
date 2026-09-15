/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.summa.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import extrabiomes.lib.Element;
import extrabiomes.module.summa.TreeSoilRegistry;

public class WorldGenFirTreeHuge extends WorldGenAbstractTree {

    private enum TreeBlock {

        LEAVES(new ItemStack(Blocks.leaves, 1, 1)),
        TRUNK(new ItemStack(Blocks.log, 1, 1));

        private ItemStack stack;

        private static boolean loadedCustomBlocks = false;

        private static void loadCustomBlocks() {
            if (Element.LEAVES_FIR.isPresent()) {
                LEAVES.stack = Element.LEAVES_FIR.get();
            }

            if (Element.LOG_QUARTER_FIR.isPresent()) {
                TRUNK.stack = Element.LOG_QUARTER_FIR.get();
            }

            loadedCustomBlocks = true;
        }

        TreeBlock(ItemStack stack) {
            this.stack = stack;
        }

        public Block getBlock() {
            if (!loadedCustomBlocks) loadCustomBlocks();
            return Block.getBlockFromItem(stack.getItem());
        }

        public int getMetadata() {
            if (!loadedCustomBlocks) loadCustomBlocks();
            return stack.getItemDamage();
        }
    }

    public WorldGenFirTreeHuge(boolean doBlockNotify) {
        super(doBlockNotify);
    }

    // Store the last seed that was used to generate a tree
    private static long lastSeed = 0;

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        lastSeed = rand.nextLong();
        return generateTree(world, new Random(lastSeed), x, y, z);
    }

    public boolean generate(World world, long seed, int x, int y, int z) {
        lastSeed = seed;
        return generateTree(world, new Random(seed), x, y, z);
    }

    private boolean generateTree(World world, Random rand, int originX, int originY, int originZ) {
        final int height = rand.nextInt(16) + 32;

        if (originY < 1 || originY + height > 255) {
            return false;
        }
        if (!TreeSoilRegistry.isValidSoil(world.getBlock(originX, originY - 1, originZ))) {
            return false;
        }

        final int trunkClearHeight = 1 + rand.nextInt(12);
        final int leafSlopeHalfMax = 2 + rand.nextInt(9);

        // Check bounds before placement
        for (int checkY = originY; checkY <= originY + 1 + height; checkY++) {
            int checkRamp = (checkY - originY < trunkClearHeight) ? 0 : leafSlopeHalfMax;
            for (int checkX = originX - checkRamp; checkX <= originX + checkRamp; ++checkX) {
                for (int checkZ = originZ - checkRamp; checkZ <= originZ + checkRamp; ++checkZ) {
                    if (!world.getChunkProvider()
                        .chunkExists(checkX >> 4, checkZ >> 4)) {
                        return false;
                    }
                    final Block block = world.getBlock(checkX, checkY, checkZ);
                    if (!block.isLeaves(world, checkX, checkY, checkZ)
                        && !block.isReplaceable(world, checkX, checkY, checkZ)) {
                        return false;
                    }
                }
            }
        }

        // Set dirt, but it may not be on dirt...
        // world.setBlock(originX, originY - 1, originZ, Blocks.dirt);
        // world.setBlock(originX - 1, originY - 1, originZ, Blocks.dirt);
        // world.setBlock(originX, originY - 1, originZ - 1, Blocks.dirt);
        // world.setBlock(originX - 1, originY - 1, originZ - 1, Blocks.dirt);

        // Trunk
        final int randomTrunkHeightReduction = rand.nextInt(3);
        final Block trunk = TreeBlock.TRUNK.getBlock();
        for (int relativeTrunkLevel = 0; relativeTrunkLevel
            < height - randomTrunkHeightReduction; ++relativeTrunkLevel) {
            final int actualTrunkLevel = originY + relativeTrunkLevel;
            final Block other = world.getBlock(originX, actualTrunkLevel, originZ);
            if (other.isLeaves(world, originX, actualTrunkLevel, originZ)
                || other.isReplaceable(world, originX, actualTrunkLevel, originZ)) {
                setBlockAndNotifyAdequately(world, originX, actualTrunkLevel, originZ, trunk, 2);
            }
            if (other.isLeaves(world, originX - 1, actualTrunkLevel, originZ)
                || other.isReplaceable(world, originX - 1, actualTrunkLevel, originZ)) {
                setBlockAndNotifyAdequately(world, originX - 1, actualTrunkLevel, originZ, trunk, 3);
            }
            if (other.isLeaves(world, originX, actualTrunkLevel, originZ - 1)
                || other.isReplaceable(world, originX, actualTrunkLevel, originZ - 1)) {
                setBlockAndNotifyAdequately(world, originX, actualTrunkLevel, originZ - 1, trunk, 1);
            }
            if (other.isLeaves(world, originX - 1, actualTrunkLevel, originZ - 1)
                || other.isReplaceable(world, originX - 1, actualTrunkLevel, originZ - 1)) {
                setBlockAndNotifyAdequately(world, originX - 1, actualTrunkLevel, originZ - 1, trunk, 0);
            }
        }

        int cornerLimit = rand.nextInt(2);
        int leafSlopeHalf = 1;
        boolean cornerFlag = false;

        final int minLeafHeight = height - trunkClearHeight;
        final Block leaves = TreeBlock.LEAVES.getBlock();
        final int leavesMeta = TreeBlock.LEAVES.getMetadata();
        final Block arms = WorldGenFirTree.TreeBlock.TRUNK.getBlock();
        final int armsMeta = WorldGenFirTree.TreeBlock.TRUNK.getMetadata();
        for (int relativeY = 0; relativeY <= minLeafHeight; ++relativeY) {
            final int actualY = originY + height - relativeY;
            for (int actualX = originX - cornerLimit - 1; actualX <= originX + cornerLimit; ++actualX) {
                final int cornerCheckX = actualX - originX;
                for (int actualZ = originZ - cornerLimit - 1; actualZ <= originZ + cornerLimit; ++actualZ) {
                    final int cornerCheckZ = actualZ - originZ;
                    // Corner skip
                    int counter = 0;
                    if (cornerLimit > 0) {
                        if (cornerCheckX == -cornerLimit - 1) ++counter;
                        if (cornerCheckZ == -cornerLimit - 1) ++counter;
                        if (cornerCheckX == cornerLimit) ++counter;
                        if (cornerCheckZ == cornerLimit) ++counter;
                        if (counter >= 2) {
                            continue;
                        }
                    }
                    // Arms
                    final Block other = world.getBlock(actualX, actualY, actualZ);
                    if (cornerLimit < leafSlopeHalf && relativeY < minLeafHeight) {
                        counter = 0;
                        if (cornerCheckX == -cornerLimit + 1) ++counter;
                        if (cornerCheckZ == -cornerLimit + 1) ++counter;
                        if (cornerCheckX == cornerLimit - 2) ++counter;
                        if (cornerCheckZ == cornerLimit - 2) ++counter;
                        if (counter >= 2 && (other.isLeaves(world, originX, actualY, originZ)
                            || other.isReplaceable(world, originX, actualY, originZ))) {
                            setBlockAndNotifyAdequately(world, actualX, actualY, actualZ, arms, armsMeta);
                            continue;
                        }
                    }
                    // Leaves
                    if (other.isAir(world, actualX, actualY, actualZ)
                        || other.canBeReplacedByLeaves(world, actualX, actualY, actualZ)) {
                        setBlockAndNotifyAdequately(world, actualX, actualY, actualZ, leaves, leavesMeta);
                    }
                }
            }

            if (cornerLimit >= leafSlopeHalf) {
                cornerLimit = cornerFlag ? 1 : 0;
                cornerFlag = true;
                if (++leafSlopeHalf > leafSlopeHalfMax) {
                    leafSlopeHalf = leafSlopeHalfMax;
                }
            } else {
                ++cornerLimit;
            }
        }

        return true;
    }

    public static long getLastSeed() {
        return lastSeed;
    }
}
