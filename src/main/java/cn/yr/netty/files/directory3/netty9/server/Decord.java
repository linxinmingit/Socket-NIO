package cn.yr.netty.files.directory3.netty9.server;

import java.util.List;

import cn.yr.netty.files.directory3.netty9.client.Message;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class Decord extends ByteToMessageDecoder {
	int HEAD_LENGTH = 4;
	private int contentSumLength = 0;
	private int contentLength = 0;
	public static boolean mark = true;

	//������ͷ��Ϣ,����������ͨ������Ϣ,������Ϣ������뵽����
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//������һ���ļ�������,�ڶ��ļ���ͷ��Ϣ,�ڶ����ļ�������,�����ļ���ͷ��Ϣ,�������ļ�������
		//�Ƚ���ͷ,���ͷ��Ϣ���������.����Ϊfalse
		if(mark)
		{
			try {
	
				int data = in.readableBytes();
				if (data < HEAD_LENGTH) { // ���HEAD_LENGTH���������ڱ�ʾͷ���ȵ��ֽ�����
					return;
				}
			
				in.markReaderIndex(); // ���Ǳ��һ�µ�ǰ��readIndex��λ��					// ��readInt()������������readIndex����4
				int dataLength = in.readInt(); // ��ȡ���͹�������Ϣ�ĳ��ȡ�ByteBuf
				if (dataLength < 0) { // ���Ƕ�������Ϣ�峤��Ϊ0�����ǲ�Ӧ�ó��ֵ���������������������ر����ӡ�
					ctx.close();
				}
				
				 if (in.readableBytes() < dataLength) { //��������Ϣ�峤�����С�����Ǵ��͹�������Ϣ���ȣ���resetReaderIndex. ������markReaderIndexʹ�õġ���readIndex���õ�mark�ĵط�
		            in.resetReaderIndex();//������ǰ���,��ʱ�����¶�ȡ.�������´εĽ���ֵ�ۼ�
		            return;
		        }
				
				 byte[] body = new byte[dataLength]; 
				 in.readBytes(body);
				 
				 Message message = new Message();
				 message.setName(new String(body));
				
				 
				 int contentLength = in.readInt();
				 message.setContentLength(contentLength);
				 
				 contentSumLength = contentLength;
				 mark = false;
				 out.add(message);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			//��������ܳ��ȼ�ȥ�ۼӵĳ���,���С�ڿɶ�����(�ɶ�����������һ�������������ļ�.�������ʱ��,ֻ����ǰ����ļ��Լ��ĳ���)
			if (in.readableBytes() > contentSumLength - contentLength)
			{
	    		//buf.skipBytes(buf.readableBytes());
	    		byte[] bytes = new byte[contentSumLength - contentLength];
	    		in.readBytes(bytes);
	    		contentSumLength = 0;
	    		contentLength = 0;
	    		out.add(bytes);
			} else //����ļ����ܳ���,�������ǿɶ����ۼӵĳ���
			{
				
				byte[] bytes = new byte[in.readableBytes()];
				contentLength = contentLength + bytes.length;
	    		in.readBytes(bytes);
	    		if (contentLength == contentSumLength)
	    		{
	    			contentLength = 0;
	    			contentSumLength = 0;
					System.out.println("�ļ���ȡ���!");
	    		}
	    		out.add(bytes);
			}
		}
	}
}