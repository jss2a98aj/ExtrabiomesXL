package extrabiomes.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extrabiomes.Extrabiomes;
import extrabiomes.api.UseLogTurnerEvent;
import extrabiomes.lib.BlockSettings;

public class BlockKneeLog extends BlockLog {

    private final BlockSettings settings;

    private final IIcon[] textures = new IIcon[9];
    private static int renderId = 32;
    private String treeType = "knee";
    private ItemStack droppedItem;

    public BlockKneeLog(BlockSettings settings, String treeType) {
        super();

        this.settings = settings;
        this.treeType = treeType;
        setStepSound(Block.soundTypeWood);
        setHardness(2.0F);
        setResistance(Blocks.log.getExplosionResistance(null) * 5.0F);
        setCreativeTab(Extrabiomes.tabsEBXL);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {

        textures[0] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "log" + treeType + "knee1");
        textures[1] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "log" + treeType + "knee2");
        textures[2] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "log" + treeType + "knee3");
        textures[3] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "log" + treeType + "knee4");

        textures[4] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "log" + treeType + "side1");
        textures[5] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "log" + treeType + "side2");
        textures[6] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "log" + treeType + "top");
        textures[7] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "log" + treeType + "top");

        textures[8] = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "todo");

    }

    @Override
    public IIcon getIcon(int side, int metadata) {

        return switch (side) {
            case 0 -> textures[bottopIIcon(metadata)];
            case 1 -> textures[topIIcon(metadata)];
            case 2 -> textures[southIIcon(metadata)];
            case 3 -> textures[northIIcon(metadata)];
            case 4 -> textures[eastIIcon(metadata)];
            case 5 -> textures[westIIcon(metadata)];
            default -> textures[4];
        };
    }

    private int topIIcon(int orientation) {
        return switch (orientation) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4, 8 -> 4;
            case 5, 9 -> 5;
            case 6, 11 -> 7;
            case 7, 10 -> 6;
            default -> 8;
        };
    }

    private int bottopIIcon(int orientation) {
        return switch (orientation) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4, 8 -> 6;
            case 5, 9 -> 7;
            case 6, 11 -> 4;
            case 7, 10 -> 5;
            default -> 8;
        };
    }

    private int eastIIcon(int orientation) {
        return switch (orientation) {
            case 0, 6 -> 5;
            case 1, 4 -> 7;
            case 2, 7 -> 6;
            case 3, 5 -> 4;
            case 8 -> 0;
            case 9 -> 1;
            case 10 -> 2;
            case 11 -> 3;
            default -> 8;
        };
    }

    private int westIIcon(int orientation) {
        return switch (orientation) {
            case 0, 5 -> 6;
            case 1, 4 -> 4;
            case 2, 7 -> 5;
            case 3, 6 -> 7;
            case 8 -> 1;
            case 9 -> 0;
            case 10 -> 3;
            case 11 -> 2;
            default -> 8;
        };
    }

    private int southIIcon(int orientation) {
        return switch (orientation) {
            case 0, 8 -> 4;
            case 1, 11 -> 5;
            case 2, 9 -> 7;
            case 3, 10 -> 6;
            case 4 -> 0;
            case 5, 7 -> 1;
            case 6 -> 2;
            default -> 8;
        };
    }

    private int northIIcon(int orientation) {
        return switch (orientation) {
            case 0, 11 -> 7;
            case 1, 8 -> 6;
            case 2, 9 -> 4;
            case 3, 10 -> 5;
            case 4 -> 1;
            case 5 -> 0;
            case 6 -> 3;
            case 7 -> 2;
            default -> 8;
        };
    }

    public static void setRenderId(int renderId) {
        BlockKneeLog.renderId = renderId;
    }

    @Override
    public int getRenderType() {
        return renderId;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
    }

    public BlockKneeLog setDroppedItemStack(ItemStack stack) {
        this.droppedItem = stack;

        return this;
    }

    @Override
    public Item getItemDropped(int metadata, Random rand, int unused) {
        if (this.droppedItem != null) {
            return this.droppedItem.getItem();
        } else {
            return Item.getItemFromBlock(this);
        }
    }

    @Override
    public int damageDropped(int metadata) {
        if (this.droppedItem != null) {
            return this.droppedItem.getItemDamage();
        } else {
            return 0;
        }
    }

    @SubscribeEvent
    public void onUseLogTurnerEvent(UseLogTurnerEvent event) {
        Block block = event.world.getBlock(event.x, event.y, event.z);

        if (block == this) {
            final Block wood = Blocks.log;
            event.world.playSoundEffect(
                event.x + 0.5F,
                event.y + 0.5F,
                event.z + 0.5F,
                wood.stepSound.soundName,
                (wood.stepSound.getVolume() + 1.0F) / 2.0F,
                wood.stepSound.getPitch() * 1.55F);

            if (!event.world.isRemote) {
                int metadata = event.world.getBlockMetadata(event.x, event.y, event.z);

                if (event.entityPlayer.isSneaking()) {
                    metadata = switch (BlockPistonBase
                        .determineOrientation(event.world, event.x, event.y, event.z, event.entityLiving)) {
                        case 0 -> switch (metadata) {
                                case 9 -> 4;
                                case 4 -> 8;
                                case 8 -> 5;
                                default -> 9;
                            };
                        case 1 -> switch (metadata) {
                                case 6 -> 11;
                                case 10 -> 6;
                                case 11 -> 7;
                                default -> 10;
                            };
                        case 2 -> switch (metadata) {
                                case 3 -> 10;
                                case 10 -> 2;
                                case 2 -> 9;
                                default -> 3;
                            };
                        case 3 -> switch (metadata) {
                                case 1 -> 11;
                                case 11 -> 0;
                                case 0 -> 8;
                                default -> 1;
                            };
                        case 4 -> switch (metadata) {
                                case 2 -> 7;
                                case 7 -> 1;
                                case 1 -> 4;
                                default -> 2;
                            };
                        default -> switch (metadata) {
                                case 0 -> 6;
                                case 6 -> 3;
                                case 3 -> 5;
                                default -> 0;
                            };
                    };
                } else {
                    // Increment the orentation
                    if (BlockPistonBase.determineOrientation(event.world, event.x, event.y, event.z, event.entityLiving)
                        % 2 == 0) {
                        metadata = (++metadata > 11) ? 0 : metadata;
                    } else {
                        metadata = (--metadata < 0) ? 11 : metadata;
                    }
                }

                event.world.setBlock(event.x, event.y, event.z, block, metadata, 3);

                // unturned = false;
            }
            event.setHandled();

            // Patch for skipping over block ids that are out of order
            event.setCanceled(true);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

        world.setBlock(x, y, z, this, 3, 3);
    }

}
