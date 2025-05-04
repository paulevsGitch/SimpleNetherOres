package paulevs.simplenetherores.structure;

import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;

public class DeepNetherOreStructure extends NetherOreStructure {
	private final BlockState replaceState;
	
	public DeepNetherOreStructure(Block block, int radius) {
		super(block, radius);
		block = BlockRegistry.INSTANCE.get(Identifier.of("bnb:hardened_netherrack"));
		assert block != null;
		replaceState = block.getDefaultState();
	}
	
	@Override
	protected boolean canReplace(BlockState state) {
		return state == replaceState;
	}
}
