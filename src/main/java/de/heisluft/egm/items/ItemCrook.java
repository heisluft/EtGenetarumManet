package de.heisluft.egm.items;

import de.heisluft.egm.EtGeneratumManet;
import de.heisluft.egm.util.ClosableHashSet;
import de.heisluft.egm.util.CrookUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class ItemCrook extends ItemTool {
	
	public enum Variant {
		WOOD("wooden_crook", ToolMaterial.WOOD), BONE("bone_crook", ToolMaterial.IRON);
		
		private final String name;
		private final ToolMaterial material;
		
		Variant(String name, ToolMaterial material) {
			this.material = material;
			this.name = name;
		}
		
		public ToolMaterial getMaterial() {
			return material;
		}
		
		public String getName() {
			return name;
		}
	}

	private static final ClosableHashSet<Block> EFFECTIVE_BLOCKS = ClosableHashSet
			.of(new Block[] { Blocks.LEAVES, Blocks.LEAVES2 }).close();

	public static void updateMinableBlocks() {
		EFFECTIVE_BLOCKS.disposeElementsAndReopen();
		for (final ResourceLocation l : Block.REGISTRY.getKeys())
			if (Block.REGISTRY.getObject(l) instanceof BlockLeaves) EFFECTIVE_BLOCKS.add(Block.REGISTRY.getObject(l));
		EFFECTIVE_BLOCKS.close();
		EtGeneratumManet.MAIN_LOG.info("Loading Compatibility for " + EFFECTIVE_BLOCKS.size() + " types of Leaves!");
	}

	public ItemCrook(Variant var) {
		super(var.getMaterial(), EFFECTIVE_BLOCKS);
		setUnlocalizedName(var.getName());
		setRegistryName(var.getName());
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState block, ItemStack is) {
		return block.getBlock() instanceof BlockLeaves;
	}
	
	// @Override
	// public float getStrVsBlock(ItemStack stack, IBlockState state) {
	// return state.getBlock() instanceof BlockLeaves ? efficiency + 0.5f : 0.1f;
	// }
	
	@Override
	public boolean onBlockStartBreak(ItemStack item, BlockPos pos, EntityPlayer player) {
		CrookUtils.doCrooking(item, pos, player);
		return false;
	}
}
