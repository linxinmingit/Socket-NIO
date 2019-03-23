package cn.yr.netty.files.FileThread;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 *
 * @author
 * @since
 */
public class Test implements Runnable {
	static final boolean SSL = System.getProperty("ssl") != null;
	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
	static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));
	
	public String fileName;
	public String filePath;
	
	public Test(String fileName,String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public void run() {
		try {
			final SslContext sslCtx;
			if (SSL) {
				sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);
			} else {
				sslCtx = null;
			}

			EventLoopGroup group = new NioEventLoopGroup();
			try {
				Bootstrap b = new Bootstrap();
				b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
						.handler(new ChannelInitializer<SocketChannel>() {
							@Override
							public void initChannel(SocketChannel ch) throws Exception {
								ChannelPipeline p = ch.pipeline();
								if (sslCtx != null) {
									p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
								}
								p.addLast(new ByteArrayEncoder(), new ChunkedWriteHandler(), new EchoClientHandler1(fileName,filePath));
							}
						});

				ChannelFuture f = b.connect(HOST, PORT).sync();
				System.out.println(Thread.currentThread().getName()+" 正在运行......");

				// Wait until the connection is closed.
				f.channel().closeFuture().sync();
			} finally {
				// Shut down the event loop to terminate all threads.
				group.shutdownGracefully();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}