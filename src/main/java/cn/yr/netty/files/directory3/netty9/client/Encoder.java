package cn.yr.netty.files.directory3.netty9.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *
 * @author
 * @since
 */ //Encoder extends MessageToByteEncoder extends ChannelInboundHandlerAdapter extends  ChannelHandlerAdapter implements ChannelHandler
public class Encoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
		out.writeInt(message.getNameLength());//���ֳ���
		out.writeBytes(message.getName().getBytes());//��������
		out.writeInt(message.getContentLength());//�ļ����ݳ���
	}
}