package toolbox.common.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import toolbox.Toolbox;

public class PacketHandler {

	public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Toolbox.MODID);
	
	public static void registerMessages() {
		int id = 0;
		
		INSTANCE.registerMessage(MessageExtraBlockBreak.MessageHolder.class, MessageExtraBlockBreak.class, id++, Side.CLIENT);
	}
	
}
