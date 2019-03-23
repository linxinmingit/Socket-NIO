package cn.yr.netty.LineBasedFrameDecoder;

import cn.yr.netty.time.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
 
import java.net.InetSocketAddress;
 
public class TimeServer {
    private final static int PORT = 8080;
 
    public static void main(String[] args) {
        start();
    }
 
    private static void start() {
        //final TimeServerHandler serverHandler = new TimeServerHandler();
        // ����EventLoopGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // ����EventLoopGroup
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                //ָ����ʹ�õ�NIO����Channel
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                //ʹ��ָ���Ķ˿������׽��ֵ�ַ
                .localAddress(new InetSocketAddress(PORT))
                // ���һ��EchoServerHandler��Channle��ChannelPipeline
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //EchoServerHandler����עΪ@shareable,�������ǿ�������ʹ��ͬ���İ���
                        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new TimeServerHandler());
                    }
                });
 
        try {
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
