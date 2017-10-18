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
import toolbox.common.items.ItemSchematic;
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
			boolean giveSchematics = false;

			NBTTagCompound playerData = player.getEntityData();
			NBTTagCompound persistedTag;
			if (!playerData.hasKey(EntityPlayer.PERSISTED_NBT_TAG, 10)) {
				playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
			}
			persistedTag = playerData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			if (!persistedTag.hasKey("received_at_book")) {
				giveBook = Config.SPAWN_WITH_BOOK;
				persistedTag.setBoolean("received_at_book", giveBook);
			}
			if (!persistedTag.hasKey("received_at_schematics")) {
				giveSchematics = Config.ENABLE_SCHEMATICS && !Config.SPAWN_SCHEMATICS.isEmpty();
				persistedTag.setBoolean("received_at_schematics", giveSchematics);
			}

			if (giveBook) {
				ItemStack book = new ItemStack(ModItems.guide_book);
				if (!player.inventory.addItemStackToInventory(book)) {
					player.dropItem(book, false);
				}
			}
			if (giveSchematics && ModItems.schematic != null) {
				for (String type : Config.SPAWN_SCHEMATICS) {
					if (ItemSchematic.subtypes.contains(type)) {
						ItemStack schematic = new ItemStack(ModItems.schematic);
						NBTTagCompound typeTag = new NBTTagCompound();
						typeTag.setString(ItemSchematic.type_tag, type);
						schematic.setTagCompound(typeTag);
						if (!player.inventory.addItemStackToInventory(schematic)) {
							player.dropItem(schematic, false);
						}
					}
				}
			}
		}
	}

}
