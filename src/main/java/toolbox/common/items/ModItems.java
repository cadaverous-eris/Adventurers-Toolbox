package toolbox.common.items;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
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
import toolbox.compat.tconstruct.ItemCast;

@Mod.EventBusSubscriber(modid = Toolbox.MODID)
@ObjectHolder("toolbox")
public class ModItems {

	public static final ItemBase rock = null;

	public static final ItemToolHead pickaxe_head = null;
	public static final ItemToolHead axe_head = null;
	public static final ItemToolHead shovel_head = null;
	public static final ItemToolHead hoe_head = null;
	public static final ItemToolHead handpick_head = null;
	public static final ItemToolHead hammer_head = null;
	public static final ItemToolHead climbing_pick_head = null;

	public static final ItemToolHead sword_blade = null;
	public static final ItemToolHead sword_crossguard = null;
	public static final ItemToolHead dagger_blade = null;
	public static final ItemToolHead mace_head = null;

	public static final ItemToolHandle handle = null;

	public static final ItemPickaxe pickaxe = null;
	public static final ItemAxe axe = null;
	public static final ItemShovel shovel = null;
	public static final ItemHoe hoe = null;
	public static final ItemHandpick handpick = null;
	public static final ItemHammer hammer = null;
	public static final ItemClimbingPick climbing_pick = null;

	public static final ItemSword sword = null;
	public static final ItemDagger dagger = null;
	public static final ItemMace mace = null;

	public static final ItemGuideBook guide_book = null;
	
	public static final ItemCast cast = null;

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
		
		event.getRegistry().register (new ItemCast());
	}
	
	@SubscribeEvent
	public static void setupModels(ModelRegistryEvent event) {

		rock.initModel();

		if(!Config.DISABLE_PICKAXE) pickaxe_head.initModel();
		if(!Config.DISABLE_AXE) axe_head.initModel();
		if(!Config.DISABLE_SHOVEL) shovel_head.initModel();
		if(!Config.DISABLE_HOE) hoe_head.initModel();
		if(!Config.DISABLE_HAND_PICK) handpick_head.initModel();
		if(!Config.DISABLE_HAMMER) hammer_head.initModel();
		if(!Config.DISABLE_CLIMBING_PICK) climbing_pick_head.initModel();

		if(!Config.DISABLE_SWORD) sword_blade.initModel();
		if(!Config.DISABLE_SWORD) sword_crossguard.initModel();
		if(!Config.DISABLE_DAGGER) dagger_blade.initModel();
		if(!Config.DISABLE_MACE) mace_head.initModel();

		handle.initModel();

		if(!Config.DISABLE_PICKAXE) pickaxe.initModel();
		if(!Config.DISABLE_AXE) axe.initModel();
		if(!Config.DISABLE_SHOVEL) shovel.initModel();
		if(!Config.DISABLE_HOE) hoe.initModel();
		if(!Config.DISABLE_HAND_PICK) handpick.initModel();
		if(!Config.DISABLE_HAMMER) hammer.initModel();
		if(!Config.DISABLE_HAND_PICK) climbing_pick.initModel();

		if(!Config.DISABLE_SWORD) sword.initModel();
		if(!Config.DISABLE_DAGGER) dagger.initModel();
		if(!Config.DISABLE_MACE) mace.initModel();

		guide_book.initModel();

		cast.initModel();
	}

}
