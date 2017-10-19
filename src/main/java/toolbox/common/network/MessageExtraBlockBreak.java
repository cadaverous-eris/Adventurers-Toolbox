package toolbox.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toolbox.client.handler.ExtraBlockBreakHandler;

public class MessageExtraBlockBreak implements IMessage {

	int entityID = 0;
	int numBlocks = 0;
	int progress = 0;
	int[][] positions = new int[numBlocks][3];
	
	public MessageExtraBlockBreak() {
		
	}
	
	public MessageExtraBlockBreak(int entityID, BlockPos[] positions, int progress) {
		this.entityID = entityID;
		this.numBlocks = positions.length;
		this.progress = progress;
		this.positions = new int[numBlocks][3];
		
		for (int i = 0; i < numBlocks; i++) {
			BlockPos pos = positions[i];
			this.positions[i][0] = pos.getX();
			this.positions[i][1] = pos.getY();
			this.positions[i][2] = pos.getZ();
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityID = buf.readInt();
		this.numBlocks = buf.readInt();
		this.progress = buf.readInt();
		this.positions = new int[this.numBlocks][3];
		
		for (int i = 0; i < this.numBlocks; i++) {
			this.positions[i][0] = buf.readInt();
			this.positions[i][1] = buf.readInt();
			this.positions[i][2] = buf.readInt();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityID);
		buf.writeInt(this.numBlocks);
		buf.writeInt(this.progress);
		
		for (int i = 0; i < this.numBlocks; i++) {
			buf.writeInt(this.positions[i][0]);
			buf.writeInt(this.positions[i][1]);
			buf.writeInt(this.positions[i][2]);
		}
	}
	
	public static class MessageHolder implements IMessageHandler<MessageExtraBlockBreak, IMessage> {

		@SideOnly(Side.CLIENT)
		@Override
		public IMessage onMessage(MessageExtraBlockBreak message, MessageContext ctx) {
			BlockPos[] positions = new BlockPos[message.numBlocks];
			
			for (int i = 0; i < message.numBlocks; i++) {
				positions[i] = new BlockPos(message.positions[i][0], message.positions[i][1], message.positions[i][2]);
			}
			
			ExtraBlockBreakHandler.INSTANCE.sendBlockBreakProgress(message.entityID, positions, message.progress);
			
			return null;
		}
		
	}

}
