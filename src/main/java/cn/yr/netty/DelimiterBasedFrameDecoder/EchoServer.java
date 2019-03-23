package cn.yr.netty.DelimiterBasedFrameDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by louyuting on 16/11/27.
 */
public class EchoServer {
    private final int port;//����������˼����Ķ˿�
    /** ���캯���д������ **/
    public EchoServer(int port){
        this.port = port;
    }

    /** ���������� **/
    public void start() throws Exception{
        //�س���
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        //����һ��serverbootstrapʵ��
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)//ָ��ʹ��һ��NIO����Channel
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //��channel��ChannelPipeline�м���EchoServerHandler�����
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                             ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                             channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));

                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new StringEncoder());
                            channel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //�첽�İ󶨷�����,sync()һֱ�ȵ������.
            ChannelFuture future = serverBootstrap.bind(this.port).sync();
            System.err.println(EchoServer.class.getName()+" started and listen on '"+ future.channel().localAddress());
            future.channel().closeFuture().sync();//������channel��CloseFuture,������ǰ�߳�ֱ���رղ������
        } finally {
            boss.shutdownGracefully().sync();//�ر�group,�ͷ����е���Դ
            worker.shutdownGracefully().sync();//�ر�group,�ͷ����е���Դ
        }
    }


    /**
     * main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new EchoServer(8000).start();
    }

}
