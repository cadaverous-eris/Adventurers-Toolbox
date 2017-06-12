package toolbox.client.models;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import api.materials.AdornmentMaterial;
import api.materials.HaftMaterial;
import api.materials.HandleMaterial;
import api.materials.HeadMaterial;
import api.materials.Materials;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IModelCustomData;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import toolbox.Toolbox;
import toolbox.common.items.ModItems;
import toolbox.common.items.tools.IAdornedTool;
import toolbox.common.items.tools.IHaftTool;
import toolbox.common.items.tools.IHandleTool;
import toolbox.common.items.tools.IHeadTool;
import toolbox.common.materials.ModMaterials;

public class HoeModel implements IModel, IRetexturableModel, IModelCustomData {
	
	public static final ModelResourceLocation LOCATION = new ModelResourceLocation(
			new ResourceLocation(Toolbox.MODID, "hoe"), "inventory");

	public static final IModel MODEL = new HoeModel();

	@Nullable
	private final ResourceLocation headTexture;
	@Nullable
	private final ResourceLocation haftTexture;
	@Nullable
	private final ResourceLocation handleTexture;
	@Nullable
	private final ResourceLocation adornmentTexture;

	public HoeModel() {
		headTexture = null;
		haftTexture = null;
		handleTexture = null;
		adornmentTexture = null;
	}
	
	public HoeModel(ResourceLocation head, ResourceLocation haft, ResourceLocation handle, ResourceLocation adornment) {
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
			head = new ResourceLocation(Materials.head_registry.get(textures.get("head")).getModId(), "items/hoe/head_" + textures.get("head"));
		}
		if (textures.containsKey("haft")) {
			haft = new ResourceLocation(Materials.haft_registry.get(textures.get("haft")).getModId(), "items/hoe/haft_" + textures.get("haft"));
		}
		if (textures.containsKey("handle")) {
			handle = new ResourceLocation(Materials.handle_registry.get(textures.get("handle")).getModId(), "items/hoe/handle_" + textures.get("handle"));
		}
		if (textures.containsKey("adornment")) {
			adornment = new ResourceLocation(Materials.adornment_registry.get(textures.get("adornment")).getModId(), "items/hoe/adornment_" + textures.get("adornment"));
		}
		return new HoeModel(head, haft, handle, adornment);
	}
	
	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		ResourceLocation head = null;
		ResourceLocation haft = null;
		ResourceLocation handle = null;
		ResourceLocation adornment = null;
		if (customData.containsKey("head")) {
			head = new ResourceLocation(Materials.head_registry.get(customData.get("head")).getModId(), "items/hoe/head_" + customData.get("head"));
		}
		if (customData.containsKey("haft")) {
			haft = new ResourceLocation(Materials.haft_registry.get(customData.get("haft")).getModId(), "items/hoe/haft_" + customData.get("haft"));
		}
		if (customData.containsKey("handle")) {
			handle = new ResourceLocation(Materials.handle_registry.get(customData.get("handle")).getModId(), "items/hoe/handle_" + customData.get("handle"));
		}
		if (customData.containsKey("adornment")) {
			adornment = new ResourceLocation(Materials.adornment_registry.get(customData.get("adornment")).getModId(), "items/hoe/adornment_" + customData.get("adornment"));
		}
		return new HoeModel(head, haft, handle, adornment);
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		
		ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
		for (HeadMaterial mat : Materials.head_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/hoe/head_" + mat.getName()));
		}
		for (HaftMaterial mat : Materials.haft_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/hoe/haft_" + mat.getName()));
		}
		for (HandleMaterial mat : Materials.handle_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/hoe/handle_" + mat.getName()));
		}
		for (AdornmentMaterial mat : Materials.adornment_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/hoe/adornment_" + mat.getName()));
		}
		Collection textures = builder.build();
		return textures;
		
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

		ImmutableMap<TransformType, TRSRTransformation> transformMap = IPerspectiveAwareModel.MapWrapper
				.getTransforms(state);

		TRSRTransformation transform = state.apply(Optional.<IModelPart>absent()).or(TRSRTransformation.identity());

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

		return new BakedHoeModel(this, builder.build(), format, Maps.immutableEnumMap(transformMap),
				Maps.<String, IBakedModel>newHashMap());
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}
	
	public enum LoaderHoe implements ICustomModelLoader {
		INSTANCE;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getResourceDomain().equals(Toolbox.MODID)
					&& modelLocation.getResourcePath().equals("hoe");
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) {
			return MODEL;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {

		}
	}
	
	private static final class BakedHoeOverrideHandler extends ItemOverrideList {
		public static final BakedHoeOverrideHandler INSTANCE = new BakedHoeOverrideHandler();

		private BakedHoeOverrideHandler() {
			super(ImmutableList.<ItemOverride>of());
		}

		@Override
		@Nonnull
		public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, @Nonnull ItemStack stack,
				@Nullable World world, @Nullable EntityLivingBase entity) {

			if (stack.getItem() != ModItems.HOE) {
				return originalModel;
			}

			BakedHoeModel model = (BakedHoeModel) originalModel;

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
	
	private static final class BakedHoeModel implements IPerspectiveAwareModel {

		private final HoeModel parent;
		private final Map<String, IBakedModel> cache;
		private final ImmutableMap<TransformType, TRSRTransformation> transforms;
		private final ImmutableList<BakedQuad> quads;
		private final VertexFormat format;

		public BakedHoeModel(HoeModel parent, ImmutableList<BakedQuad> quads, VertexFormat format,
				ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
				Map<String, IBakedModel> cache) {
			this.quads = quads;
			this.format = format;
			this.parent = parent;
			this.transforms = itemTransforms();
			this.cache = cache;
		}

		@Override
		public ItemOverrideList getOverrides() {
			return BakedHoeOverrideHandler.INSTANCE;
		}

		@Override
		public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
			return IPerspectiveAwareModel.MapWrapper.handlePerspective(this,
					(ImmutableMap<TransformType, TRSRTransformation>) this.transforms, cameraTransformType);
		}

		private static ImmutableMap<TransformType, TRSRTransformation> itemTransforms() {
			ImmutableMap.Builder<TransformType, TRSRTransformation> builder = ImmutableMap.builder();
			builder.put(TransformType.GROUND, get(0 , 2, 0, 0, 0, 0, 0.5f));
			builder.put(TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
			builder.put(TransformType.FIXED, get(0, 0, 0, 0, 180, 0, 1));
			builder.put(TransformType.THIRD_PERSON_RIGHT_HAND, get(0, 4, 0.5f,         0, -90, 55, 0.85f));
			builder.put(TransformType.THIRD_PERSON_LEFT_HAND, get(0, 4, 0.5f,         0, 90, -55, 0.85f));
			builder.put(TransformType.FIRST_PERSON_RIGHT_HAND, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
			builder.put(TransformType.FIRST_PERSON_LEFT_HAND, get(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f));
			return (ImmutableMap) builder.build();
		}

		private static TRSRTransformation get(float tx, float ty, float tz, float ax, float ay, float az, float s) {
			return TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
					new Vector3f(tx / 16, ty / 16, tz / 16),
					TRSRTransformation.quatFromXYZDegrees(new Vector3f(ax, ay, az)), new Vector3f(s, s, s), null));
		}

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
			if (side == null)
				return quads;
			return ImmutableList.of();
		}

		public boolean isAmbientOcclusion() {
			return true;
		}

		public boolean isGui3d() {
			return false;
		}

		public boolean isBuiltInRenderer() {
			return false;
		}

		public TextureAtlasSprite getParticleTexture() {
			return null;
		}

		public ItemCameraTransforms getItemCameraTransforms() {
			return ItemCameraTransforms.DEFAULT;
		}
	}

}
