package toolbox.common.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

public class ModItems {

	public static ItemBase ROCK;

	public static ItemToolHead PICKAXE_HEAD;
	public static ItemToolHead AXE_HEAD;
	public static ItemToolHead SHOVEL_HEAD;
	public static ItemToolHead HOE_HEAD;
	public static ItemToolHead HANDPICK_HEAD;
	public static ItemToolHead HAMMER_HEAD;
	public static ItemToolHead CLIMBING_PICK_HEAD;

	public static ItemToolHead SWORD_BLADE;
	public static ItemToolHead CROSSGUARD;
	public static ItemToolHead DAGGER_BLADE;
	public static ItemToolHead MACE_HEAD;

	public static ItemToolHandle HANDLE;

	public static ItemPickaxe PICKAXE;
	public static ItemAxe AXE;
	public static ItemShovel SHOVEL;
	public static ItemHoe HOE;
	public static ItemHandpick HANDPICK;
	public static ItemHammer HAMMER;
	public static ItemClimbingPick CLIMBING_PICK;

	public static ItemSword SWORD;
	public static ItemDagger DAGGER;
	public static ItemMace MACE;

	public static ItemGuideBook GUIDE_BOOK;

	public static void init() {

		ROCK = new ItemRock();

		if(!Config.DISABLE_PICKAXE) PICKAXE_HEAD = new ItemToolHead("pickaxe_head");
		if(!Config.DISABLE_AXE) AXE_HEAD = new ItemToolHead("axe_head");
		if(!Config.DISABLE_SHOVEL) SHOVEL_HEAD = new ItemToolHead("shovel_head");
		if(!Config.DISABLE_HOE) HOE_HEAD = new ItemToolHead("hoe_head");
		if(!Config.DISABLE_HAND_PICK) HANDPICK_HEAD = new ItemToolHead("handpick_head");
		if(!Config.DISABLE_HAMMER) HAMMER_HEAD = new ItemToolHead("hammer_head");
		if(!Config.DISABLE_CLIMBING_PICK) CLIMBING_PICK_HEAD = new ItemToolHead("climbing_pick_head");

		if(!Config.DISABLE_SWORD) SWORD_BLADE = new ItemToolHead("sword_blade");
		if(!Config.DISABLE_SWORD) CROSSGUARD = new ItemToolHead("sword_crossguard");
		if(!Config.DISABLE_DAGGER) DAGGER_BLADE = new ItemToolHead("dagger_blade");
		if(!Config.DISABLE_MACE) MACE_HEAD = new ItemToolHead("mace_head");

		HANDLE = new ItemToolHandle();

		if(!Config.DISABLE_PICKAXE) PICKAXE = new ItemPickaxe();
		if(!Config.DISABLE_AXE) AXE = new ItemAxe();
		if(!Config.DISABLE_SHOVEL) SHOVEL = new ItemShovel();
		if(!Config.DISABLE_HOE) HOE = new ItemHoe();
		if(!Config.DISABLE_HAND_PICK) HANDPICK = new ItemHandpick();
		if(!Config.DISABLE_HAMMER) HAMMER = new ItemHammer();
		if(!Config.DISABLE_CLIMBING_PICK) CLIMBING_PICK = new ItemClimbingPick();

		if(!Config.DISABLE_SWORD) SWORD = new ItemSword();
		if(!Config.DISABLE_DAGGER) DAGGER = new ItemDagger();
		if(!Config.DISABLE_MACE) MACE = new ItemMace();

		GUIDE_BOOK = new ItemGuideBook();

	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {

		ROCK.initModel();

		if(!Config.DISABLE_PICKAXE) PICKAXE_HEAD.initModel();
		if(!Config.DISABLE_AXE) AXE_HEAD.initModel();
		if(!Config.DISABLE_SHOVEL) SHOVEL_HEAD.initModel();
		if(!Config.DISABLE_HOE) HOE_HEAD.initModel();
		if(!Config.DISABLE_HAND_PICK) HANDPICK_HEAD.initModel();
		if(!Config.DISABLE_HAMMER) HAMMER_HEAD.initModel();
		if(!Config.DISABLE_CLIMBING_PICK) CLIMBING_PICK_HEAD.initModel();

		if(!Config.DISABLE_SWORD) SWORD_BLADE.initModel();
		if(!Config.DISABLE_SWORD) CROSSGUARD.initModel();
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
