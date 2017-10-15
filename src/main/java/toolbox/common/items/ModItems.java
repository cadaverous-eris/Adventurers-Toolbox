package toolbox.common.items;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.Toolbox;
import toolbox.common.Config;
import toolbox.common.items.parts.ItemToolHandle;
import toolbox.common.items.parts.ItemToolHead;
import toolbox.common.items.tools.ItemAxe;
import toolbox.common.items.tools.ItemClimbingPick;
import toolbox.common.items.tools.ItemDagger;
import toolbox.common.items.tools.ItemHammer;
import toolbox.common.items.tools.ItemHandpick;
import toolbox.common.items.tools.ItemHoe;
import toolbox.common.items.tools.ItemMace;
import toolbox.common.items.tools.ItemPickaxe;
import toolbox.common.items.tools.ItemShovel;
import toolbox.common.items.tools.ItemSword;

@Mod.EventBusSubscriber(modid = Toolbox.MODID)
@ObjectHolder("toolbox")
public class ModItems {

	public static final ItemBase ROCK = null;

	public static final ItemToolHead PICKAXE_HEAD = null;
	public static final ItemToolHead AXE_HEAD = null;
	public static final ItemToolHead SHOVEL_HEAD = null;
	public static final ItemToolHead HOE_HEAD = null;
	public static final ItemToolHead HANDPICK_HEAD = null;
	public static final ItemToolHead HAMMER_HEAD = null;
	public static final ItemToolHead CLIMBING_PICK_HEAD = null;

	public static final ItemToolHead SWORD_BLADE = null;
	public static final ItemToolHead SWORD_CROSSGUARD = null;
	public static final ItemToolHead DAGGER_BLADE = null;
	public static final ItemToolHead MACE_HEAD = null;

	public static final ItemToolHandle HANDLE = null;

	public static final ItemPickaxe PICKAXE = null;
	public static final ItemAxe AXE = null;
	public static final ItemShovel SHOVEL = null;
	public static final ItemHoe HOE = null;
	public static final ItemHandpick HANDPICK = null;
	public static final ItemHammer HAMMER = null;
	public static final ItemClimbingPick CLIMBING_PICK = null;

	public static final ItemSword SWORD = null;
	public static final ItemDagger DAGGER = null;
	public static final ItemMace MACE = null;

	public static final ItemGuideBook GUIDE_BOOK = null;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register (new ItemRock());
		
		if(!Config.DISABLE_PICKAXE) event.getRegistry().register (new ItemToolHead("pickaxe_head"));
		if(!Config.DISABLE_AXE) event.getRegistry().register (new ItemToolHead("axe_head"));
		if(!Config.DISABLE_SHOVEL) event.getRegistry().register (new ItemToolHead("shovel_head"));
		if(!Config.DISABLE_HOE) event.getRegistry().register (new ItemToolHead("hoe_head"));
		if(!Config.DISABLE_HAND_PICK) event.getRegistry().register (new ItemToolHead("handpick_head"));
		if(!Config.DISABLE_HAMMER) event.getRegistry().register (new ItemToolHead("hammer_head"));
		if(!Config.DISABLE_CLIMBING_PICK) event.getRegistry().register (new ItemToolHead("climbing_pick_head"));

		if(!Config.DISABLE_SWORD) event.getRegistry().register (new ItemToolHead("sword_blade"));
		if(!Config.DISABLE_SWORD) event.getRegistry().register (new ItemToolHead("sword_crossguard"));
		if(!Config.DISABLE_DAGGER) event.getRegistry().register (new ItemToolHead("dagger_blade"));
		if(!Config.DISABLE_MACE) event.getRegistry().register (new ItemToolHead("mace_head"));
		
		event.getRegistry().register (new ItemToolHandle());

		if(!Config.DISABLE_PICKAXE) event.getRegistry().register (new ItemPickaxe());
		if(!Config.DISABLE_AXE) event.getRegistry().register (new ItemAxe());
		if(!Config.DISABLE_SHOVEL) event.getRegistry().register (new ItemShovel());
		if(!Config.DISABLE_HOE) event.getRegistry().register (new ItemHoe());
		if(!Config.DISABLE_HAND_PICK) event.getRegistry().register (new ItemHandpick());
		if(!Config.DISABLE_HAMMER) event.getRegistry().register (new ItemHammer());
		if(!Config.DISABLE_CLIMBING_PICK) event.getRegistry().register (new ItemClimbingPick());

		if(!Config.DISABLE_SWORD) event.getRegistry().register (new ItemSword());
		if(!Config.DISABLE_DAGGER) event.getRegistry().register (new ItemDagger());
		if(!Config.DISABLE_MACE) event.getRegistry().register (new ItemMace());

		event.getRegistry().register (new ItemGuideBook());
	}
	
	@SubscribeEvent
	public static void setupModels(ModelRegistryEvent event) {

		ROCK.initModel();

		if(!Config.DISABLE_PICKAXE) PICKAXE_HEAD.initModel();
		if(!Config.DISABLE_AXE) AXE_HEAD.initModel();
		if(!Config.DISABLE_SHOVEL) SHOVEL_HEAD.initModel();
		if(!Config.DISABLE_HOE) HOE_HEAD.initModel();
		if(!Config.DISABLE_HAND_PICK) HANDPICK_HEAD.initModel();
		if(!Config.DISABLE_HAMMER) HAMMER_HEAD.initModel();
		if(!Config.DISABLE_CLIMBING_PICK) CLIMBING_PICK_HEAD.initModel();

		if(!Config.DISABLE_SWORD) SWORD_BLADE.initModel();
		if(!Config.DISABLE_SWORD) SWORD_CROSSGUARD.initModel();
		if(!Config.DISABLE_DAGGER) DAGGER_BLADE.initModel();
		if(!Config.DISABLE_MACE) MACE_HEAD.initModel();

		HANDLE.initModel();

		if(!Config.DISABLE_PICKAXE) PICKAXE.initModel();
		if(!Config.DISABLE_AXE) AXE.initModel();
		if(!Config.DISABLE_SHOVEL) SHOVEL.initModel();
		if(!Config.DISABLE_HOE) HOE.initModel();
		if(!Config.DISABLE_HAND_PICK) HANDPICK.initModel();
		if(!Config.DISABLE_HAMMER) HAMMER.initModel();
		if(!Config.DISABLE_HAND_PICK) CLIMBING_PICK.initModel();

		if(!Config.DISABLE_SWORD) SWORD.initModel();
		if(!Config.DISABLE_DAGGER) DAGGER.initModel();
		if(!Config.DISABLE_MACE) MACE.initModel();

		GUIDE_BOOK.initModel();

	}

}
