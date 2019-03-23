package cn.yr.netty.files.directory2.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * �Զ�����Ϣͷ ������
 * 
 * @author Ф����
 *
 *         2017��6��14������11:10:03
 * @desc
 */
//Encoder extends MessageToByteEncoder extends ChannelInboundHandlerAdapter extends  ChannelHandlerAdapter implements ChannelHandler
public class Encoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
		out.writeInt(message.getNameLength());
		System.err.println(message.getName());
		out.writeBytes(message.getName().getBytes());
	}
}