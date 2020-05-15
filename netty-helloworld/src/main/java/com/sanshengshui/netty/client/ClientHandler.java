package com.sanshengshui.netty.client;


import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class ClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 接受server返回的信息
     *
     * @param ctx 保存 Channel 相关的所有上下文信息，同时关联一个 ChannelHandler 对象。
     * @param msg 信息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
