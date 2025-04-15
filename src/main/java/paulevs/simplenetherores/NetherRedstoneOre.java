package paulevs.simplenetherores;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;
import java.util.Random;

public class NetherRedstoneOre extends NetherOre {
	public NetherRedstoneOre(Identifier id) {
		super(id);
		setTicksRandomly(true);
		setDefaultState(getDefaultState().with(Properties.LIT, false));
		setDropItem(Item.redstoneDust, 1, 5);
		setLuminance(state -> state.get(Properties.LIT) ? 7 : 0);
	}
	
	@Override
	public void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(Properties.LIT);
	}
	
	@Override
	public void activate(Level level, int x, int y, int z, PlayerEntity player) {
		setActive(level, x, y, z);
		super.activate(level, x, y, z, player);
	}
	
	@Override
	public void onSteppedOn(Level level, int x, int y, int z, Entity entityBase) {
		setActive(level, x, y, z);
		super.onSteppedOn(level, x, y, z, entityBase);
	}
	
	@Override
	public boolean canUse(Level level, int x, int y, int z, PlayerEntity player) {
		setActive(level, x, y, z);
		return super.canUse(level, x, y, z, player);
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random rand) {
		BlockState state = level.getBlockState(x, y, z);
		if (!state.isOf(this) || !state.get(Properties.LIT)) return;
		level.setBlockState(x, y, z, state.with(Properties.LIT, false));
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void onRandomClientTick(Level level, int x, int y, int z, Random random) {
		BlockState state = level.getBlockState(x, y, z);
		if (!state.isOf(this) || !state.get(Properties.LIT)) return;
		spawnParticles(level, x, y, z, random);
	}
	
	private void setActive(Level level, int x, int y, int z) {
		level.setBlockState(x, y, z, getDefaultState().with(Properties.LIT, true));
		spawnParticles(level, x, y, z, level.random);
	}
	
	private void spawnParticles(Level level, int x, int y, int z, Random random) {
		double px = x;
		double py = y;
		double pz = z;
		switch (random.nextInt(3)) {
			case 0 -> {
				px += random.nextInt(2);
				py += random.nextFloat();
				pz += random.nextFloat();
			}
			case 1 -> {
				px += random.nextFloat();
				py += random.nextInt(2);
				pz += random.nextFloat();
			}
			case 2 -> {
				px += random.nextFloat();
				py += random.nextFloat();
				pz += random.nextInt(2);
			}
		}
		level.addParticle("reddust", px, py, pz, 0.0, 0.0, 0.0);
	}
}
