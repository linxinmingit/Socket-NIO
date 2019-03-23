package cn.yr.netty.DelimiterBasedFrameDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * Created by louyuting on 16/11/27.
 * ����������ͻ��˴�������......
 *
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    private int counter=0;
    /**
     * ÿ���յ���Ϣ��ʱ�򱻵���;
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String)msg;

        System.out.println("this is:< "+ (++counter) +" >time." + " Server received: " + body);
        body = body + "$_";
        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(echo);
    }
    /**
     * �ڶ������쳣���׳�ʱ������
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//��ӡ�쳣�Ķ�ջ������Ϣ
        ctx.close();//�ر����channel
    }
}
