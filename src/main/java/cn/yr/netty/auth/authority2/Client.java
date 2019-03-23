package cn.yr.netty.auth.authority2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
public class Client {
	public static void main(String[] args) throws Exception {
		new Client().connect("127.0.0.1", 8888);
	}
 
	public void connect(String host, int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
 
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
					ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
					ch.pipeline().addLast(new ClientChannelHandler());
				}
			});
			ChannelFuture f = b.connect(host, port);
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
 
}
