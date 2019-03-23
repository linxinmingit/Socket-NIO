/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package cn.yr.netty.files.directory3.netty9.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.yr.netty.files.directory3.netty9.client.Message;


import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	private boolean first = true;
	private FileOutputStream fos;
	private BufferedOutputStream bufferedOutputStream;
	private static String prefix = "C:\\Users\\Administrator\\Desktop\\lv\\";
	private static String subfix = "";
	
	private String OK = "ok";
	private String ERR = "err";
	private int contentLength = 0;
	private int contentSumLength = 0;
	private int mark = 0;

    @Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		/*ByteBuf buf = (ByteBuf) msg;
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);*/
		if (msg instanceof Message) {//
			
			//String fileName = new String(bytes);
			//System.out.println(fileName);
			  Message message = (Message) msg;
			  
			  contentSumLength = message.getContentLength();
			  
			File file = new File(prefix + message.getName());
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				fos =  new FileOutputStream(file);
				bufferedOutputStream = new BufferedOutputStream(fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			//ReferenceCountUtil.release(msg);
			ctx.writeAndFlush(OK.getBytes());
			return;
		}
//		System.out.println(2);
    	try {
    		byte[] bytes= (byte[]) msg;
    		contentLength = contentLength + bytes.length;
    		bufferedOutputStream.write(bytes, 0, bytes.length);
    		bufferedOutputStream.flush();
    		//100  10 10 10 10 10 10 10 10 10 10
    		if(contentLength == contentSumLength)
    		{
    			contentLength = 0 ;
    			contentSumLength = 0;
    			bufferedOutputStream.close();
    			Decord.mark = true;
    		}
    		//buf.release();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    	ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
