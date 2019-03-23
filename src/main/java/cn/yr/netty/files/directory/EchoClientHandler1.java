package cn.yr.netty.files.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

public class EchoClientHandler1 extends ChannelInboundHandlerAdapter {
	private String fileName;  //文件名字
	private String filePath;  //文件路径
	
	public EchoClientHandler1(String fileName,String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(fileName.getBytes(CharsetUtil.UTF_8));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		RandomAccessFile raf = null;
		try {
			System.out.println(" 文件的名称：  " + fileName);
			raf = new RandomAccessFile(filePath, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ctx.writeAndFlush(new ChunkedFile(raf)).addListener(new ChannelFutureListener(){
				public void operationComplete(ChannelFuture future) throws Exception {
					future.channel().close();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
