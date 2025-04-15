package paulevs.simplenetherores;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemStack;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class CreativeTabListener {
	@EventListener
	public void registerTab(TabRegistryEvent event) {
		SimpleTab blocksTab = new SimpleTab(SimpleNetherOres.id("tab"), new ItemStack(CommonListener.BLOCKS.get(5)));
		CommonListener.BLOCKS.forEach(block -> blocksTab.addItem(new ItemStack(block)));
		event.register(blocksTab);
	}
}
