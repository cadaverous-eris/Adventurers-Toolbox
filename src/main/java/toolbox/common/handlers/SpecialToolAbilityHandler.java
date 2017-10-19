package toolbox.common.handlers;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.materials.ModMaterials;

public class SpecialToolAbilityHandler {

	private Random rand = new Random();

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEndRodToolHarvestEvent(BlockEvent.HarvestDropsEvent event) {
		if (event.getHarvester() == null) {
			return;
		}

		ItemStack stack = event.getHarvester().getHeldItemMainhand();

		boolean flag = false;

		if (stack.getItem() instanceof IHaftTool) {
			if (IHaftTool.getHaftMat(stack) == ModMaterials.HAFT_END_ROD) {
				flag = true;
			}
		}

		if (flag) {
			if (!event.getWorld().isRemote) {
				BlockPos pos = event.getPos();
				for (ItemStack itemstack : event.getDrops()) {
					if (rand.nextFloat() < event.getDropChance()) {
						EntityItem entity = new EntityItem(event.getWorld(), pos.getX() + 0.5, pos.getY() + 0.25,
								pos.getZ() + 0.5, itemstack);
						entity.setNoGravity(true);
						entity.motionX = 0;
						entity.motionY = 0;
						entity.motionZ = 0;
						entity.velocityChanged = true;
						event.getWorld().spawnEntity(entity);
					}
				}
			}
			event.getDrops().clear();
		}
	}

	@SubscribeEvent
	public void onBlazeRodToolRightClickEvent(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();

		if (!event.getEntityPlayer().isSneaking()) {
			return;
		}

		boolean flag = false;

		if (stack.getItem() instanceof IHaftTool) {
			if (IHaftTool.getHaftMat(stack) == ModMaterials.HAFT_BLAZE_ROD) {
				flag = true;
			}
		}

		if (flag && event.getEntityPlayer().canPlayerEdit(event.getPos(), event.getFace(), stack)) {
			BlockPos pos = event.getPos().offset(event.getFace());

			if (event.getWorld().isAirBlock(pos)) {
				if (!event.getWorld().isRemote) {
					event.getWorld().setBlockState(pos, Blocks.FIRE.getDefaultState(), 11);
				}
				event.getWorld().playSound(event.getEntityPlayer(), pos, SoundEvents.ITEM_FLINTANDSTEEL_USE,
						SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
				event.getEntityPlayer().swingArm(event.getHand());
			}
		}
	}

	@SubscribeEvent
	public void onPrismarineAdornedToolMine(PlayerEvent.BreakSpeed event) {

		if (event.getEntityPlayer() == null) {
			return;
		}

		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();

		if (stack.getItem() instanceof IAdornedTool
				&& IAdornedTool.getAdornmentMat(stack) == ModMaterials.ADORNMENT_PRISMARINE && player.isInWater()) {

			event.setNewSpeed(event.getOriginalSpeed() * 2F);

		}

	}

}
