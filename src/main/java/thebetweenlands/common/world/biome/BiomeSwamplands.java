package thebetweenlands.common.world.biome;

import thebetweenlands.common.entity.mobs.*;
import thebetweenlands.common.registries.BlockRegistry;
import thebetweenlands.common.world.WorldProviderBetweenlands;
import thebetweenlands.common.world.biome.spawning.spawners.CaveSpawnEntry;
import thebetweenlands.common.world.biome.spawning.spawners.SurfaceSpawnEntry;
import thebetweenlands.common.world.biome.spawning.spawners.TreeSpawnEntry;
import thebetweenlands.common.world.gen.biome.decorator.BiomeDecoratorSwamplands;
import thebetweenlands.common.world.gen.biome.feature.AlgaeFeature;
import thebetweenlands.common.world.gen.biome.feature.FlatLandFeature;
import thebetweenlands.common.world.gen.biome.feature.PatchFeature;
import thebetweenlands.common.world.gen.biome.feature.SiltBeachFeature;

public class BiomeSwamplands extends BiomeBetweenlands {

	public BiomeSwamplands() {
		super(new BiomeProperties("swamplands").setBaseHeight(110.0F).setHeightVariation(5F).setWaterColor(0x184220).setTemperature(0.8F).setRainfall(0.9F));
		//this.setWeight(25);
		this.setFogColor(10, 30, 12);
		this.getBiomeGenerator().setFillerBlockState(BlockRegistry.COARSE_SWAMP_DIRT.getDefaultState())
		.setTopBlockState(BlockRegistry.DEAD_GRASS.getDefaultState())
		.addFeature(new FlatLandFeature(WorldProviderBetweenlands.LAYER_HEIGHT))
		.addFeature(new PatchFeature(0.18D, 0.18D, BlockRegistry.SWAMP_GRASS.getDefaultState()))
		.addFeature(new PatchFeature(0.05D, 0.05D, BlockRegistry.SWAMP_GRASS.getDefaultState()))
		.addFeature(new PatchFeature(0.74D, 0.74D, BlockRegistry.COARSE_SWAMP_DIRT.getDefaultState()))
		.addFeature(new PatchFeature(0.65D, 0.65D, BlockRegistry.MUD.getDefaultState(), 1.0D / 1.35D, 1.72D))
		.addFeature(new AlgaeFeature())
		.addFeature(new SiltBeachFeature(0.98F))
		.setDecorator(new BiomeDecoratorSwamplands());
		this.setFoliageColors(0x2AFF00, 0x2AFF00);

		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityFirefly.class, (short) 20).setSpawnCheckRadius(32.0D));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityGecko.class, (short) 40).setGroupSize(1, 3).setSpawnCheckRadius(32.0D).setSpawningInterval(600));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityMireSnail.class, (short) 60).setGroupSize(1, 5).setSpawnCheckRadius(32.0D).setSpawningInterval(800));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityFrog.class, (short) 26).setCanSpawnOnWater(true).setGroupSize(1, 3).setSpawnCheckRadius(32.0D).setSpawningInterval(100));
		this.blSpawnEntries.add(new CaveSpawnEntry(EntityBlindCaveFish.class, (short) 30).setGroupSize(3, 5).setSpawnCheckRadius(32.0D));
		this.blSpawnEntries.add(new TreeSpawnEntry(EntitySporeling.class, (short) 120).setGroupSize(2, 5).setSpawnCheckRadius(32.0D));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityGiantToad.class, (short) 12).setSpawnCheckRadius(64.0D).setSpawningInterval(800));
		this.blSpawnEntries.add(new CaveSpawnEntry(EntityGiantToad.class, (short) 8).setSpawnCheckRadius(64.0D).setSpawningInterval(800));

//		this.blSpawnEntries.add(new TarSpawnEntry(EntityTarBeast.class, (short) 80).setHostile(true)); TODO
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityLeech.class, (short) 35).setHostile(true));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityAngler.class, (short) 40).setHostile(true));
		this.blSpawnEntries.add(new CaveSpawnEntry(EntityAngler.class, (short) 35).setHostile(true).setGroupSize(1, 3));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntitySwampHag.class, (short) 80).setHostile(true));
		this.blSpawnEntries.add(new CaveSpawnEntry(EntitySwampHag.class, (short) 140).setHostile(true).setSpawnCheckRadius(6.0D).setGroupSize(1, 3));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityWight.class, (short) 12).setHostile(true).setSpawnCheckRadius(64.0D));
		this.blSpawnEntries.add(new CaveSpawnEntry(EntityWight.class, (short) 18).setHostile(true).setSpawnCheckRadius(64.0D));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityBloodSnail.class, (short) 25).setHostile(true));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityChiromaw.class, (short) 12).setHostile(true).setSpawnCheckRadius(30.0D));
		this.blSpawnEntries.add(new CaveSpawnEntry(EntityChiromaw.class, (short) 40).setHostile(true).setSpawnCheckRadius(20.0D).setGroupSize(1, 3));
	}

}
