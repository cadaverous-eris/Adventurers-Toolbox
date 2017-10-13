package toolbox.client.models;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import toolbox.Toolbox;

public class ToolHeadModel implements IModel {

	private String modid;
	private String toolName;
	private String partName;
	private String matName;
	
	public ToolHeadModel(String modid, String toolName, String partName, String matName) {
		this.modid = modid;
		this.toolName = toolName;
		this.partName = partName;
		this.matName = matName;
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return ImmutableList.<ResourceLocation>of(new ResourceLocation(modid, "items/" + toolName + "/" + partName + "_" + matName));
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			java.util.function.Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		
		ImmutableMap<TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);
		
		TRSRTransformation transform = (TRSRTransformation.identity());
		
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
		ImmutableList<ResourceLocation> textures = ImmutableList.<ResourceLocation>of(new ResourceLocation(modid, "items/" + toolName + "/" + partName + "_" + matName));
		builder.addAll(new ItemLayerModel(textures).bake(state, format, bakedTextureGetter).getQuads(null, null, 0));
		return new BakedToolHeadModel(this, builder.build(), format, Maps.immutableEnumMap(transformMap),
				Maps.<String, IBakedModel>newHashMap());
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	public enum LoaderToolHead implements ICustomModelLoader {
		INSTANCE;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return (modelLocation.getResourcePath().length() > 3 && modelLocation.getResourcePath().substring(0, 3).equals("th_"));
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) {
			String path = modelLocation.getResourcePath().substring(3);
			int index = path.indexOf('_');
			if(path.charAt(0) == 'c') index = path.indexOf('_', index + 1);
			String toolName = path.substring(0, index);
			path = path.substring(index + 1);
			index = path.indexOf('_');
			String partName = path.substring(0, index);
			path = path.substring(index + 1);
			String matName = path;
			
			return new ToolHeadModel(modelLocation.getResourceDomain(), toolName, partName, matName);
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {

		}
	}
	
	private static final class BakedToolHeadModel implements IBakedModel {

		private final ToolHeadModel parent;
		private final ImmutableMap<TransformType, TRSRTransformation> transforms;
		private final ImmutableList<BakedQuad> quads;
		private final VertexFormat format;

		public BakedToolHeadModel(ToolHeadModel parent, ImmutableList<BakedQuad> quads, VertexFormat format,
				ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
				Map<String, IBakedModel> cache) {
			this.quads = quads;
			this.format = format;
			this.parent = parent;
			this.transforms = itemTransforms();
		}

		@Override
		public ItemOverrideList getOverrides() {
			return ItemOverrideList.NONE;
		}

		@Override
		public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
			return PerspectiveMapWrapper.handlePerspective(this, transforms, cameraTransformType);
		}

		private static ImmutableMap<TransformType, TRSRTransformation> itemTransforms() {
			ImmutableMap.Builder<TransformType, TRSRTransformation> builder = ImmutableMap.builder();
			builder.put(TransformType.GROUND, get(0 , 2, 0, 0, 0, 0, 0.5f));
			builder.put(TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
			builder.put(TransformType.FIXED, get(0, 0, 0, 0, 180, 0, 1));
			builder.put(TransformType.THIRD_PERSON_RIGHT_HAND, get(0, 3, 1, 0, 0, 0, 0.55f));
			builder.put(TransformType.THIRD_PERSON_LEFT_HAND, leftify(get(0, 3, 1, 0, 0, 0, 0.55f)));
			builder.put(TransformType.FIRST_PERSON_RIGHT_HAND, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
			builder.put(TransformType.FIRST_PERSON_LEFT_HAND, leftify(get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f)));
			return (ImmutableMap) builder.build();
		}

		private static TRSRTransformation get(float tx, float ty, float tz, float ax, float ay, float az, float s) {
			return TRSRTransformation.blockCenterToCorner(new TRSRTransformation(
					new Vector3f(tx / 16, ty / 16, tz / 16),
					TRSRTransformation.quatFromXYZDegrees(new Vector3f(ax, ay, az)), new Vector3f(s, s, s), null));
		}
		
		private static final TRSRTransformation flipX = new TRSRTransformation(null, null, new Vector3f(-1, 1, 1),
				null);

		private static TRSRTransformation leftify(TRSRTransformation transform) {
			return TRSRTransformation.blockCenterToCorner(
					flipX.compose(TRSRTransformation.blockCornerToCenter(transform)).compose(flipX));
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
