package paulevs.simplenetherores.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class NetherOre extends TemplateBlock {
	private int minDropCount = 1;
	private int dropDelta = 0;
	private Item dropItem;
	private int dropMeta;
	
	public NetherOre(Identifier id) {
		super(id, Material.STONE);
		setTranslationKey(id);
		setSounds(NETHERRACK.sounds);
		setHardness(NETHERRACK.getHardness() * 2.0F);
	}
	
	public NetherOre setDropItem(Item item) {
		dropItem = item;
		return this;
	}
	
	public NetherOre setDropItem(Item item, int minCount, int maxCount) {
		minDropCount = minCount;
		dropDelta = maxCount - minCount + 1;
		dropItem = item;
		return this;
	}
	
	public NetherOre setDropItem(Item item, int meta, int minCount, int maxCount) {
		minDropCount = minCount;
		dropDelta = maxCount - minCount + 1;
		dropItem = item;
		dropMeta = meta;
		return this;
	}
	
	@Override
	public List<ItemStack> getDropList(Level level, int x, int y, int z, BlockState state, int meta) {
		if (dropItem == null) dropItem = this.asItem();
		int count = dropDelta > 1 ? level.random.nextInt(dropDelta) + minDropCount : minDropCount;
		return List.of(new ItemStack(dropItem, count, dropMeta));
	}
}
