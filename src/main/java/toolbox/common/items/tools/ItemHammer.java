package toolbox.common.items.tools;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Multimap;

import api.materials.Materials;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHammer extends ItemToolBase implements IHeadTool, IHaftTool, IHandleTool, IAdornedTool {

	public static final ImmutableSet<net.minecraft.block.material.Material> harvestableMaterials = ImmutableSet.of(net.minecraft.block.material.Material.IRON, net.minecraft.block.material.Material.ROCK, net.minecraft.block.material.Material.ICE, net.minecraft.block.material.Material.GLASS, net.minecraft.block.material.Material.ANVIL, net.minecraft.block.material.Material.PACKED_ICE, net.minecraft.block.material.Material.PISTON);
	
	public ItemHammer() {
		super("hammer");
		this.toolClass = "pickaxe";
		this.setMaxDamage(0);
	}

	public int getHarvestLevel(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getHarvestLevel() + IAdornedTool.getAdornmentMat(stack).getHarvestLevelMod();
	}

	public int getDurability(ItemStack stack) {
		return (int) (IHeadTool.getHeadMat(stack).getDurability() * IHaftTool.getHaftMat(stack).getDurabilityMod()
				* IHandleTool.getHandleMat(stack).getDurabilityMod()
				* IAdornedTool.getAdornmentMat(stack).getDurabilityMod());
	}

	public float getEfficiency(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getEfficiency() * IAdornedTool.getAdornmentMat(stack).getEfficiencyMod()
				* 0.25F;
	}

	public float getAttackDamage(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getAttackDamage() + IAdornedTool.getAdornmentMat(stack).getAttackDamageMod();
	}

	public int getEnchantability(ItemStack stack) {
		return (int) (IHeadTool.getHeadMat(stack).getEnchantability()
				* IHaftTool.getHaftMat(stack).getEnchantabilityMod()
				* IAdornedTool.getAdornmentMat(stack).getEnchantabilityMod());
	}

	public ItemStack getRepairItem(ItemStack stack) {
		return IHeadTool.getHeadMat(stack).getRepairItem();
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		for (String type : getToolClasses(stack)) {
			if (state.getBlock().isToolEffective(type, state) || state.getMaterial() == Material.ROCK
					|| state.getMaterial() == Material.IRON) {
				return getEfficiency(stack);
			}
		}
		return 1.0F;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return getEnchantability(stack);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		ItemStack mat = getRepairItem(toRepair);
		if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) {
			return true;
		}
		return super.getIsRepairable(toRepair, repair);
	}

	@Override
	public boolean canHarvestBlock(IBlockState state) {
		return harvestableMaterials.contains(state.getMaterial());
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return canHarvestBlock(state);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass,
			@javax.annotation.Nullable net.minecraft.entity.player.EntityPlayer player,
			@javax.annotation.Nullable IBlockState blockState) {
		int level = super.getHarvestLevel(stack, toolClass, player, blockState);
		if (level == -1 && toolClass.equals(this.toolClass)) {
			return getHarvestLevel(stack);
		} else {
			return level;
		}
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot,
			ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
					"Tool modifier", (double) 9.0F + this.getAttackDamage(stack), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) -3.5F, 0));
		}

		return multimap;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (double) getDamage(stack) / (double) getDurability(stack);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return getDurability(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		if (GuiScreen.isShiftKeyDown()) {
			if (!flagIn.isAdvanced() || !stack.hasTagCompound() || !stack.getTagCompound().hasKey(DAMAGE_TAG)) {
				tooltip.add(I18n.translateToLocal("desc.durability.name") + ": "
						+ (getDurability(stack) - getDamage(stack)) + " / " + getDurability(stack));
			}
			tooltip.add(I18n.translateToLocal("desc.efficiency.name") + ": " + getEfficiency(stack));
			tooltip.add(I18n.translateToLocal("desc.harvest_level.name") + ": " + getHarvestLevel(stack));
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		ItemStack stack1 = new ItemStack(this);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString(HEAD_TAG, Materials.randomHead().getName());
		tag.setString(HAFT_TAG, Materials.randomHaft().getName());
		tag.setString(HANDLE_TAG, Materials.randomHandle().getName());
		tag.setString(ADORNMENT_TAG, Materials.randomAdornment().getName());
		stack1.setTagCompound(tag);
		if (isInCreativeTab(tab)) {
			subItems.add(stack1);
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal(IHeadTool.getHeadMat(stack).getName() + ".name") + " "
				+ super.getItemStackDisplayName(stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
		return enchantment.type.canEnchantItem(stack.getItem()) || enchantment.type == EnumEnchantmentType.DIGGER;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		World world = player.getEntityWorld();
		
		if (!world.isRemote && player instanceof EntityPlayerMP) {
			
			RayTraceResult rt = this.rayTrace(world, player, false);
			if (rt.typeOfHit == RayTraceResult.Type.BLOCK) {
				EnumFacing side = rt.sideHit;
				
				List<BlockPos> extraBlocks = getExtraBlocks(world, rt, player);
				
				for (BlockPos pos2 : extraBlocks) {
					
					IBlockState state = world.getBlockState(pos2);
					
					if (!world.isBlockLoaded(pos2) || !player.canPlayerEdit(pos2, side, itemstack) || !(state.getBlock().canHarvestBlock(world, pos2, player))) {
						continue;
					}
					
					if (player.capabilities.isCreativeMode) {
						state.getBlock().onBlockHarvested(world, pos2, state, player);
						if (state.getBlock().removedByPlayer(state, world, pos2, player, false)) {
							state.getBlock().onBlockDestroyedByPlayer(world, pos2, state);
						}
					} else {
						int xp = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP) player).interactionManager.getGameType(), (EntityPlayerMP) player, pos2);
						
						state.getBlock().onBlockHarvested(world, pos2, state, player);
						this.onBlockDestroyed(itemstack, world, state, pos2, player);
						if (state.getBlock().removedByPlayer(state, world, pos2, player, true)) {
							state.getBlock().onBlockDestroyedByPlayer(world, pos2, state);
							state.getBlock().harvestBlock(world, player, pos2, state, world.getTileEntity(pos2), itemstack);
							state.getBlock().dropXpOnBlockBreak(world, pos2, xp);
						}
					}
					
					world.playEvent(2001, pos, Block.getStateId(state));
					((EntityPlayerMP)player).connection.sendPacket(new SPacketBlockChange(world, pos));
					
				}
				
			}
			
		}
		
		return false;
	}
	
	public RayTraceResult rayTraceBlocks(World world, EntityPlayer player) {
		return this.rayTrace(world, player, false);
	}
	
	public List<BlockPos> getExtraBlocks(World world, RayTraceResult rt, EntityPlayer player) {
		List<BlockPos> positions = new ArrayList<BlockPos>();
		BlockPos pos = rt.getBlockPos();
		
		if (player.isSneaking()) {
			return positions;
		}
		
		switch (rt.sideHit.getAxis()) {
		case Y:
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.NORTH), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.EAST), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.SOUTH), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.WEST), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.NORTH).offset(EnumFacing.EAST), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.EAST).offset(EnumFacing.SOUTH), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.SOUTH).offset(EnumFacing.WEST), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.WEST).offset(EnumFacing.NORTH), positions);
			break;
		case X:
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.NORTH), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.UP), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.SOUTH), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.DOWN), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.NORTH).offset(EnumFacing.UP), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.UP).offset(EnumFacing.SOUTH), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.SOUTH).offset(EnumFacing.DOWN), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.DOWN).offset(EnumFacing.NORTH), positions);
			break;
		case Z:
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.DOWN), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.EAST), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.UP), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.WEST), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.DOWN).offset(EnumFacing.EAST), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.EAST).offset(EnumFacing.UP), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.UP).offset(EnumFacing.WEST), positions);
			attemptAddExtraBlock(world, pos, pos.offset(EnumFacing.WEST).offset(EnumFacing.DOWN), positions);
			break;
		}
		
		return positions;
	}
	
	protected void attemptAddExtraBlock(World world, BlockPos pos1, BlockPos pos2, List<BlockPos> list) {
		
		if (world.getBlockState(pos2).getBlock() != world.getBlockState(pos1).getBlock() || world.isAirBlock(pos2)) {
			return;
		}
		
		if (!world.getBlockState(pos1).getBlock().isToolEffective(toolClass, world.getBlockState(pos1)) && !canHarvestBlock(world.getBlockState(pos1))) {
			return;
		}
		
		if (!world.getBlockState(pos2).getBlock().isToolEffective(toolClass, world.getBlockState(pos2)) && !canHarvestBlock(world.getBlockState(pos2))) {
			return;
		}
		
		list.add(pos2);
		
	}

}
