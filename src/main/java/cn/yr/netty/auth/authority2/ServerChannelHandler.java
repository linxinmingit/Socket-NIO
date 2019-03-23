package cn.yr.netty.auth.authority2;
 

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author
 * @since
 */
                                         //ChannelHandlerAdapter
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {

	private Map<String,Boolean> nodeCheck=new ConcurrentHashMap<String,Boolean>();
   //分别定义了重复登录保护和IP认证的白名单列表，主要用于提升握手的可靠性
	private String[] whitekList ={"127.0.0.1","192.168.1.63"};


	//@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("active-->>服务端通道已开启");
		//ctx.writeAndFlush(buildResponse((byte) -1));
	}
 
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(111);

		//System.out.println(msg);
		NettyMessage message = (NettyMessage) msg;
		/**
		 * 用于接入认证，首先根据客户端的原地址(/127.0.0.1:12088)进行重复登录判断
         * 如果客户端已经登录成功，拒绝重复登录，以防止由于客户端重复登录导致的句柄泄漏
		 */
		if (message.getUsername()!= "admin" && message.getPasswords() != "123" && message.getHead().equals("GET"))
		{
			String nodeIndex = ctx.channel().remoteAddress().toString();
			NettyMessage loginResp = null;

			if (nodeCheck.containsKey(nodeIndex))
			{
				loginResp =  buildResponse((byte) -1);
			} else {
				InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
				String ip = address.getAddress().getHostAddress();
				boolean isOk = false;
				for (String WIP:whitekList) {
					//
					if (WIP.equals(ip))
					{
						isOk = true;
						break;
					}
				}
				loginResp = isOk?buildResponse((byte) 0):buildResponse((byte) -1);
				if (isOk)
				{
					nodeCheck.put(nodeIndex,true);
				}
			}
			System.out.println("Thelogin responseis:" + loginResp + "  body[" + loginResp.getBody() + "]");
			ctx.writeAndFlush(loginResp);
		} else {
			ctx.fireChannelRead(msg);
		}
	}

//	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println(111);
//
//		//System.out.println(msg);
//		NettyMessage message = (NettyMessage) msg;
//		/**
//		 * 用于接入认证，首先根据客户端的原地址(/127.0.0.1:12088)进行重复登录判断
//		 * 如果客户端已经登录成功，拒绝重复登录，以防止由于客户端重复登录导致的句柄泄漏
//		 */
//		if (message.getUsername()!= "admin" && message.getPasswords() != "123")
//		{
//			String nodeIndex = ctx.channel().remoteAddress().toString();
//			NettyMessage loginResp = null;
//
//			if (nodeCheck.containsKey(nodeIndex))
//			{
//				loginResp =  buildResponse((byte) -1);
//			} else {
//				InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
//				String ip = address.getAddress().getHostAddress();
//				boolean isOk = false;
//				for (String WIP:whitekList) {
//					//
//					if (WIP.equals(ip))
//					{
//						isOk = true;
//						break;
//					}
//				}
//				loginResp = isOk?buildResponse((byte) 0):buildResponse((byte) -1);
//				if (isOk)
//				{
//					nodeCheck.put(nodeIndex,true);
//				}
//			}
//			System.out.println("Thelogin responseis:" + loginResp + "body[" + loginResp.getBody() + "]");
//			ctx.writeAndFlush(loginResp);
//		} else {
//			ctx.fireChannelRead(msg);
//		}
//	}

	/**
	 * 绑定响应数据
	 * @param result
	 * @return
	 */
    private NettyMessage buildResponse(byte result)
	{
		NettyMessage message = new NettyMessage();
		message.setHead("POST");
		message.setUsername("admin");
		message.setPasswords("123");
		message.setUrl("https://www.jianshu.com/p/b9f3f6a16911");
		return message;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端读取完成!");
	}

	//@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
 
}
