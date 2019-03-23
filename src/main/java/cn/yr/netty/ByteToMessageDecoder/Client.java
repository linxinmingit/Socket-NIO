package cn.yr.netty.ByteToMessageDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
 
public class Client {
	/**
	 * ���ӷ�����
	 * 
	 * @param port
	 * @param host
	 * @throws Exception
	 */
	public void connect(int port, String host) throws Exception {
		// ���ÿͻ���NIO�߳���
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			// �ͻ��˸��������� �Կͻ�������
			Bootstrap b = new Bootstrap();
			b.group(group)//
					.channel(NioSocketChannel.class)//
					.option(ChannelOption.TCP_NODELAY, true)//
					.handler(new ClientChannelHandler());//
			// �첽���ӷ����� ͬ���ȴ����ӳɹ�
			ChannelFuture f = b.connect(host, port).sync();
 
			// ������Ϣ
			Thread.sleep(1000);
			f.channel().writeAndFlush(777);
			f.channel().writeAndFlush(666);
			Thread.sleep(2000);
			f.channel().writeAndFlush(888);
 
			// �ȴ����ӹر�
			f.channel().closeFuture().sync();
 
		} finally {
			group.shutdownGracefully();
			System.out.println("�ͻ������ŵ��ͷ����߳���Դ...");
		}
 
	}
 
	/**
	 * �����¼�������
	 */
	private class ClientChannelHandler extends
			ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// �����Զ���ı������ͽ�����
			ch.pipeline().addLast(new IntegerToByteEncoder());
			ch.pipeline().addLast(new ByteToIntegerDecoder());
			// �ͻ��˵Ĵ�����
			ch.pipeline().addLast(new ClientHandler());
		}
 
	}
 
	public static void main(String[] args) throws Exception {
		new Client().connect(9998, "127.0.0.1");
 
	}
}
