/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.fabrica.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import extrabiomes.Extrabiomes;
import extrabiomes.api.Stuff;
import extrabiomes.blocks.BlockCustomFence;
import extrabiomes.blocks.BlockRedRock;
import extrabiomes.events.BlockActiveEvent.AcaciaStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.AutumnStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.BaldCypressStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.CypressStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.FenceActiveEvent;
import extrabiomes.events.BlockActiveEvent.FenceGateActiveEvent;
import extrabiomes.events.BlockActiveEvent.FirStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.JapaneseMapleStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.NewWoodSlabActiveEvent;
import extrabiomes.events.BlockActiveEvent.PlankActiveEvent;
import extrabiomes.events.BlockActiveEvent.RainbowEucalyptusStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.RedCobbleStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.RedRockActiveEvent;
import extrabiomes.events.BlockActiveEvent.RedRockBrickStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.RedRockSlabActiveEvent;
import extrabiomes.events.BlockActiveEvent.RedwoodStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.SakuraBlossomStairsActiveEvent;
import extrabiomes.events.BlockActiveEvent.WallActiveEvent;
import extrabiomes.events.BlockActiveEvent.WoodDoorActiveEvent;
import extrabiomes.events.BlockActiveEvent.WoodSlabActiveEvent;
import extrabiomes.module.fabrica.block.BlockCustomWall;
import extrabiomes.module.fabrica.block.BlockCustomWood;
import extrabiomes.module.fabrica.block.BlockCustomWoodSlab;
import extrabiomes.module.fabrica.block.BlockNewWoodSlab;
import extrabiomes.module.fabrica.block.BlockRedRockSlab;
import extrabiomes.proxy.CommonProxy;

public class RecipeManager {

    private ItemStack plankAcaciaItem;
    private ItemStack plankFirItem;
    private ItemStack plankRedwoodItem;
    private ItemStack plankCypressItem;
    private ItemStack plankBaldCypressItem;
    private ItemStack plankJapaneseMapleItem;
    private ItemStack plankRainbowEucalyptusItem;
    private ItemStack plankAutumnItem;
    private ItemStack plankSakuraBlossomItem;
    private ItemStack redRockItem;
    private ItemStack redCobbleItem;
    private ItemStack redRockBrickItem;

    private final List<ItemStack> acaciaLogs = new ArrayList<>();
    private final List<ItemStack> firLogs = new ArrayList<>();
    private final List<ItemStack> oakLogs = new ArrayList<>();
    private final List<ItemStack> redwoodLogs = new ArrayList<>();
    private final List<ItemStack> cypressLogs = new ArrayList<>();
    private final List<ItemStack> baldCypressLogs = new ArrayList<>();
    private final List<ItemStack> autumnLogs = new ArrayList<>();
    private final List<ItemStack> japanesemapleLogs = new ArrayList<>();
    private final List<ItemStack> rainboweucalyptusLogs = new ArrayList<>();
    private final List<ItemStack> sakurablossomLogs = new ArrayList<>();

    @SubscribeEvent
    public void acaciaStairsRecipeHandler(AcaciaStairsActiveEvent event) {
        if (plankAcaciaItem != null) addStairsRecipe(plankAcaciaItem, event.block);
    }

    private void addStairsRecipe(ItemStack source, Block target) {
        IRecipe recipe = new ShapedOreRecipe(
            new ItemStack(target, 4),
            new String[] { "r  ", "rr ", "rrr" },
            'r',
            source);
        Extrabiomes.proxy.addRecipe(recipe);
        recipe = new ShapedOreRecipe(new ItemStack(target, 4), new String[] { "  r", " rr", "rrr" }, 'r', source);
        Extrabiomes.proxy.addRecipe(recipe);
    }

    @SubscribeEvent
    public void firStairsRecipeHandler(FirStairsActiveEvent event) {
        if (plankFirItem != null) addStairsRecipe(plankFirItem, event.block);
    }

    @SubscribeEvent
    public void cypressStairsRecipeHandler(CypressStairsActiveEvent event) {
        if (plankCypressItem != null) addStairsRecipe(plankCypressItem, event.block);
    }

    @SubscribeEvent
    public void baldCypressStairsRecipeHandler(BaldCypressStairsActiveEvent event) {
        if (plankBaldCypressItem != null) addStairsRecipe(plankBaldCypressItem, event.block);
    }

    @SubscribeEvent
    public void japaneseMapleStairsRecipeHandler(JapaneseMapleStairsActiveEvent event) {
        if (plankJapaneseMapleItem != null) addStairsRecipe(plankJapaneseMapleItem, event.block);
    }

    @SubscribeEvent
    public void autumnStairsRecipeHandler(AutumnStairsActiveEvent event) {
        if (plankAutumnItem != null) addStairsRecipe(plankAutumnItem, event.block);
    }

    @SubscribeEvent
    public void rainbowEucalyptusStairsRecipeHandler(RainbowEucalyptusStairsActiveEvent event) {
        if (plankRainbowEucalyptusItem != null) addStairsRecipe(plankRainbowEucalyptusItem, event.block);
    }

    @SubscribeEvent
    public void rainbowSakuraBlossomStairsRecipeHandler(SakuraBlossomStairsActiveEvent event) {
        if (plankSakuraBlossomItem != null) addStairsRecipe(plankSakuraBlossomItem, event.block);
    }

    @SubscribeEvent
    public void plankRecipeHandler(PlankActiveEvent event) {
        ItemStack planks = new ItemStack(event.block, 4, BlockCustomWood.BlockType.ACACIA.metadata());
        for (final ItemStack itemstack : acaciaLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        plankAcaciaItem = new ItemStack(event.block, 1, BlockCustomWood.BlockType.ACACIA.metadata());

        planks = new ItemStack(event.block, 4, BlockCustomWood.BlockType.FIR.metadata());
        for (final ItemStack itemstack : firLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        plankFirItem = new ItemStack(event.block, 1, BlockCustomWood.BlockType.FIR.metadata());

        planks = new ItemStack(Blocks.planks, 4);
        for (final ItemStack itemstack : oakLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        planks = new ItemStack(event.block, 4, BlockCustomWood.BlockType.REDWOOD.metadata());
        for (final ItemStack itemstack : redwoodLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        plankRedwoodItem = new ItemStack(event.block, 1, BlockCustomWood.BlockType.REDWOOD.metadata());

        planks = new ItemStack(event.block, 4, BlockCustomWood.BlockType.CYPRESS.metadata());
        for (final ItemStack itemstack : cypressLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        plankCypressItem = new ItemStack(event.block, 1, BlockCustomWood.BlockType.CYPRESS.metadata());

        planks = new ItemStack(event.block, 4, BlockCustomWood.BlockType.BALD_CYPRESS.metadata());
        for (final ItemStack itemstack : baldCypressLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        plankBaldCypressItem = new ItemStack(event.block, 1, BlockCustomWood.BlockType.BALD_CYPRESS.metadata());

        planks = new ItemStack(event.block, 4, BlockCustomWood.BlockType.AUTUMN.metadata());
        for (final ItemStack itemstack : autumnLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        plankAutumnItem = new ItemStack(event.block, 1, BlockCustomWood.BlockType.AUTUMN.metadata());

        planks = new ItemStack(event.block, 4, BlockCustomWood.BlockType.JAPANESE_MAPLE.metadata());
        for (final ItemStack itemstack : japanesemapleLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        plankJapaneseMapleItem = new ItemStack(event.block, 1, BlockCustomWood.BlockType.JAPANESE_MAPLE.metadata());

        planks = new ItemStack(event.block, 4, BlockCustomWood.BlockType.RAINBOW_EUCALYPTUS.metadata());
        for (final ItemStack itemstack : rainboweucalyptusLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        plankRainbowEucalyptusItem = new ItemStack(
            event.block,
            1,
            BlockCustomWood.BlockType.RAINBOW_EUCALYPTUS.metadata());

        planks = new ItemStack(event.block, 2, BlockCustomWood.BlockType.SAKURA_BLOSSOM.metadata());
        for (final ItemStack itemstack : sakurablossomLogs) {
            final IRecipe recipe = new ShapelessOreRecipe(planks, itemstack);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        plankSakuraBlossomItem = new ItemStack(event.block, 1, BlockCustomWood.BlockType.SAKURA_BLOSSOM.metadata());
    }

    @SubscribeEvent
    public void redCobbleStairsRecipeHandler(RedCobbleStairsActiveEvent event) {
        if (redCobbleItem != null) addStairsRecipe(redCobbleItem, event.block);
    }

    @SubscribeEvent
    public void redRockBrickStairsRecipeHandler(RedRockBrickStairsActiveEvent event) {
        if (redRockBrickItem != null) addStairsRecipe(redRockBrickItem, event.block);
    }

    @SubscribeEvent
    public void redRockRecipeHandler(RedRockActiveEvent event) {
        final CommonProxy proxy = Extrabiomes.proxy;

        redRockItem = new ItemStack(event.block, 1, BlockRedRock.BlockType.RED_ROCK.metadata());
        redCobbleItem = new ItemStack(event.block, 1, BlockRedRock.BlockType.RED_COBBLE.metadata());
        redRockBrickItem = new ItemStack(event.block, 1, BlockRedRock.BlockType.RED_ROCK_BRICK.metadata());

        IRecipe recipe = new ShapelessOreRecipe(
            new ItemStack(Items.clay_ball, 4),
            redCobbleItem,
            Items.water_bucket,
            Items.water_bucket,
            Items.water_bucket);
        proxy.addRecipe(recipe);

        recipe = new ShapedOreRecipe(
            new ItemStack(event.block, 4, BlockRedRock.BlockType.RED_ROCK_BRICK.metadata()),
            new String[] { "rr", "rr" },
            'r',
            redRockItem);
        proxy.addRecipe(recipe);

        proxy.addSmelting(
            Item.getItemFromBlock(event.block),
            BlockRedRock.BlockType.RED_COBBLE.metadata(),
            redRockItem,
            0.1F);
    }

    @SubscribeEvent
    public void redRockSlabRecipeHandler(RedRockSlabActiveEvent event) {
        final CommonProxy proxy = Extrabiomes.proxy;

        if (redRockItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockRedRockSlab.BlockType.REDROCK.metadata()),
                new String[] { "rrr" },
                'r',
                redRockItem);
            proxy.addRecipe(recipe);
        }

        if (redCobbleItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockRedRockSlab.BlockType.REDCOBBLE.metadata()),
                new String[] { "rrr" },
                'r',
                redCobbleItem);
            proxy.addRecipe(recipe);
        }

        if (redRockBrickItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockRedRockSlab.BlockType.REDROCKBRICK.metadata()),
                new String[] { "rrr" },
                'r',
                redRockBrickItem);
            proxy.addRecipe(recipe);
        }
    }

    @SubscribeEvent
    public void redwoodStairsRecipeHandler(RedwoodStairsActiveEvent event) {
        if (plankRedwoodItem != null) {
            addStairsRecipe(plankRedwoodItem, event.block);
        }
    }

    @SubscribeEvent
    public void wallRecipeHandler(WallActiveEvent event) {

        if (redCobbleItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockCustomWall.BlockType.RED_COBBLE.metadata()),
                new String[] { "ppp", "ppp" },
                'p',
                redCobbleItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }
    }

    @SubscribeEvent
    public void fenceRecipeHandler(FenceActiveEvent event) {
        ItemStack sticks = new ItemStack(Items.stick, 1);
        ItemStack planks;
        ItemStack fences;
        for (int i = 0; i < BlockCustomFence.BlockType.values().length; i++) {
            planks = new ItemStack(Stuff.planks.get(), 1, BlockCustomFence.BlockType.values()[i].metadata());
            fences = new ItemStack(event.block, 3, i);

            final IRecipe recipe = new ShapedOreRecipe(fences, new String[] { "psp", "psp" }, 'p', planks, 's', sticks);
            Extrabiomes.proxy.addRecipe(recipe);
            // blockType.texture = iconRegister.registerIcon(Extrabiomes.TEXTURE_PATH + "planks" +
            // blockType.name().toLowerCase(Locale.ENGLISH));
        }
    }

    @SubscribeEvent
    public void fenceGateRecipeHandler(FenceGateActiveEvent event) {
        final IRecipe recipe = new ShapedOreRecipe(
            event.gate,
            new String[] { "sps", "sps" },
            'p',
            event.wood,
            's',
            new ItemStack(Items.stick, 1));
        Extrabiomes.proxy.addRecipe(recipe);

    }

    @SubscribeEvent
    public void newWoodSlabRecipeHandler(NewWoodSlabActiveEvent event) {
        if (plankSakuraBlossomItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockNewWoodSlab.BlockType.SAKURA_BLOSSOM.metadata()),
                new String[] { "ppp" },
                'p',
                plankSakuraBlossomItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }
    }

    @SubscribeEvent
    public void woodSlabRecipeHandler(WoodSlabActiveEvent event) {

        if (plankAcaciaItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockCustomWoodSlab.BlockType.ACACIA.metadata()),
                new String[] { "ppp" },
                'p',
                plankAcaciaItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        if (plankFirItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockCustomWoodSlab.BlockType.FIR.metadata()),
                new String[] { "ppp" },
                'p',
                plankFirItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        if (plankRedwoodItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockCustomWoodSlab.BlockType.REDWOOD.metadata()),
                new String[] { "ppp" },
                'p',
                plankRedwoodItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        if (plankBaldCypressItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockCustomWoodSlab.BlockType.BALD_CYPRESS.metadata()),
                new String[] { "ppp" },
                'p',
                plankBaldCypressItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        if (plankCypressItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockCustomWoodSlab.BlockType.CYPRESS.metadata()),
                new String[] { "ppp" },
                'p',
                plankCypressItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        if (plankAutumnItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockCustomWoodSlab.BlockType.AUTUMN.metadata()),
                new String[] { "ppp" },
                'p',
                plankAutumnItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        if (plankJapaneseMapleItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockCustomWoodSlab.BlockType.JAPANESE_MAPLE.metadata()),
                new String[] { "ppp" },
                'p',
                plankJapaneseMapleItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }

        if (plankRainbowEucalyptusItem != null) {
            final IRecipe recipe = new ShapedOreRecipe(
                new ItemStack(event.block, 6, BlockCustomWoodSlab.BlockType.RAINBOW_EUCALYPTUS.metadata()),
                new String[] { "ppp" },
                'p',
                plankRainbowEucalyptusItem);
            Extrabiomes.proxy.addRecipe(recipe);
        }
    }

    @SubscribeEvent
    public void woodDoorRecipeHandler(WoodDoorActiveEvent event) {
        final IRecipe recipe = new ShapedOreRecipe(event.door, new String[] { "pp", "pp", "pp" }, 'p', event.wood);
        Extrabiomes.proxy.addRecipe(recipe);
    }
}
