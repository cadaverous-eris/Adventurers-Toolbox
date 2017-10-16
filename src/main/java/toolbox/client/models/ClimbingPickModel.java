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
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
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

public class ClimbingPickModel implements IModel {
	
	public static final ModelResourceLocation LOCATION = new ModelResourceLocation(
			new ResourceLocation(Toolbox.MODID, "climbing_pick"), "inventory");

	public static final IModel MODEL = new ClimbingPickModel();

	@Nullable
	private final ResourceLocation headTexture;
	@Nullable
	private final ResourceLocation haftTexture;
	@Nullable
	private final ResourceLocation handleTexture;

	public ClimbingPickModel() {
		headTexture = null;
		haftTexture = null;
		handleTexture = null;
	}
	
	public ClimbingPickModel(ResourceLocation head, ResourceLocation haft, ResourceLocation handle) {
		this.headTexture = head;
		this.haftTexture = haft;
		this.handleTexture = handle;
	}

	@Override
	public IModel retexture(ImmutableMap<String, String> textures) {
		ResourceLocation head = null;
		ResourceLocation haft = null;
		ResourceLocation handle = null;
		ResourceLocation adornment = null;
		if (textures.containsKey("head")) {
			head = new ResourceLocation(Materials.head_registry.get(textures.get("head")).getModId(), "items/climbing_pick/head_" + textures.get("head"));
		}
		if (textures.containsKey("haft")) {
			haft = new ResourceLocation(Materials.haft_registry.get(textures.get("haft")).getModId(), "items/climbing_pick/haft_" + textures.get("haft"));
		}
		if (textures.containsKey("handle")) {
			handle = new ResourceLocation(Materials.handle_registry.get(textures.get("handle")).getModId(), "items/climbing_pick/handle_" + textures.get("handle"));
		}
		return new ClimbingPickModel(head, haft, handle);
	}
	
	@Override
	public IModel process(ImmutableMap<String, String> customData) {
		ResourceLocation head = null;
		ResourceLocation haft = null;
		ResourceLocation handle = null;
		if (customData.containsKey("head")) {
			head = new ResourceLocation(Materials.head_registry.get(customData.get("head")).getModId(), "items/climbing_pick/head_" + customData.get("head"));
		}
		if (customData.containsKey("haft")) {
			haft = new ResourceLocation(Materials.haft_registry.get(customData.get("haft")).getModId(), "items/climbing_pick/haft_" + customData.get("haft"));
		}
		if (customData.containsKey("handle")) {
			handle = new ResourceLocation(Materials.handle_registry.get(customData.get("handle")).getModId(), "items/climbing_pick/handle_" + customData.get("handle"));
		}
		return new ClimbingPickModel(head, haft, handle);
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		
		ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
		for (HeadMaterial mat : Materials.head_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/climbing_pick/head_" + mat.getName()));
		}
		for (HaftMaterial mat : Materials.haft_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/climbing_pick/haft_" + mat.getName()));
		}
		for (HandleMaterial mat : Materials.handle_registry.values()) {
			builder.add(new ResourceLocation(mat.getModId(), "items/climbing_pick/handle_" + mat.getName()));
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
			ImmutableList<ResourceLocation> textures = texBuilder.build();
			
			IBakedModel model = (new ItemLayerModel(textures)).bake(state, format, bakedTextureGetter);
			builder.addAll(model.getQuads(null, null, 0));
		}

		return new BakedClimbingPickModel(this, builder.build(), format, Maps.immutableEnumMap(transformMap),
				Maps.<String, IBakedModel>newHashMap());
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	public enum LoaderClimbingPick implements ICustomModelLoader {
		INSTANCE;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getResourceDomain().equals(Toolbox.MODID)
					&& modelLocation.getResourcePath().equals("climbing_pick");
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) {
			return MODEL;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {

		}
	}

	private static final class BakedClimbingPickOverrideHandler extends ItemOverrideList {
		public static final BakedClimbingPickOverrideHandler INSTANCE = new BakedClimbingPickOverrideHandler();

		private BakedClimbingPickOverrideHandler() {
			super(ImmutableList.<ItemOverride>of());
		}

		@Override
		@Nonnull
		public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, @Nonnull ItemStack stack,
				@Nullable World world, @Nullable EntityLivingBase entity) {

			if (stack.getItem() != ModItems.climbing_pick) {
				return originalModel;
			}

			BakedClimbingPickModel model = (BakedClimbingPickModel) originalModel;

			String key = IHeadTool.getHeadMat(stack).getName() + "|"
					+ IHaftTool.getHaftMat(stack).getName() + "|"
					+ IHandleTool.getHandleMat(stack).getName();

			if (!model.cache.containsKey(key)) {
				ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
				builder.put("head", IHeadTool.getHeadMat(stack).getName());
				builder.put("haft", IHaftTool.getHaftMat(stack).getName());
				builder.put("handle", IHandleTool.getHandleMat(stack).getName());
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

	private static final class BakedClimbingPickModel extends BakedToolModel {

		public BakedClimbingPickModel(ClimbingPickModel parent, ImmutableList<BakedQuad> quads, VertexFormat format,
				ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
				Map<String, IBakedModel> cache) {
			super(parent, quads, format, transforms, cache);
		}

		@Override
		public ItemOverrideList getOverrides() {
			return BakedClimbingPickOverrideHandler.INSTANCE;
		}
		
	}

}
