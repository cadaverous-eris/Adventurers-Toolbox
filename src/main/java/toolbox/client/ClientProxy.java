package toolbox.client;

import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import toolbox.client.gui.GuiBook;
import toolbox.client.handler.ExtraBlockBreakHandler;
import toolbox.client.models.AxeModel;
import toolbox.client.models.DaggerModel;
import toolbox.client.models.HammerModel;
import toolbox.client.models.HandpickModel;
import toolbox.client.models.HoeModel;
import toolbox.client.models.MaceModel;
import toolbox.client.models.PickaxeModel;
import toolbox.client.models.ShovelModel;
import toolbox.client.models.SwordModel;
import toolbox.client.models.ToolHeadModel;
import toolbox.common.CommonProxy;
import toolbox.common.entities.ModEntities;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModelLoaderRegistry.registerLoader(ToolHeadModel.LoaderToolHead.INSTANCE);
		
		ModelLoaderRegistry.registerLoader(PickaxeModel.LoaderPickaxe.INSTANCE);
		ModelLoaderRegistry.registerLoader(AxeModel.LoaderAxe.INSTANCE);
		ModelLoaderRegistry.registerLoader(ShovelModel.LoaderShovel.INSTANCE);
		ModelLoaderRegistry.registerLoader(HoeModel.LoaderHoe.INSTANCE);
		ModelLoaderRegistry.registerLoader(HandpickModel.LoaderHandpick.INSTANCE);
		ModelLoaderRegistry.registerLoader(HammerModel.LoaderHammer.INSTANCE);
		
		ModelLoaderRegistry.registerLoader(SwordModel.LoaderSword.INSTANCE);
		ModelLoaderRegistry.registerLoader(DaggerModel.LoaderDagger.INSTANCE);
		ModelLoaderRegistry.registerLoader(MaceModel.LoaderMace.INSTANCE);
		
		ModEntities.initRenderers();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		MinecraftForge.EVENT_BUS.register(ExtraBlockBreakHandler.INSTANCE);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		
		GuiBook.initPages();
		
	}

}
