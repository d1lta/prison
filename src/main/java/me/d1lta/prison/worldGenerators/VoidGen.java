package me.d1lta.prison.worldGenerators;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

public class VoidGen extends ChunkGenerator {

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(World world) {
        return Collections.<BlockPopulator>emptyList();
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunkData = super.createChunkData(world);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                biome.setBiome(x, z, Biome.PLAINS);
            }
        }
        return chunkData;
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 128, 0);
    }
}
