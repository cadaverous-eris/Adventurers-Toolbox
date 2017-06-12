package toolbox.common.handlers;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import toolbox.Toolbox;
import toolbox.common.items.ModItems;
import toolbox.common.items.parts.ItemToolHead;

public class CraftingEventHandler {

	@SubscribeEvent
	public void onToolHeadCraft(PlayerEvent.ItemCraftedEvent event) {
		if (event.crafting.getItem() instanceof ItemToolHead) {
			if (!event.player.hasAchievement(Toolbox.HEAD_CRAFTED)) {
				event.player.addStat(Toolbox.HEAD_CRAFTED, 1);
				if (!event.player.getEntityWorld().isRemote) {
					ItemStack bookstack = new ItemStack(ModItems.GUIDE_BOOK);
					if (!event.player.inventory.addItemStackToInventory(bookstack)) {
						event.player.dropItem(bookstack, false);
					}
				}
			}
		} else if (event.crafting.getItem() == ModItems.PICKAXE) {
			if (!event.player.hasAchievement(AchievementList.BUILD_PICKAXE)) {
				event.player.addStat(AchievementList.BUILD_PICKAXE);
			} else if (ModItems.PICKAXE.getHarvestLevel(event.crafting) > 0
					&& !event.player.hasAchievement(AchievementList.BUILD_BETTER_PICKAXE)) {
				event.player.addStat(AchievementList.BUILD_BETTER_PICKAXE);
			}
		} else if (event.crafting.getItem() == ModItems.HOE) {
			if (!event.player.hasAchievement(AchievementList.BUILD_HOE)) {
				event.player.addStat(AchievementList.BUILD_HOE);
			}
		} else if (event.crafting.getItem() == ModItems.SWORD) {
			if (!event.player.hasAchievement(AchievementList.BUILD_SWORD)) {
				event.player.addStat(AchievementList.BUILD_SWORD);
			}
		}
	}

}
