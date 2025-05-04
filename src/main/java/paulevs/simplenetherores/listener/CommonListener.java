package paulevs.simplenetherores.listener;

import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.level.structure.Structure;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.world.gen.WorldGenEvent.ChunkDecoration;
import net.modificationstation.stationapi.api.worldgen.feature.VolumetricScatterFeature;
import paulevs.simplenetherores.block.NetherOre;
import paulevs.simplenetherores.structure.DeepNetherOreStructure;
import paulevs.simplenetherores.structure.NetherOreStructure;
import paulevs.simplenetherores.block.NetherRedstoneOre;
import paulevs.simplenetherores.SimpleNetherOres;

import java.util.ArrayList;
import java.util.List;

public class CommonListener {
	public static final List<Block> BLOCKS = new ArrayList<>();
	private static final List<Structure> STRUCTURES = new ArrayList<>();
	
	private static Block coalOre;
	private static Block ironOre;
	private static Block goldOre;
	private static Block diamondOre;
	private static Block redstoneOre;
	public static Block glowstoneOre;
	private static Block lapisOre;
	
	private static Block deepCoalOre;
	private static Block deepIronOre;
	private static Block deepGoldOre;
	private static Block deepDiamondOre;
	private static Block deepRedstoneOre;
	private static Block deepGlowstoneOre;
	private static Block deepLapisOre;
	
	@EventListener
	public void onBlockRegister(BlockRegistryEvent event) {
		coalOre = makeBlock("coal_ore").setDropItem(Item.coal);
		ironOre = makeBlock("iron_ore");
		goldOre = makeBlock("gold_ore");
		diamondOre = makeBlock("diamond_ore").setDropItem(Item.diamond);
		redstoneOre = add(new NetherRedstoneOre(SimpleNetherOres.id("redstone_ore")));
		glowstoneOre = makeBlock("glowstone_ore").setDropItem(Item.glowstoneDust, 2, 4).setLightEmittance(0.75F);
		lapisOre = makeBlock("lapis_ore").setDropItem(Item.dyePowder, 4, 3, 6);
		
		if (FabricLoader.getInstance().isModLoaded("bnb")) {
			deepCoalOre = makeBlock("deep_coal_ore").setDropItem(Item.coal);
			deepIronOre = makeBlock("deep_iron_ore");
			deepGoldOre = makeBlock("deep_gold_ore");
			deepDiamondOre = makeBlock("deep_diamond_ore").setDropItem(Item.diamond);
			deepRedstoneOre = add(new NetherRedstoneOre(SimpleNetherOres.id("deep_redstone_ore")));
			deepGlowstoneOre = makeBlock("deep_glowstone_ore").setDropItem(Item.glowstoneDust, 2, 4).setLightEmittance(0.75F);
			deepLapisOre = makeBlock("deep_lapis_ore").setDropItem(Item.dyePowder, 4, 3, 6);
		}
	}
	
	@EventListener
	public void onChunkDecoration(ChunkDecoration event) {
		if (event.world.dimension.id != -1) return;
		
		if (STRUCTURES.isEmpty()) {
			int minY = event.world.getBottomY() + 4;
			int maxY = event.world.getTopY() - 4;
			float iterations = (event.world.getTopY() - event.world.getBottomY()) / 16F;
			int count1 = Math.max(1, Math.round(iterations));
			int count2 = Math.max(1, Math.round(iterations * 0.75F));
			int count3 = Math.max(1, Math.round(iterations * 0.5F));
			
			makeFeature(coalOre, 2, Math.max(1, Math.round(iterations)), minY, maxY);
			makeFeature(ironOre, 2, count1, minY, maxY);
			makeFeature(goldOre, 2, count2, minY, maxY);
			makeFeature(diamondOre, 1, count2, minY, (maxY + minY) >> 1);
			makeFeature(redstoneOre, 2, count1, minY, (maxY + minY) >> 1);
			makeFeature(glowstoneOre, 2, count1, minY, maxY);
			makeFeature(lapisOre, 1, count3, minY, maxY);
			
			if (FabricLoader.getInstance().isModLoaded("bnb")) {
				count3 = count2;
				count2 = count1;
				count1 = Math.max(1, Math.round(iterations * 1.5F));
				makeFeatureBNB(deepCoalOre, 2, Math.max(1, Math.round(iterations)), minY, maxY);
				makeFeatureBNB(deepIronOre, 2, count1, minY, maxY);
				makeFeatureBNB(deepGoldOre, 2, count2, minY, maxY);
				makeFeatureBNB(deepDiamondOre, 1, count2, minY, (maxY + minY) >> 1);
				makeFeatureBNB(deepRedstoneOre, 2, count1, minY, (maxY + minY) >> 1);
				makeFeatureBNB(deepGlowstoneOre, 2, count1, minY, maxY);
				makeFeatureBNB(deepLapisOre, 1, count3, minY, maxY);
			}
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
	
	private static void makeFeatureBNB(Block block, int radius, int iterations, int minY, int maxY) {
		NetherOreStructure ore = new DeepNetherOreStructure(block, radius);
		STRUCTURES.add(new VolumetricScatterFeature(ore, iterations, minY, maxY));
	}
}
