package toolbox.common.handlers;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toolbox.common.items.ModItems;

public class WorldHandler {
	
	Random rand = new Random();

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if (event.getWorld().getMinecraftServer() != null) {
			event.getWorld()
					.addEventListener(new WorldListener(event.getWorld(), event.getWorld().getMinecraftServer()));
		}
	}

}
