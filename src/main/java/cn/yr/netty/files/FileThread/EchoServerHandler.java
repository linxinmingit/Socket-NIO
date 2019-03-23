package cn.yr.netty.files.FileThread;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.*;

@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	private boolean first = true;
	private FileOutputStream fos;
	private BufferedOutputStream bufferedOutputStream;
	private static String prefix = "C:\\Users\\Administrator\\Desktop\\lv\\";
	private String OK = "ok";


    @Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buf = (ByteBuf) msg;
		
		byte[] bytes = new byte[buf.readableBytes()];  //readableBytes 可读的实际内容长度
		buf.readBytes(bytes);
		if (first) {
			String fileName = new String(bytes);
			System.out.println(" 文件名称 ：  "+fileName);
			first = false;
			File file = new File(prefix + fileName);
			
			if (!file.exists()) { //判断是否是一个文件，如果没有此文件就创建
				try { 
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				fos =  new FileOutputStream(file);
				bufferedOutputStream = new BufferedOutputStream(fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ctx.writeAndFlush(OK.getBytes());
			buf.release();
			return;
		}
		
    	try {
    		bufferedOutputStream.write(bytes, 0, bytes.length);
    		buf.release();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    	ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
