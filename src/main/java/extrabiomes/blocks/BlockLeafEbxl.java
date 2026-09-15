/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extrabiomes.Extrabiomes;

public class BlockLeafEbxl extends BlockLeaves {

    private final String[] types;
    private final IIcon[] betterFoliageTextures;

    public BlockLeafEbxl(String... types) {
        this.types = types;
        betterFoliageTextures = new IIcon[types.length];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) & 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return field_150129_M[Blocks.leaves.isOpaqueCube() ? 1 : 0][(meta % 4) % types.length];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List itemList) {
        for (int meta = 0; meta < types.length; ++meta) {
            itemList.add(new ItemStack(itemIn, 1, meta));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        field_150129_M[0] = new IIcon[types.length];
        field_150129_M[1] = new IIcon[types.length];
        for (int i = 0; i < types.length; ++i) {
            String pathFragment = Extrabiomes.TEXTURE_PATH + types[i];
            field_150129_M[0][i] = reg.registerIcon(pathFragment + "fancy");
            field_150129_M[1][i] = reg.registerIcon(pathFragment + "fast");
            betterFoliageTextures[i] = reg.registerIcon(Extrabiomes.TEXTURE_PATH + "better_" + types[i]);
        }
    }

    @Override
    public String[] func_150125_e() {
        return types;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (world.isRemote) {
            return;
        }
        int meta = world.getBlockMetadata(x, y, z);

        if ((meta & 8) != 0 && (meta & 4) == 0) {
            byte decayRange = 8;
            int searchRange = decayRange + 1;
            byte searchRangeMax = 32;
            int SearchRangeSquared = searchRangeMax * searchRangeMax;
            int halfSearchRangeMax = searchRangeMax / 2;

            if (field_150128_a == null) {
                field_150128_a = new int[searchRangeMax * searchRangeMax * searchRangeMax];
            }

            int searchX;

            if (world.checkChunksExist(
                x - searchRange,
                y - searchRange,
                z - searchRange,
                x + searchRange,
                y + searchRange,
                z + searchRange)) {
                int searchY;
                int searchZ;

                for (searchX = -decayRange; searchX <= decayRange; ++searchX) {
                    for (searchY = -decayRange; searchY <= decayRange; ++searchY) {
                        for (searchZ = -decayRange; searchZ <= decayRange; ++searchZ) {
                            Block block = world.getBlock(x + searchX, y + searchY, z + searchZ);
                            int position = (searchX + halfSearchRangeMax) * SearchRangeSquared
                                + (searchY + halfSearchRangeMax) * searchRangeMax
                                + searchZ
                                + halfSearchRangeMax;
                            if (!block.canSustainLeaves(world, x + searchX, y + searchY, z + searchZ)) {
                                if (block.isLeaves(world, x + searchX, y + searchY, z + searchZ)) {
                                    field_150128_a[position] = -2;
                                } else {
                                    field_150128_a[position] = -1;
                                }
                            } else {
                                field_150128_a[position] = 0;
                            }
                        }
                    }
                }

                for (searchX = 1; searchX <= decayRange; ++searchX) {
                    for (searchY = -decayRange; searchY <= decayRange; ++searchY) {
                        for (searchZ = -decayRange; searchZ <= decayRange; ++searchZ) {
                            for (int i = -decayRange; i <= decayRange; ++i) {
                                if (field_150128_a[(searchY + halfSearchRangeMax) * SearchRangeSquared
                                    + (searchZ + halfSearchRangeMax) * searchRangeMax
                                    + i
                                    + halfSearchRangeMax] == searchX - 1) {
                                    int position = (searchY + halfSearchRangeMax - 1) * SearchRangeSquared
                                        + (searchZ + halfSearchRangeMax) * searchRangeMax
                                        + i
                                        + halfSearchRangeMax;
                                    if (field_150128_a[position] == -2) {
                                        field_150128_a[position] = searchX;
                                    }
                                    position = (searchY + halfSearchRangeMax + 1) * SearchRangeSquared
                                        + (searchZ + halfSearchRangeMax) * searchRangeMax
                                        + i
                                        + halfSearchRangeMax;
                                    if (field_150128_a[position] == -2) {
                                        field_150128_a[position] = searchX;
                                    }
                                    position = (searchY + halfSearchRangeMax) * SearchRangeSquared
                                        + (searchZ + halfSearchRangeMax - 1) * searchRangeMax
                                        + i
                                        + halfSearchRangeMax;
                                    if (field_150128_a[position] == -2) {
                                        field_150128_a[position] = searchX;
                                    }
                                    position = (searchY + halfSearchRangeMax) * SearchRangeSquared
                                        + (searchZ + halfSearchRangeMax + 1) * searchRangeMax
                                        + i
                                        + halfSearchRangeMax;
                                    if (field_150128_a[position] == -2) {
                                        field_150128_a[position] = searchX;
                                    }
                                    position = (searchY + halfSearchRangeMax) * SearchRangeSquared
                                        + (searchZ + halfSearchRangeMax) * searchRangeMax
                                        + (i + halfSearchRangeMax - 1);
                                    if (field_150128_a[position] == -2) {
                                        field_150128_a[position] = searchX;
                                    }
                                    position = (searchY + halfSearchRangeMax) * SearchRangeSquared
                                        + (searchZ + halfSearchRangeMax) * searchRangeMax
                                        + i
                                        + halfSearchRangeMax
                                        + 1;
                                    if (field_150128_a[position] == -2) {
                                        field_150128_a[position] = searchX;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            searchX = field_150128_a[halfSearchRangeMax * SearchRangeSquared + halfSearchRangeMax * searchRangeMax
                + halfSearchRangeMax];

            if (searchX >= 0) {
                world.setBlockMetadataWithNotify(x, y, z, meta & -9, 4);
            } else {
                removeLeaves(world, x, y, z);
            }
        }
    }

    // Access transformer "public net.minecraft.block.BlockLeaves func_150126_e" did not work on BlockLeaves?
    private void removeLeaves(World world, int x, int y, int z) {
        dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.setBlockToAir(x, y, z);
    }

    // NotFine, Angelica, Optifine compat

    @Override
    public boolean isOpaqueCube() {
        return Blocks.leaves.isOpaqueCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return Blocks.leaves.shouldSideBeRendered(world, x, y, z, side);
    }

    // Better Foliage (?) compat

    public IIcon getIconBetterLeaves(int metadata, float randomIndex) {
        return betterFoliageTextures[metadata & 3];
    }

    public IIcon getIconFallingLeaves(int metadata) {
        return field_150129_M[1][metadata & 3];
    }

}
