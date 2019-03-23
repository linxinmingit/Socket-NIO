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
package cn.yr.netty.files.directory3.netty9.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.stream.ChunkedFile;

/**
 * Handler implementation for the echo client. It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
	private String context;

	public EchoClientHandler() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		String path = "C:\\Users\\Administrator\\Desktop\\send";
		File file =new File(path);
		File files[] = file.listFiles();
		for(int i=0;i<files.length;i++)
		{
			
			Message message = new Message();
			message.setNameLength(files[i].getName().getBytes().length);
			message.setName(files[i].getName());
			try {
				message.setContentLength(new FileInputStream(files[i]).available());
			} catch (Exception e) {
				e.printStackTrace();
			}
			ctx.writeAndFlush(message);
			
			
			RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile(files[i].getPath(), "r");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				ctx.writeAndFlush(new ChunkedFile(raf)).addListener(new ChannelFutureListener(){
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						//future.channel().close();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
//			try {
//				Thread.sleep(1 * 1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
