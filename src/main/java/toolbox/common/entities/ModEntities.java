package toolbox.common.entities;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.Toolbox;
import toolbox.client.renderers.RenderProjectileItem;
import toolbox.common.items.ModItems;

public final class ModEntities {
	
	private ModEntities() {}
	
	public static void init() {
		int entityId = 0;
		EntityRegistry.registerModEntity(new ResourceLocation(Toolbox.MODID + ":rock"), EntityRock.class, "rock", entityId++, Toolbox.instance, 64, 1, true);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityRock.class, new RenderProjectileItem.Factory(new ItemStack(ModItems.rock)));
	}

}
