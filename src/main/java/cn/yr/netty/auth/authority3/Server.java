package cn.yr.netty.auth.authority3;
 

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author
 * @since
 */
public class Server {
	public static void main(String[] args) {
		try {
			new Server().bind(8888);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public void bind(final int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.TCP_NODELAY, true);
			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
 
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
							ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
							ch.pipeline().addLast(new ServerChannelHandler());
						}
 
					});
			ChannelFuture f = b.bind(port).sync();
			System.out.println("服务端已启动");
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
 
}
