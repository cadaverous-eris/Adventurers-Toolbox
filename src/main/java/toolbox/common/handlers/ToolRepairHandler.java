package toolbox.common.handlers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toolbox.common.items.tools.IBladeTool;
import toolbox.common.items.tools.IHeadTool;

public class ToolRepairHandler {
	
	@SubscribeEvent
	public void onAnvilEvent(AnvilUpdateEvent event) {
		ItemStack tool = event.getLeft();
		
		if (tool.getItem() instanceof IHeadTool || tool.getItem() instanceof IBladeTool) {
			ItemStack repairItem = event.getRight();
			if (repairItem.getItem() == tool.getItem()) {
				boolean compatibleTools = false;
				
				if (tool.getItem() instanceof IHeadTool) {
					compatibleTools = IHeadTool.getHeadMat(tool) == IHeadTool.getHeadMat(repairItem);
				} else if (tool.getItem() instanceof IBladeTool) {
					compatibleTools = IBladeTool.getBladeMat(tool) == IBladeTool.getBladeMat(repairItem);
				}
				
				if (!compatibleTools) {
					event.setCanceled(true);
					return;
				}
			}
			
			if (event.getOutput().isEmpty() && tool.getItem().getIsRepairable(tool, event.getRight())) {
				ItemStack output = tool.copy();
				
				int l2 = Math.min(tool.getItemDamage(), tool.getMaxDamage() / 4);
				int i3;
				
				if (l2 <= 0) return;
				
                for (i3 = 0; l2 > 0 && i3 < event.getRight().getCount(); ++i3) {
                    int j3 = output.getItemDamage() - l2;
                    output.setItemDamage(j3);
                    l2 = Math.min(tool.getItemDamage(), tool.getMaxDamage() / 4);
                }

                event.setMaterialCost(i3);
                event.setCost(tool.isItemEnchanted() ? i3 * 2 : i3);
                event.setOutput(output);
			}
		}
	}

}
