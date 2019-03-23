package cn.yr.netty.LengthFileldBasedFrameDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;  
  
public class CustomServerHandler extends SimpleChannelInboundHandler<Object> {  
    private  Integer count = 1;
    @Override  
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端通道完成!");
        if (msg instanceof CustomMsg) {
            CustomMsg customMsg = (CustomMsg)msg;  
            System.out.println("Client->Server:"+ctx.channel().remoteAddress()+" send "+customMsg.toString()+ count++);
            ctx.writeAndFlush(customMsg);
            //ctx.write(customMsg);
            ctx.flush();
        }  
          
    }  
  
}  