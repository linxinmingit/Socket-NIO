package cn.yr.netty.method;

import org.jboss.netty.channel.*;

public class ServerHandler extends SimpleChannelHandler {
	/** ������Ϣ*/
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		String s = (String) e.getMessage();
		System.out.println(s);
		//��д����
		ctx.getChannel().write("HelloWorld");
		super.messageReceived(ctx, e);
	}
	/** �����쳣*/
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.out.println("exceptionCaught");
		super.exceptionCaught(ctx, e);
	}
	/** ������*/
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelConnected");
		super.channelConnected(ctx, e);
	}
	/** �����������Ѿ��������ر�ͨ����ʱ��Żᴥ��  */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelDisconnected");
		super.channelDisconnected(ctx, e);
	}
	/** channel�رյ�ʱ�򴥷� */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		System.out.println("channelClosed");
		super.channelClosed(ctx, e);
	}
}
