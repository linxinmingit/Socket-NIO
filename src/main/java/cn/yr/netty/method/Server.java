package cn.yr.netty.method;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author
 * @since
 */
public class Server {
	public static void main(String[] args) {
		// ������,��������netty ��netty5��ͬ��ʹ�������������
		ServerBootstrap bootstrap = new ServerBootstrap();
		// �½������̳߳�  boss�̼߳����˿ڣ�worker�̸߳������ݶ�д
		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();
		// ����niosocket����  ����NIO�����½�ServerSocketChannel��SocketChannel
		bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));
		// ���ùܵ��Ĺ���
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new StringDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				pipeline.addLast("helloHandler", new ServerHandler());  //���һ��Handler������ͻ��˵��¼���Handler��Ҫ�̳�ChannelHandler
				return pipeline;
			}
		});
		bootstrap.bind(new InetSocketAddress(10101));
		System.out.println("start!!!");
	}
}
