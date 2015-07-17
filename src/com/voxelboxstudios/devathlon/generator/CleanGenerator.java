package com.voxelboxstudios.devathlon.generator;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class CleanGenerator extends ChunkGenerator {

	/** Generation **/
	
	@Override
	public short[][] generateExtBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
		return new short[256 / 16][];
	}
	
}
