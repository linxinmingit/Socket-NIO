package cn.yr.netty.files.file;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.io.File;

public class FileUploadClient {
    public void connect(int port, String host, final FileUploadFile fileUploadFile) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                    ch.pipeline().addLast(new FileUploadClientHandler(fileUploadFile));
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        try {
            FileUploadFile uploadFile = new FileUploadFile();
            File file = new File("C:\\Users\\Administrator\\Desktop\\send\\eduwkspace.zip");
            String fileMd5 = file.getName();// 文件名
            uploadFile.setFile(file);
            uploadFile.setFile_md5(fileMd5);
            uploadFile.setStarPos(0);// 文件开始位置
            new FileUploadClient().connect(port, "127.0.0.1", uploadFile);


//            FileUploadFile uploadFile = new FileUploadFile();
//              String path = "C:\\Users\\Administrator\\Desktop\\send";
//              File file = new File(path);
//              File[] files = file.listFiles();
//            for (File file1 : files) {
//                //
//                if (file1.isFile())
//                {
//                    String fileMd5 = file1.getName();// 文件名
//                    uploadFile.setFile(file1);
//                    uploadFile.setFile_md5(fileMd5);
//                    uploadFile.setStarPos(0);// 文件开始位置
//                    new FileUploadClient().connect(port, "127.0.0.1", uploadFile);
//                } else {
//                    File[] file2 = file1.listFiles();
//                    for (File file3 : file2) {
//                        //
//                        String fileMd6 = file3.getName();// 文件名
//                        uploadFile.setFile(file3);
//                        uploadFile.setFile_md5(fileMd6);
//                        uploadFile.setStarPos(0);// 文件开始位置
//                        new FileUploadClient().connect(port, "127.0.0.1", uploadFile);
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
