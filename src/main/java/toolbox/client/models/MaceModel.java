package toolbox.client.models;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import toolbox.Toolbox;
import toolbox.common.items.ModItems;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.items.tools.IHeadTool;
import toolbox.common.materials.ModMaterials;

public class MaceModel implements IModel {
	
	public static final ModelResourceLocation LOCATION = new ModelResourceLocation(
			new ResourceLocation(Toolbox.MODID, "mace"), "inventory");

	public static final IModel MODEL = new MaceModel();

	@Nullable
	private final ResourceLocation headTexture;
	@Nullable
	private final ResourceLocation haftTexture;
	@Nullable
	private final ResourceLocation handleTexture;
	@Nullable
	private final ResourceLocation adornmentTexture;

	public MaceModel() {
		headTexture = null;
		haftTexture = null;
		handleTexture = null;
		adornmentTexture = null;
	}
	
	public MaceModel(ResourceLocation head, ResourceLocation haft, ResourceLocation handle, ResourceLocation adornment) {
		this.headTexture = head;
		this.haftTexture = haft;
		this.handleTexture = handle;
		this.adornmentTexture = adornment;
	}

	@Override
	public IModel retexture(ImmutableMap<String, String> textures) {
		ResourceLocation head = null;
		ResourceLocation haft = null;
		ResourceLocation handle = null;
		ResourceLocation adornment = null;
		if (textures.containsKey("head")) {
			head = new ResourceLocation(Materials.head_registry.get(textures.get("head")).getModId(), "items/mace/head_" + textures.get("head"));
		}
		if (textures.containsKey("haft")) {
			haft = new ResourceLocation(Materials.haft_registry.get(textures.get("haft")).getModId(), "items/mace/haft_" + textures.get("haft"));
		}
		if (textures.containsKey("handle")) {
			handle = new ResourceLocation(Materials.handle_registry.get(textures.get("handle")).getModId(), "items/mace/handle_" + textures.get("handle"));
		}
		if (textures.containsKey("adornment")) {
			adornment = new ResourceLocation(Materials.adornment_registry.get(textures.get("adornment")).getModId(), "items/mace/adornment_" + textures.get("adornment"));
		}
		return new MaceModel(head, haft, handle, adornment);
	}
	
	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		ResourceLocation head = null;
		ResourceLocation haft = null;
		ResourceLocation handle = null;
		ResourceLocation adornment = null;
		if (customData.containsKey("head")) {
			head = new ResourceLocation(Materials.head_registry.get(customData.get("head")).getModId(), "items/mace/head_" + customData.get("head"));
		}
		if (customData.containsKey("haft")) {
			haft = new ResourceLocation(Materials.haft_registry.get(customData.get("haft")).getModId(), "items/mace/haft_" + customData.get("haft"));
		}
		if (customData.containsKey("handle")) {
			handle = new ResourceLocation(Materials.handle_registry.get(customData.get("handle")).getModId(), "items/mace/handle_" + customData.get("handle"));
		}
		if (customData.containsKey("adornment")) {
			adornment = new ResourceLocation(Materials.adornment_registry.get(customData.get("adornment")).getModId(), "items/mace/adornment_" + customData.get("adornment"));
		}
		return new MaceModel(head, haft, handle, adornment);
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		
		ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
		for (HeadMaterial mat : Materials.head_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/mace/head_" + mat.getName()));
		}
		for (HaftMaterial mat : Materials.haft_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/mace/haft_" + mat.getName()));
		}
		for (HandleMaterial mat : Materials.handle_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/mace/handle_" + mat.getName()));
		}
		for (AdornmentMaterial mat : Materials.adornment_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/mace/adornment_" + mat.getName()));
		}
		Collection textures = builder.build();
		return textures;
		
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			java.util.function.Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

		ImmutableMap<TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);

		TRSRTransformation transform = (TRSRTransformation.identity());

		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		if (headTexture != null && haftTexture != null && handleTexture != null) {
			
			ImmutableList.Builder<ResourceLocation> texBuilder = ImmutableList.builder();
			if (haftTexture != null) {
				texBuilder.add(haftTexture);
			}
			if (headTexture != null) {
				texBuilder.add(headTexture);
			}
			if (handleTexture != null) {
				texBuilder.add(handleTexture);
			}
			if (adornmentTexture != null) {
				texBuilder.add(adornmentTexture);
			}
			ImmutableList<ResourceLocation> textures = texBuilder.build();
			
			IBakedModel model = (new ItemLayerModel(textures)).bake(state, format, bakedTextureGetter);
			builder.addAll(model.getQuads(null, null, 0));
		}

		return new BakedMaceModel(this, builder.build(), format, Maps.immutableEnumMap(transformMap),
				Maps.<String, IBakedModel>newHashMap());
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	public enum LoaderMace implements ICustomModelLoader {
		INSTANCE;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getResourceDomain().equals(Toolbox.MODID)
					&& modelLocation.getResourcePath().equals("mace");
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) {
			return MODEL;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {

		}
	}

	private static final class BakedMaceOverrideHandler extends ItemOverrideList {
		public static final BakedMaceOverrideHandler INSTANCE = new BakedMaceOverrideHandler();

		private BakedMaceOverrideHandler() {
			super(ImmutableList.<ItemOverride>of());
		}

		@Override
		@Nonnull
		public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, @Nonnull ItemStack stack,
				@Nullable World world, @Nullable EntityLivingBase entity) {

			if (stack.getItem() != ModItems.mace) {
				return originalModel;
			}

			BakedMaceModel model = (BakedMaceModel) originalModel;

			String key = IHeadTool.getHeadMat(stack).getName() + "|"
					+ IHaftTool.getHaftMat(stack).getName() + "|"
					+ IHandleTool.getHandleMat(stack).getName() + "|"
					+ IAdornedTool.getAdornmentMat(stack).getName();

			if (!model.cache.containsKey(key)) {
				ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
				builder.put("head", IHeadTool.getHeadMat(stack).getName());
				builder.put("haft", IHaftTool.getHaftMat(stack).getName());
				builder.put("handle", IHandleTool.getHandleMat(stack).getName());
				if (IAdornedTool.getAdornmentMat(stack) != ModMaterials.ADORNMENT_NULL) {
					builder.put("adornment", IAdornedTool.getAdornmentMat(stack).getName());
				}
				IModel parent = model.parent.retexture(builder.build());
				Function<ResourceLocation, TextureAtlasSprite> textureGetter;
				textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
					public TextureAtlasSprite apply(ResourceLocation location) {
						return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
					}
				};
				IBakedModel bakedModel = parent.bake(new SimpleModelState(model.transforms), model.format,
						textureGetter);
				model.cache.put(key, bakedModel);
				return bakedModel;
			}

			return model.cache.get(key);
		}

	}

	private static final class BakedMaceModel extends BakedToolModel {

		public BakedMaceModel(MaceModel parent, ImmutableList<BakedQuad> quads, VertexFormat format,
				ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
				Map<String, IBakedModel> cache) {
			super(parent, quads, format, transforms, cache);
		}

		@Override
		public ItemOverrideList getOverrides() {
			return BakedMaceOverrideHandler.INSTANCE;
		}

	}

}
