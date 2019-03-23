package cn.yr.netty.FixedLengthFrameDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
 
/**
 *
 * @author
 * @since
 */
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
					.handler(new MyChannelHandler());//
			// �첽���ӷ����� ͬ���ȴ����ӳɹ�
			ChannelFuture f = b.connect(host, port).sync();
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
	private class MyChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// ��Ӷ���������
			ch.pipeline().addLast(new StringEncoder());
			ch.pipeline().addLast(new FixedLengthFrameDecoder(5));
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new ClientHandler());
			// �ͻ��˵Ĵ�����
			//ch.pipeline().addLast(new ClientHandler());
		}
 
	}
 
	public static void main(String[] args) throws Exception {
		new Client().connect(9998, "127.0.0.1");
 
	}
 
}
