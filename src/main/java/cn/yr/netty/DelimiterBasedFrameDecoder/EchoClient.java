package cn.yr.netty.DelimiterBasedFrameDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;



import java.net.InetSocketAddress;

/**
 * Created by louyuting on 16/11/27.
 */
public class EchoClient {
    private final String host;
    private final int port;//����������˼����Ķ˿�
    /** ���캯���д������ **/
    public EchoClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    /** ���������� **/
    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        //����һ��client ��bootstrapʵ��
        Bootstrap clientBootstrap = new Bootstrap();

        try {
            clientBootstrap.group(group)
                    .channel(NioSocketChannel.class)//ָ��ʹ��һ��NIO����Channel
                    .remoteAddress(new InetSocketAddress(host, port))//����Զ�˷�������host�Ͷ˿�
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        //��channel��ChannelPipeline�м���EchoClientHandler�����
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));

                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new StringEncoder());
                            channel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = clientBootstrap.connect().sync();//���ӵ�Զ��,һֱ�ȵ��������
            f.channel().closeFuture().sync();//һֱ������channel�ر�
        } finally {
            group.shutdownGracefully().sync();//�ر�group,�ͷ����е���Դ
        }
    }


    /**
     * main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 8000).start();
    }

}
