package paulevs.simplenetherores;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.world.gen.WorldGenEvent.ChunkDecoration;
import net.modificationstation.stationapi.api.worldgen.feature.VolumetricScatterFeature;

import java.util.ArrayList;
import java.util.List;

public class CommonListener {
	public static final List<Block> BLOCKS = new ArrayList<>();
	private static final List<Structure> STRUCTURES = new ArrayList<>();
	
	@EventListener
	public void onBlockRegister(BlockRegistryEvent event) {
		makeBlock("coal_ore").setDropItem(Item.coal);
		makeBlock("iron_ore");
		makeBlock("gold_ore");
		makeBlock("diamond_ore").setDropItem(Item.diamond);
		add(new NetherRedstoneOre(SimpleNetherOres.id("redstone_ore")));
		makeBlock("glowstone_ore").setDropItem(Item.glowstoneDust, 2, 4).setLightEmittance(0.75F);
		makeBlock("lapis_ore").setDropItem(Item.dyePowder, 4, 3, 6);
	}
	
	@EventListener
	public void onChunkDecoration(ChunkDecoration event) {
		if (event.world.dimension.id != -1) return;
		
		if (STRUCTURES.isEmpty()) {
			int minY = event.world.getBottomY() + 4;
			int maxY = event.world.getTopY() - 4;
			float iterations = (event.world.getTopY() - event.world.getBottomY()) / 32F;
			makeFeature(BLOCKS.get(0), 3, Math.round(iterations), minY, maxY);
			makeFeature(BLOCKS.get(1), 3, Math.round(iterations * 0.75F), minY, maxY);
			makeFeature(BLOCKS.get(2), 2, Math.round(iterations * 0.5F), minY, maxY);
			makeFeature(BLOCKS.get(3), 1, Math.round(iterations * 0.5F), minY, (maxY + minY) >> 1);
			makeFeature(BLOCKS.get(4), 1, Math.round(iterations * 0.5F), minY, (maxY + minY) >> 1);
			makeFeature(BLOCKS.get(5), 3, Math.round(iterations * 0.75F), minY, maxY);
			makeFeature(BLOCKS.get(6), 2, Math.round(iterations * 0.25F), minY, maxY);
		}
		
		for (Structure structure : STRUCTURES) {
			structure.generate(event.world, event.random, event.x + 8, 0, event.z + 8);
		}
	}
	
	private static NetherOre add(NetherOre ore) {
		BLOCKS.add(ore);
		return ore;
	}
	
	private static NetherOre makeBlock(String name) {
		NetherOre ore = new NetherOre(SimpleNetherOres.id(name));
		BLOCKS.add(ore);
		return ore;
	}
	
	private static void makeFeature(Block block, int radius, int iterations, int minY, int maxY) {
		NetherOreStructure ore = new NetherOreStructure(block, radius);
		STRUCTURES.add(new VolumetricScatterFeature(ore, iterations, minY, maxY));
	}
}
