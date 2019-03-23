package cn.yr.netty.ByteToMessageDecoder;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
 
/**
 *
 * @author
 * @since
 */
public class ServerHandler extends ChannelHandlerAdapter {
 
	//@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// ���ܿͻ��˵�����
		Integer body = (Integer) msg;
		System.out.println("Client :" + body.toString());
		// ����ˣ���д���ݸ��ͻ���
		// ֱ�ӻ�д���ε�����
		ctx.writeAndFlush(33);
 
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
 
	//@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
