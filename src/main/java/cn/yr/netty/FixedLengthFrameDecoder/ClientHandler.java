package cn.yr.netty.FixedLengthFrameDecoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//ChannelHandlerAdapter
public class ClientHandler extends ChannelInboundHandlerAdapter {
 	private static  final String MES = "Cumulative read to the message with a total length of LEN fixed length lovely";

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 客户端连接成功，给服务端发送数据
		//ctx.channel().writeAndFlush("aaaaabbbbb");
		// 增加空格，达到定长
		//ctx.channel().writeAndFlush("ccccccc  ");


		// ctx.writeAndFlush(MES);
		ctx.writeAndFlush(Unpooled.wrappedBuffer(MES.getBytes()));
		//cf.channel().writeAndFlush(Unpooled.wrappedBuffer("aaaaabbbbb".getBytes()));
		//cf.channel().writeAndFlush(Unpooled.copiedBuffer("ccccccc".getBytes()));

	}
 

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		StringBuilder response = new StringBuilder((String) msg);
		System.out.println("Client: " + response);

		System.out.println("length-->>"+((String) msg).length());
	}
 

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
 
}
