package cn.yr.netty.FixedLengthFrameDecoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author
 * @since
 */
//ChannelHandlerAdapter
public class ServerHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client Active===========");
		//super.channelActive(ctx);
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String request = (String) msg;
		System.out.println("Server :" + msg);
		String response = request;
		//ctx.writeAndFlush(response);
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) throws Exception {
		t.printStackTrace();
		ctx.close();
	}
 
}
