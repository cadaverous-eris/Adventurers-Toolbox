package toolbox.client.models;

import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.TRSRTransformation;

public abstract class BakedToolModel implements IBakedModel {

	protected final IModel parent;
	protected final Map<String, IBakedModel> cache;
	protected final ImmutableMap<TransformType, TRSRTransformation> transforms;
	protected final ImmutableList<BakedQuad> quads;
	protected final VertexFormat format;

	public BakedToolModel(IModel parent, ImmutableList<BakedQuad> quads, VertexFormat format,
			ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
			Map<String, IBakedModel> cache) {
		this.quads = quads;
		this.format = format;
		this.parent = parent;
		this.transforms = itemTransforms();
		this.cache = cache;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		return PerspectiveMapWrapper.handlePerspective(this, transforms, cameraTransformType);
	}

	protected ImmutableMap<TransformType, TRSRTransformation> itemTransforms() {
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

	protected TRSRTransformation get(float tx, float ty, float tz, float ax, float ay, float az, float s) {
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

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return null;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}
	
}