package paulevs.simplenetherores.listener;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;
import paulevs.simplenetherores.SimpleNetherOres;

public class CreativeTabListener {
	@EventListener
	public void registerTab(TabRegistryEvent event) {
		SimpleTab blocksTab = new SimpleTab(SimpleNetherOres.id("tab"), new ItemStack(CommonListener.glowstoneOre));
		CommonListener.BLOCKS.forEach(block -> blocksTab.addItem(new ItemStack(block)));
		event.register(blocksTab);
	}
}
