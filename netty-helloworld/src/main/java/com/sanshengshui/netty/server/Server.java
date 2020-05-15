package com.sanshengshui.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author 穆书伟
 * @date 2018年9月18号
 * @description 服务端启动程序
 */
public final class Server {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //ServerBootstrap 是服务端启动引导类,主要作用是配置整个 Netty 程序，串联各个组件
            ServerBootstrap b = new ServerBootstrap();
            //boosGroup 用于 Accetpt client连接建立事件并分发请求
            //workerGroup workerGroup 用于处理 I/O 读写事件和业务逻辑
            b.group(bossGroup, workerGroup)
                    //Channel : Netty 网络通信的组件，能够用于执行网络 I/O 操作
                    .channel(NioServerSocketChannel.class)
                    //打印日志
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 配置入站、出站事件handler
                    .childHandler(new ServerInitializer());
            ChannelFuture f = b.bind(8888);
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
