package cn.yr.netty.ByteToMessageDecoder;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
 
public class ClientHandler extends ChannelHandlerAdapter {
 
	//@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		try {
			Integer body = (Integer) msg;
			System.out.println("Client :" + body.toString());
 
			// ֻ�Ƕ����ݣ�û��д���ݵĻ�
			// ��Ҫ�Լ��ֶ����ͷŵ���Ϣ
 
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
 
}
