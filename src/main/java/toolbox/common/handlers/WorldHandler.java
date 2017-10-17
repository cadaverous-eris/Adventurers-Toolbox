package toolbox.common.handlers;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toolbox.common.Config;
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
	
	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			boolean giveBook = false;
			
			NBTTagCompound playerData = player.getEntityData();
			if (playerData.hasKey(EntityPlayer.PERSISTED_NBT_TAG, 10)) {
				NBTTagCompound persistedTag = playerData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
				if (!persistedTag.hasKey("received_at_book")) {
					giveBook = Config.SPAWN_WITH_BOOK;
					persistedTag.setBoolean("received_at_book", giveBook);
				}
			}
			
			if (giveBook) {
				ItemStack book = new ItemStack(ModItems.guide_book);
				if (!player.inventory.addItemStackToInventory(book)) {
					player.dropItem(book, false);
				}
			}
		}
	}

}
