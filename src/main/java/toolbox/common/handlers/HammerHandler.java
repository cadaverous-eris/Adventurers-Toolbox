package toolbox.common.handlers;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.common.items.ModItems;

public class HammerHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void drawHammerBlockBoundsEvent(DrawBlockHighlightEvent event) {

		if (event.getPlayer() == null) {
			return;
		}

		if (event.getSubID() == 0 && event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK) {

			ItemStack stack = event.getPlayer().getHeldItemMainhand();

			if (stack.getItem() == ModItems.hammer) {
				
				World world = event.getPlayer().getEntityWorld();
				List<BlockPos> positions = ModItems.hammer.getExtraBlocks(world, event.getTarget(), event.getPlayer(), stack);
				
				for (BlockPos pos : positions) {
					event.getContext().drawSelectionBox(event.getPlayer(), new RayTraceResult(new Vec3d(0, 0, 0), null, pos), 0, event.getPartialTicks());
				}

			}

		}

	}
	
	@SubscribeEvent
	public void onPlayerClickBlock(PlayerInteractEvent.RightClickBlock event) {

		if (event.getEntityPlayer() == null || !event.getEntityPlayer().isSneaking()) {
			return;
		}
		
		if (event.getItemStack().getItem() == ModItems.hammer && !event.getEntityPlayer().getCooldownTracker().hasCooldown(ModItems.hammer)) {

			if (!event.getWorld().isRemote) {
				BlockPos pos = event.getPos().offset(event.getFace());
				event.getWorld().createExplosion(event.getEntityPlayer(), pos.getX() + 0.5, pos.getY() + 0.5,
						pos.getZ() + 0.5, 3, false);
			}

			event.getEntityPlayer().swingArm(event.getHand());
			event.getEntityPlayer().setActiveHand(event.getHand());
			event.getEntityPlayer().getCooldownTracker().setCooldown(ModItems.hammer, 200);
			ModItems.hammer.setDamage(event.getItemStack(), ModItems.hammer.getDamage(event.getItemStack()) - 10);
			
			event.setCancellationResult(EnumActionResult.SUCCESS);
			event.setResult(Event.Result.ALLOW);
			event.setCanceled(true);
		}

	}

}
