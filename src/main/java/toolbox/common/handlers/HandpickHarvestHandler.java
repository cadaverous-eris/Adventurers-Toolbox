package toolbox.common.handlers;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toolbox.common.items.ModItems;

public class HandpickHarvestHandler {

	private Random rand = new Random();

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onHandpickHarvest(BlockEvent.HarvestDropsEvent event) {
		if (event.getHarvester() == null) {
			return;
		}
		if (!event.getHarvester().getHeldItemMainhand().isEmpty() && event.getHarvester().getHeldItemMainhand().getItem() == ModItems.handpick) {
			if (!event.getDrops().isEmpty()) {
				event.getDrops().clear();
				if (!event.getWorld().isRemote) {
					event.getDrops().addAll(event.getState().getBlock().getDrops(event.getWorld(), event.getPos(),
							event.getState(), event.getFortuneLevel() + 1));
				}
			}
		}
	}

}
