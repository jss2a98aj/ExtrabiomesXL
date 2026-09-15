/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.summa.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

class WorldGenEelGrass extends WorldGenerator {

    private final Block block;
    private final int metaData;

    WorldGenEelGrass(Block block, int metaData) {
        this.block = block;
        this.metaData = metaData;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        int maxDepth = 4;
        while (world.isAirBlock(x, y, z) && y > 0) {
            y--;
        }

        Block checkBlock = world.getBlock(x, y, z);
        int depth = 0;
        while ((checkBlock.equals(Blocks.water) || checkBlock.equals(Blocks.flowing_water)) && y > 0 && maxDepth > 0) {
            depth++;
            y--;
            maxDepth--;
            checkBlock = world.getBlock(x, y, z);
        }

        y++;

        if (depth > 1 && block.canBlockStay(world, x, y, z)) {
            world.setBlock(x, y, z, block, metaData, 0);
        }

        return true;
    }

}
