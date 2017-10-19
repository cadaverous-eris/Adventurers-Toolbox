package toolbox.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.common.items.ModItems;

public class EntityRock extends EntityThrowable {

	public EntityRock(World world) {
		super(world);
	}

	public EntityRock(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	public EntityRock(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			for (int i = 0; i < 8; ++i) {
				this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, 0.0D, 0.0D,
						0.0D, Item.getIdFromItem(ModItems.rock), 0);
			}
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		//System.out.println(MathHelper.sqrt((MathHelper.abs((float) this.motionX) + MathHelper.abs((float) this.motionY) + MathHelper.abs((float) this.motionZ))));
		if (result.entityHit != null) {
			float f = MathHelper.sqrt((MathHelper.abs((float) this.motionX) + MathHelper.abs((float) this.motionY) + MathHelper.abs((float) this.motionZ)));
			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 4F * f);
		}

		if (!this.world.isRemote) {
			if (this.rand.nextFloat() < 0.5F) {
				EntityItem rock = new EntityItem(world, posX, posY, posZ, new ItemStack(ModItems.rock));
				world.spawnEntity(rock);
			} else {
				this.world.setEntityState(this, (byte) 3);
			}
			this.setDead();
		}
	}

	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

}
