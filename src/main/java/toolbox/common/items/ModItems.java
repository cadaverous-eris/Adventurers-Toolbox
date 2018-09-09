package toolbox.common.items;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import toolbox.Toolbox;
import toolbox.common.Config;
import toolbox.common.items.parts.ItemToolHandle;
import toolbox.common.items.parts.ItemToolHead;
import toolbox.common.items.tools.ItemATAxe;
import toolbox.common.items.tools.ItemATDagger;
import toolbox.common.items.tools.ItemATHammer;
import toolbox.common.items.tools.ItemATHandpick;
import toolbox.common.items.tools.ItemATHoe;
import toolbox.common.items.tools.ItemATMace;
import toolbox.common.items.tools.ItemATPickaxe;
import toolbox.common.items.tools.ItemATShovel;
import toolbox.common.items.tools.ItemATSword;
import toolbox.common.materials.ModMaterials;
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

	public static final ItemATPickaxe pickaxe = null;
	public static final ItemATAxe axe = null;
	public static final ItemATShovel shovel = null;
	public static final ItemATHoe hoe = null;
	public static final ItemATHandpick handpick = null;
	public static final ItemATHammer hammer = null;

	public static final ItemATSword sword = null;
	public static final ItemATDagger dagger = null;
	public static final ItemATMace mace = null;

	public static final ItemSchematic schematic = null;
	
	public static final ItemGuideBook guide_book = null;
	
	public static final ItemCast cast = null;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemRock());
		
		if (!Config.DISABLED_TOOLS.contains("pickaxe")) {
			event.getRegistry().register(new ItemToolHead("pickaxe_head"));
			ItemSchematic.subtypes.add("pickaxe_head");
		}
		if (!Config.DISABLED_TOOLS.contains("axe")) {
			event.getRegistry().register(new ItemToolHead("axe_head"));
			ItemSchematic.subtypes.add("axe_head");
		}
		if (!Config.DISABLED_TOOLS.contains("shovel")) {
			event.getRegistry().register(new ItemToolHead("shovel_head"));
			ItemSchematic.subtypes.add("shovel_head");
		}
		if (!Config.DISABLED_TOOLS.contains("hoe")) {
			event.getRegistry().register(new ItemToolHead("hoe_head"));
			ItemSchematic.subtypes.add("hoe_head");
		}
		if (!Config.DISABLED_TOOLS.contains("handpick")) {
			event.getRegistry().register(new ItemToolHead("handpick_head"));
			ItemSchematic.subtypes.add("handpick_head");
		}
		if (!Config.DISABLED_TOOLS.contains("hammer")) {
			event.getRegistry().register(new ItemToolHead("hammer_head"));
			ItemSchematic.subtypes.add("hammer_head");
		}

		if (!Config.DISABLED_TOOLS.contains("sword")) {
			event.getRegistry().register(new ItemToolHead("sword_blade"));
			ItemSchematic.subtypes.add("sword_blade");
			event.getRegistry().register(new ItemToolHead("sword_crossguard"));
			ItemSchematic.subtypes.add("sword_crossguard");
		}
		if (!Config.DISABLED_TOOLS.contains("dagger")) {
			event.getRegistry().register(new ItemToolHead("dagger_blade"));
			ItemSchematic.subtypes.add("dagger_blade");
		}
		if (!Config.DISABLED_TOOLS.contains("mace")) {
			event.getRegistry().register(new ItemToolHead("mace_head"));
			ItemSchematic.subtypes.add("mace_head");
		}

		if (Config.ENABLE_SCHEMATICS) event.getRegistry().register(new ItemSchematic());

		event.getRegistry().register(new ItemToolHandle());

		event.getRegistry().register(new ItemGuideBook());
		
		if (Config.ENABLE_TINKERS_COMPAT) event.getRegistry().register(new ItemCast());
		
		if (!Config.DISABLED_TOOLS.contains("pickaxe")) event.getRegistry().register(new ItemATPickaxe());
		if (!Config.DISABLED_TOOLS.contains("axe")) event.getRegistry().register(new ItemATAxe());
		if (!Config.DISABLED_TOOLS.contains("shovel")) event.getRegistry().register(new ItemATShovel());
		if (!Config.DISABLED_TOOLS.contains("hoe")) event.getRegistry().register(new ItemATHoe());
		if (!Config.DISABLED_TOOLS.contains("handpick")) event.getRegistry().register(new ItemATHandpick());
		if (!Config.DISABLED_TOOLS.contains("hammer")) event.getRegistry().register(new ItemATHammer());

		if (!Config.DISABLED_TOOLS.contains("sword")) event.getRegistry().register(new ItemATSword());
		if (!Config.DISABLED_TOOLS.contains("dagger")) event.getRegistry().register(new ItemATDagger());
		if (!Config.DISABLED_TOOLS.contains("mace")) event.getRegistry().register(new ItemATMace());
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

		if (Config.ENABLE_SCHEMATICS) schematic.initModel();

		guide_book.initModel();
		
		if(Config.ENABLE_TINKERS_COMPAT) cast.initModel();
	}

}
