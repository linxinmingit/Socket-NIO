package cn.yr.netty.auth.authority3;
 
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author
 * @since
 */
//ChannelHandlerAdapter
public class ClientChannelHandler extends ChannelInboundHandlerAdapter {
	//@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("active-->>客户端通道已开启");
//		Student s = new Student("小红", "5班", 12);
//		ctx.writeAndFlush(s);

		//ctx.writeAndFlush(buildLoginRep());
		ctx.writeAndFlush("localhost:8080/NettyProject/query?username=admin&password=123&head=POST");
	}
 
	//@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ByteBuf bu = (ByteBuf) msg;
//		byte[] bytes = new byte[bu.readableBytes()];
//		bu.readBytes(bytes);
//		String str = new String(bytes);
//		System.out.println(str);


        System.out.println(1);
		NettyMessage message = (NettyMessage) msg;

		if (message.getUsername()!= "admin" && message.getPasswords() != "123" && message.getHead().equals("POST"))
		{
			byte loginRresult = message.getBody();
			if (loginRresult!= 0)
            {
                ctx.close();//握手失败，关闭连接；
            } else {
                //System.out.println("LoginIs ok ："+message);
                ctx.fireChannelRead(msg);
            }
		} else {
			ctx.fireChannelRead(msg);
		}
		System.out.println(msg);
	}

//	private NettyMessage buildLoginRep()
//	{
//		NettyMessage message = new NettyMessage();
//		message.setHead("GET");
//		message.setUsername("admin");
//		message.setPasswords("123");
//		message.setUrl("https://www.baidu.com/");
//		return message;
//	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端读取完成!");
	}

	//@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
 			cause.printStackTrace();
	}
}
