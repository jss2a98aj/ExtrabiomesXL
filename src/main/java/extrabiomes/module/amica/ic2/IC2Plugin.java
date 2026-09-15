/**
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.amica.ic2;

import java.util.Collection;

import net.minecraft.world.biome.BiomeGenBase;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import extrabiomes.api.PluginEvent;
import extrabiomes.helpers.LogHelper;
import extrabiomes.lib.BiomeSettings;

public class IC2Plugin {

    private static final String MOD_ID = "IC2";
    private static final String MOD_NAME = "IndustrialCraft 2";
    private IC2API api;

    private void addBiomeBonus(Collection<Optional<? extends BiomeGenBase>> biomes, int humidityBonus,
        int nutrientsBonus) {
        for (final Optional<? extends BiomeGenBase> biome : biomes) addBiomeBonus(biome, humidityBonus, nutrientsBonus);
    }

    private void addBiomeBonus(Optional<? extends BiomeGenBase> biome, int humidityBonus, int nutrientsBonus) {
        api.addBiomeBonus(biome, humidityBonus, nutrientsBonus);
    }

    @SuppressWarnings("unchecked")
    private void addBiomeBonuses() {
        addBiomeBonus(BiomeSettings.GREENSWAMP.getBiome(), 2, 2);
        addBiomeBonus(
            Lists.newArrayList(
                BiomeSettings.AUTUMNWOODS.getBiome(),
                BiomeSettings.BIRCHFOREST.getBiome(),
                BiomeSettings.FORESTEDHILLS.getBiome(),
                BiomeSettings.FORESTEDISLAND.getBiome(),
                BiomeSettings.PINEFOREST.getBiome(),
                BiomeSettings.RAINFOREST.getBiome(),
                BiomeSettings.REDWOODFOREST.getBiome(),
                BiomeSettings.REDWOODLUSH.getBiome(),
                BiomeSettings.TEMPORATERAINFOREST.getBiome(),
                BiomeSettings.WOODLANDS.getBiome()),
            1,
            1);
        addBiomeBonus(
            Lists.newArrayList(BiomeSettings.EXTREMEJUNGLE.getBiome(), BiomeSettings.MINIJUNGLE.getBiome()),
            1,
            2);
        addBiomeBonus(
            Lists.newArrayList(
                BiomeSettings.MOUNTAINDESERT.getBiome(),
                BiomeSettings.MOUNTAINRIDGE.getBiome(),
                BiomeSettings.WASTELAND.getBiome()),
            0,
            0);
    }

    @SubscribeEvent
    public void init(PluginEvent.Init event) {
        if (api != null) {
            addBiomeBonuses();
        }
    }

    @SubscribeEvent
    public void postInit(PluginEvent.Post event) {
        api = null;
    }

    @SubscribeEvent
    public void preInit(PluginEvent.Pre event) {
        if (!Loader.isModLoaded(MOD_ID)) return;
        LogHelper.fine("Initializing %s plugin.", MOD_NAME);

        try {
            api = new IC2API();
        } catch (final Exception ex) {
            ex.printStackTrace();
            LogHelper.fine("Could not communicate with %s. Disabling plugin.", MOD_NAME);
            api = null;
        }
    }

}
