package extrabiomes.blocks;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extrabiomes.Extrabiomes;

public class BlockCustomFenceGate extends BlockFenceGate {

    private final String textureName;
    private IIcon texture;

    public BlockCustomFenceGate(String name) {
        super();
        setHardness(2.0F);
        setResistance(5.0F);
        setBlockName("extrabiomes.fencegate." + name);
        textureName = "planks" + name;
        setCreativeTab(Extrabiomes.tabsEBXL);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        texture = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + textureName);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return texture;
    }

}
