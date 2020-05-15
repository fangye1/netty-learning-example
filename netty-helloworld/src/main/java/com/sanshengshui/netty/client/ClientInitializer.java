package com.sanshengshui.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private static final StringDecoder DECODER = new StringDecoder();
    private static final StringEncoder ENCODER = new StringEncoder();

    private static final ClientHandler CLIENT_HANDLER = new ClientHandler();


    @Override
    public void initChannel(SocketChannel ch) {
        //ChannelPipline : 保存 ChannelHandler 的 List，用于处理或拦截 Channel 的入站事件和出站操作。
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // DECODER : IO 的 入站解码器
        pipeline.addLast(DECODER);
        // DECODER : IO 的 出站编码器
        pipeline.addLast(ENCODER);
        //添加自定义的 Channel 处理器 ClientHandler
        pipeline.addLast(CLIENT_HANDLER);
    }
}
