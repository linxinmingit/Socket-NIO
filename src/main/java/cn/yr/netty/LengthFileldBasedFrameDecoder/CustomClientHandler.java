package cn.yr.netty.LengthFileldBasedFrameDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;  
  
public class CustomClientHandler extends ChannelInboundHandlerAdapter {  
      
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端通道准备完成!");
        for (int i = 0; i < 100; i++) {
            //CustomMsg customMsg = new CustomMsg((byte)0xAB, (byte)0xCD, "Hello,Netty".length(), "Hello,Netty");
            CustomMsg customMsg = new CustomMsg(100,"The topic of Netty");
            Thread.sleep(50);
            ctx.writeAndFlush(customMsg);
            ctx.flush();
        }
//        //CustomMsg customMsg = new CustomMsg((byte)0xAB, (byte)0xCD, "Hello,Netty".length(), "Hello,Netty");
//        CustomMsg customMsg = new CustomMsg(100,"The topic of Netty");
//        ctx.writeAndFlush(customMsg);
//        ctx.flush();
    }  
  
}  