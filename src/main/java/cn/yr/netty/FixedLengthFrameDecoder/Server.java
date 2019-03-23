package cn.yr.netty.FixedLengthFrameDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
 
public class Server {
	public void bind(int port) throws Exception {
		// �������߳��� ���������¼��Ĵ��� һ�����ڷ��������տͻ��˵�����
		// ��һ���߳������ڴ���SocketChannel�������д
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// NIO�������˵ĸ��������� ���ͷ����������Ѷ�
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)// ����NIO��serverSocketChannel
					.option(ChannelOption.SO_BACKLOG, 1024)// ����TCP����
					.option(ChannelOption.SO_BACKLOG, 1024) // ����tcp������
					.option(ChannelOption.SO_SNDBUF, 32 * 1024) // ���÷��ͻ����С
					.option(ChannelOption.SO_RCVBUF, 32 * 1024) // ���ǽ��ջ����С
					.option(ChannelOption.SO_KEEPALIVE, true) // ��������
					.childHandler(new ChildChannelHandler());// ����I/O�¼��Ĵ�����
																// ��������IO�¼�
 
			// ������������ �󶨼����˿� ͬ���ȴ��ɹ� ��Ҫ�����첽������֪ͨ�ص� �ص������õ�ChildChannelHandler
			ChannelFuture f = serverBootstrap.bind(port).sync();
			System.out.println("Server����");
			// �ȴ�����˼����˿ڹر�
			f.channel().closeFuture().sync();
 
		} finally {
			// �����˳� �ͷ��̳߳���Դ
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			System.out.println("���������ŵ��ͷ����߳���Դ...");
		}
 
	}
 
	/**
	 * �����¼�������
	 */
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new StringEncoder());
			// ���ö����ַ�������
			ch.pipeline().addLast(new FixedLengthFrameDecoder(5));
			// �����ַ�����ʽ�Ľ���
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new ServerHandler());
		}
	}
 
	public static void main(String[] args) throws Exception {
		int port = 9998;
		new Server().bind(port);
	}
 
}
