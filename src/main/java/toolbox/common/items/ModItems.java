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
		event.getRegistry().register(new ItemRock());
		
		if(!Config.DISABLED_TOOLS.contains("pickaxe")) event.getRegistry().register(new ItemToolHead("pickaxe_head"));
		if(!Config.DISABLED_TOOLS.contains("axe")) event.getRegistry().register(new ItemToolHead("axe_head"));
		if(!Config.DISABLED_TOOLS.contains("shovel")) event.getRegistry().register(new ItemToolHead("shovel_head"));
		if(!Config.DISABLED_TOOLS.contains("hoe")) event.getRegistry().register(new ItemToolHead("hoe_head"));
		if(!Config.DISABLED_TOOLS.contains("handpick")) event.getRegistry().register(new ItemToolHead("handpick_head"));
		if(!Config.DISABLED_TOOLS.contains("hammer")) event.getRegistry().register(new ItemToolHead("hammer_head"));
		if(!Config.DISABLED_TOOLS.contains("climbing_pick")) event.getRegistry().register(new ItemToolHead("climbing_pick_head"));

		if(!Config.DISABLED_TOOLS.contains("sword")) {
			event.getRegistry().register(new ItemToolHead("sword_blade"));
			event.getRegistry().register(new ItemToolHead("sword_crossguard"));
		}
		if(!Config.DISABLED_TOOLS.contains("dagger")) event.getRegistry().register(new ItemToolHead("dagger_blade"));
		if(!Config.DISABLED_TOOLS.contains("mace")) event.getRegistry().register(new ItemToolHead("mace_head"));
		
		event.getRegistry().register(new ItemToolHandle());

		event.getRegistry().register(new ItemGuideBook());
		
		if(Config.ENABLE_TINKERS_COMPAT) event.getRegistry().register(new ItemCast());
		
		if(!Config.DISABLED_TOOLS.contains("pickaxe")) event.getRegistry().register(new ItemPickaxe());
		if(!Config.DISABLED_TOOLS.contains("axe")) event.getRegistry().register(new ItemAxe());
		if(!Config.DISABLED_TOOLS.contains("shovel")) event.getRegistry().register(new ItemShovel());
		if(!Config.DISABLED_TOOLS.contains("hoe")) event.getRegistry().register(new ItemHoe());
		if(!Config.DISABLED_TOOLS.contains("handpick")) event.getRegistry().register(new ItemHandpick());
		if(!Config.DISABLED_TOOLS.contains("hammer")) event.getRegistry().register(new ItemHammer());
		if(!Config.DISABLED_TOOLS.contains("climbing_pick")) event.getRegistry().register(new ItemClimbingPick());

		if(!Config.DISABLED_TOOLS.contains("sword")) event.getRegistry().register(new ItemSword());
		if(!Config.DISABLED_TOOLS.contains("dagger")) event.getRegistry().register(new ItemDagger());
		if(!Config.DISABLED_TOOLS.contains("mace")) event.getRegistry().register(new ItemMace());
	}
	
	@SubscribeEvent
	public static void setupModels(ModelRegistryEvent event) {

		rock.initModel();

		if(!Config.DISABLED_TOOLS.contains("pickaxe")) {
			pickaxe_head.initModel();
			pickaxe.initModel();
		}
		if(!Config.DISABLED_TOOLS.contains("axe")) {
			axe_head.initModel();
			axe.initModel();
		}
		if(!Config.DISABLED_TOOLS.contains("shovel")) {
			shovel_head.initModel();
			shovel.initModel();
		}
		if(!Config.DISABLED_TOOLS.contains("hoe")) {
			hoe_head.initModel();
			hoe.initModel();
		}
		if(!Config.DISABLED_TOOLS.contains("handpick")) {
			handpick_head.initModel();
			handpick.initModel();
		}
		if(!Config.DISABLED_TOOLS.contains("hammer")) {
			hammer_head.initModel();
			hammer.initModel();
		}
		if(!Config.DISABLED_TOOLS.contains("climbing_pick")) {
			climbing_pick_head.initModel();
			climbing_pick.initModel();
		}

		if(!Config.DISABLED_TOOLS.contains("sword")) {
			sword_blade.initModel();
			sword_crossguard.initModel();
			sword.initModel();
		}
		if(!Config.DISABLED_TOOLS.contains("dagger")) {
			dagger_blade.initModel();
			dagger.initModel();
		}
		if(!Config.DISABLED_TOOLS.contains("mace")) {
			mace_head.initModel();
			mace.initModel();
		}

		handle.initModel();

		guide_book.initModel();
		
		if(Config.ENABLE_TINKERS_COMPAT) cast.initModel();
	}

}
