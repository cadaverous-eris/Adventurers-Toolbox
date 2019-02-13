package toolbox;

import org.apache.logging.log4j.LogManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.common.CommonProxy;
import toolbox.common.items.ModItems;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IBladeTool;
import toolbox.common.items.tools.ICrossguardTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.items.tools.IHeadTool;
import toolbox.common.materials.ModMaterials;
import toolbox.common.network.PacketHandler;

@Mod(modid = Toolbox.MODID, name = Toolbox.NAME, version = Toolbox.VERSION, dependencies = Toolbox.DEPENDENCIES, useMetadata = false)
public class Toolbox {
	
	public static final String MODID = "toolbox";
	public static final String NAME = "Adventurer's Toolbox";
	public static final String VERSION = "0.2.14";
	public static final String DEPENDENCIES = "after:thaumcraft";
	
	@SidedProxy(clientSide = "toolbox.client.ClientProxy", serverSide = "toolbox.common.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance
	public static Toolbox instance;
	
	public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(MODID);
	
	public static CreativeTabs partsTab = new CreativeTabs("partsTab") {
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			ItemStack stack = new ItemStack(ModItems.pickaxe_head, 1, 3);
			return stack;
		}
	};
	
	public static CreativeTabs toolsTab = new CreativeTabs("toolsTab") {		
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			ItemStack stack = new ItemStack(ModItems.pickaxe);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(IHeadTool.HEAD_TAG, "iron");
			tag.setString(IHaftTool.HAFT_TAG, "wood");
			tag.setString(IHandleTool.HANDLE_TAG, "leather");
			tag.setString(IAdornedTool.ADORNMENT_TAG, "diamond");
			stack.setTagCompound(tag);
			return stack;
		}
	};
	
	public static CreativeTabs weaponsTab = new CreativeTabs("weaponsTab") {		
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			ItemStack stack = new ItemStack(ModItems.sword);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString(IBladeTool.BLADE_TAG, "iron");
			tag.setString(ICrossguardTool.CROSSGUARD_TAG, "gold");
			tag.setString(IHandleTool.HANDLE_TAG, "leather");
			tag.setString(IAdornedTool.ADORNMENT_TAG, "diamond");
			stack.setTagCompound(tag);
			return stack;
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PacketHandler.registerMessages();
		
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		int meta = 0;
		for (int i : ModItems.pickaxe_head.meta_map.keySet()) {
			if (ModItems.pickaxe_head.meta_map.get(i) == ModMaterials.HEAD_GOLD) {
				meta = i;
				break;
			}
		}
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

}
