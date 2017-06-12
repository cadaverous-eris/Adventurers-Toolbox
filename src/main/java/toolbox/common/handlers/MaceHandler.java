package toolbox.common.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toolbox.common.items.ModItems;

public class MaceHandler {
	
	@SubscribeEvent
	public void onMaceAttack(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		if (source != null && source instanceof EntityDamageSource) {
			Entity entity = ((EntityDamageSource) source).getSourceOfDamage();
			if (entity != null && entity instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) entity;
				if (attacker.getHeldItemMainhand().getItem() == ModItems.MACE) {
					if (event.getEntityLiving().getTotalArmorValue() > 0) {
						event.setAmount(event.getAmount() * event.getEntityLiving().getTotalArmorValue() / 12F);
					}
				}
			}
		}
	}

}
