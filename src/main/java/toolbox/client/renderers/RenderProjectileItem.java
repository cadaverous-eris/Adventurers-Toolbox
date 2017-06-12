package toolbox.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import toolbox.common.entities.EntityRock;
import toolbox.common.items.ModItems;

public class RenderProjectileItem<T extends Entity> extends Render<T> {
	
	protected final ItemStack item;
	private final RenderItem itemRenderer;

	protected RenderProjectileItem(RenderManager renderManager, RenderItem itemRendererIn, ItemStack itemIn) {
		super(renderManager);
		this.itemRenderer = itemRendererIn;
		this.item = itemIn;
	}
	
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(
				(float) (this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX,
				1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		this.bindTexture(getEntityTexture(entity));

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		this.itemRenderer.renderItem(this.getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public ItemStack getStackToRender(T entityIn) {
		return this.item;
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
	
	public static class Factory<T extends Entity> implements IRenderFactory<T> {
		
		private final ItemStack stack;
		
		public Factory(ItemStack itemStack) {
			this.stack = itemStack;
		}

		@Override
		public Render<T> createRenderFor(RenderManager manager) {
			return new RenderProjectileItem(manager, Minecraft.getMinecraft().getRenderItem(), stack);
		}

	}

}
