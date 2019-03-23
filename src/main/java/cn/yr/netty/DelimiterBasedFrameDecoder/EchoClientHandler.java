package cn.yr.netty.DelimiterBasedFrameDecoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * Created by louyuting on 16/12/1.
 *
 */
//SimpleChannelInboundHandler<String>
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private int counter=0;

      private static final String REQ = "LOUYYUTING netty. $_";
   // private static final String REQ = "client information==";

    /**
     * 当收到连接成功的通知,发送一条消息.
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 100; i++) {
            //
            ctx.writeAndFlush( Unpooled.copiedBuffer(REQ.getBytes()) );
        }
        //ctx.writeAndFlush( Unpooled.copiedBuffer(REQ.getBytes()) );
    }

    /**
     * 每当收到数据时这个方法会被调用.打印收到的消息日志
     * @param
     * @param msg
     * @throws Exception
     */
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
//        System.out.println("client received: " + "counter:" + (++counter) + "  msg:"+msg);
//    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client received: " + "counter:< " + (++counter) + "  >msg:"+msg);
    }

    /**
     * 异常发生时,记录错误日志,关闭channel
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//打印堆栈的错误日志
        ctx.close();
    }
}
