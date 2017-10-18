package toolbox.common.handlers;

import java.util.Collection;
import java.util.Random;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetNBT;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import toolbox.Toolbox;
import toolbox.common.Config;
import toolbox.common.items.ItemSchematic;
import toolbox.common.items.ModItems;

@Mod.EventBusSubscriber(modid = Toolbox.MODID)
public class LootTableHandler {
	
	@SubscribeEvent
	public static void lootTableLoad(LootTableLoadEvent event) {
		if (Config.ENABLE_SCHEMATICS) {
			if (!Config.DUNGEON_SCHEMATICS.isEmpty() && event.getName().toString().equals("minecraft:chests/simple_dungeon")) {
				LootEntry[] entries = new LootEntry[Config.DUNGEON_SCHEMATICS.size()];
				for (int i = 0; i < entries.length; i++) {
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString(ItemSchematic.type_tag, Config.DUNGEON_SCHEMATICS.get(i));
					entries[i] = new LootEntryItem(ModItems.schematic, 1, 0, new LootFunction[] { new SetNBT(new LootCondition[0], nbt) }, new LootCondition[0], Config.DUNGEON_SCHEMATICS.get(i));
				}
				LootPool pool = new LootPool(entries, new LootCondition[0], new RandomValueRange(0, Math.min(3, entries.length)), new RandomValueRange(0), "schematics");
				event.getTable().addPool(pool);
			}
			if (!Config.BLACKSMITH_SCHEMATICS.isEmpty() && event.getName().toString().equals("minecraft:chests/village_blacksmith")) {
				LootEntry[] entries = new LootEntry[Config.BLACKSMITH_SCHEMATICS.size()];
				for (int i = 0; i < entries.length; i++) {
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString(ItemSchematic.type_tag, Config.BLACKSMITH_SCHEMATICS.get(i));
					entries[i] = new LootEntryItem(ModItems.schematic, 1, 0, new LootFunction[] { new SetNBT(new LootCondition[0], nbt) }, new LootCondition[0], Config.BLACKSMITH_SCHEMATICS.get(i));
				}
				LootPool pool = new LootPool(entries, new LootCondition[0], new RandomValueRange(0, Math.min(3, entries.length)), new RandomValueRange(0), "schematics");
				event.getTable().addPool(pool);
			}
			if (!Config.BONUS_CHEST_SCHEMATICS.isEmpty() && event.getName().toString().equals("minecraft:chests/spawn_bonus_chest")) {
				LootEntry[] entries = new LootEntry[Config.BONUS_CHEST_SCHEMATICS.size()];
				for (int i = 0; i < entries.length; i++) {
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setString(ItemSchematic.type_tag, Config.BONUS_CHEST_SCHEMATICS.get(i));
					entries[i] = new LootEntryItem(ModItems.schematic, 1, 0, new LootFunction[] { new SetNBT(new LootCondition[0], nbt) }, new LootCondition[0], Config.BONUS_CHEST_SCHEMATICS.get(i));
				}
				LootPool pool = new LootPool(entries, new LootCondition[0], new RandomValueRange(0, Math.min(3, entries.length)), new RandomValueRange(0), "schematics");
				event.getTable().addPool(pool);
			}
		}
	}
	
}
