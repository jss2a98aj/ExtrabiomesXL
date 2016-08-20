/**
 * This work is licensed under the Creative Commons
 * Attribution-ShareAlike 3.0 Unported License. To view a copy of this
 * license, visit http://creativecommons.org/licenses/by-sa/3.0/.
 */

package extrabiomes.module.summa.biome;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Height;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary.Type;
import extrabiomes.lib.BiomeSettings;
import extrabiomes.lib.DecorationSettings;

public class BiomeSavanna extends ExtraBiome
{
	@Override
	public DecorationSettings getDecorationSettings() {
		return DecorationSettings.SAVANNA;
	}

    @SuppressWarnings("unchecked")
    public BiomeSavanna()
    {
		super(BiomeSettings.SAVANNA, Type.PLAINS, Type.SANDY);
        
        setColor(0xBFA243);
        setBiomeName("Savanna");
        temperature = Biome.desert.temperature;
        rainfall = Biome.desert.rainfall;
        this.setHeight(new Height(0.1F, 0.05F));
        
        spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 3, 2, 4));
    }
}
