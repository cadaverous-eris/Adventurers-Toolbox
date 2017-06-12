package toolbox.common.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
		
		PICKAXE_HEAD = new ItemToolHead("pickaxe_head");
		AXE_HEAD = new ItemToolHead("axe_head");
		SHOVEL_HEAD = new ItemToolHead("shovel_head");
		HOE_HEAD = new ItemToolHead("hoe_head");
		HANDPICK_HEAD = new ItemToolHead("handpick_head");
		HAMMER_HEAD = new ItemToolHead("hammer_head");
		CLIMBING_PICK_HEAD = new ItemToolHead("climbing_pick_head");
		
		SWORD_BLADE = new ItemToolHead("sword_blade");
		CROSSGUARD = new ItemToolHead("sword_crossguard");
		DAGGER_BLADE = new ItemToolHead("dagger_blade");
		MACE_HEAD = new ItemToolHead("mace_head");
		
		HANDLE = new ItemToolHandle();
		
		PICKAXE = new ItemPickaxe();
		AXE = new ItemAxe();
		SHOVEL = new ItemShovel();
		HOE = new ItemHoe();
		HANDPICK = new ItemHandpick();
		HAMMER = new ItemHammer();
		CLIMBING_PICK = new ItemClimbingPick();
		
		SWORD = new ItemSword();
		DAGGER = new ItemDagger();
		MACE = new ItemMace();
		
		GUIDE_BOOK = new ItemGuideBook();
		
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		
		ROCK.initModel();
		
		PICKAXE_HEAD.initModel();
		AXE_HEAD.initModel();
		SHOVEL_HEAD.initModel();
		HOE_HEAD.initModel();
		HANDPICK_HEAD.initModel();
		HAMMER_HEAD.initModel();
		CLIMBING_PICK_HEAD.initModel();
		
		SWORD_BLADE.initModel();
		CROSSGUARD.initModel();
		DAGGER_BLADE.initModel();
		MACE_HEAD.initModel();
		
		HANDLE.initModel();
		
		PICKAXE.initModel();
		AXE.initModel();
		SHOVEL.initModel();
		HOE.initModel();
		HANDPICK.initModel();
		HAMMER.initModel();
		CLIMBING_PICK.initModel();
		
		SWORD.initModel();
		DAGGER.initModel();
		MACE.initModel();
		
		GUIDE_BOOK.initModel();
		
	}

}
