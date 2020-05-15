package com.sanshengshui.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class Client {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //Bootstrap :一个 Netty 应用通常由一个 Bootstrap 开始，主要作用是配置整个 Netty 程序，串联各个组件.
            // Netty 中 Bootstrap 类是客户端程序的启动引导类，ServerBootstrap 是服务端启动引导类。
            Bootstrap b = new Bootstrap();
            //EventLoopGroup ：主要管理 eventLoop 的生命周期，可以理解为一个线程池，内部维护了一组线程，
            // 每个线程(NioEventLoop)负责处理多个 Channel 上的事件，而一个 Channel 只对应于一个线程
            b.group(group)
                    .channel(NioSocketChannel.class)
                    //配置 处理 Channel【 I/O 事件或拦截 I/O 】的处理器
                    .handler(new ClientInitializer());
            //Channel : Netty 网络通信的组件，能够用于执行网络 I/O 操作
            Channel ch = b.connect("127.0.0.1", 8888).sync().channel();


            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                // 给服务器发送信息
                lastWriteFuture = ch.writeAndFlush(line + "\r\n");
                // If user typed the 'bye' command, wait until the server closes
                // the connection.
                if ("bye".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }
            }

            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
