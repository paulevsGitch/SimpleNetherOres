package paulevs.simplenetherores;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class CommonListener {
	public static final List<Block> BLOCKS = new ArrayList<>();
	
	@EventListener
	public void onBlockRegister(BlockRegistryEvent event) {
		make("coal_ore").setDropItem(Item.coal);
		make("iron_ore");
		make("gold_ore");
		make("diamond_ore").setDropItem(Item.diamond);
		add(new NetherRedstoneOre(SimpleNetherOres.id("redstone_ore")));
		make("glowstone_ore").setDropItem(Item.glowstoneDust, 2, 4).setLightEmittance(0.75F);
		make("lapis_ore").setDropItem(Item.dyePowder, 4, 3, 6);
	}
	
	private static NetherOre add(NetherOre ore) {
		BLOCKS.add(ore);
		return ore;
	}
	
	private static NetherOre make(String name) {
		NetherOre ore = new NetherOre(SimpleNetherOres.id(name));
		BLOCKS.add(ore);
		return ore;
	}
}
