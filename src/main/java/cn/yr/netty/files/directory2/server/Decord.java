package cn.yr.netty.files.directory2.server;

import java.util.List;

import cn.yr.netty.files.directory2.client.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class Decord extends ByteToMessageDecoder {
	int HEAD_LENGTH = 4;
	public static boolean mark = true;

	//不管是头信息,还是我们普通的流信息,所有信息都会进入到这里
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//先接收头,如果头信息接收完成了.设置为false
		if(mark)
		{
			System.out.println("-=----------------------");
			try {
	
				int data = in.readableBytes();
				if (data < HEAD_LENGTH) { // 这个HEAD_LENGTH是我们用于表示头长度的字节数。
					System.err.println("--------------------1");
					return;
				}
			
				in.markReaderIndex(); // 我们标记一下当前的readIndex的位置					// 的readInt()方法会让他的readIndex增加4
				int dataLength = in.readInt(); // 读取传送过来的消息的长度。ByteBuf
				if (dataLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
					ctx.close();
				}
				
				 if (in.readableBytes() < dataLength) { //读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex. 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
		            in.resetReaderIndex();//返回以前标记,到时再重新读取.还会与下次的接收值累加
		            return;
		        }
				
				 byte[] body = new byte[dataLength]; 
				 in.readBytes(body);
				 
				 Message message = new Message();
				 message.setName(new String(body));
				 System.out.println("name ===================" + message.getName());
				 mark = false;
				 out.add(message);
	
			} catch (Exception e) {
				System.err.println("抛出了异常---Decord");
			}
		}
		else
		{
    		//buf.skipBytes(buf.readableBytes());
    		byte[] bytes = new byte[in.readableBytes()];
    		in.readBytes(bytes);
    		out.add(bytes);
		}
	}
}