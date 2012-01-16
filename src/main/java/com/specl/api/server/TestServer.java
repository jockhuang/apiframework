package com.specl.api.server;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.specl.api.client.TestHandler;

public class TestServer {

    /**
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

        chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        chain.addLast("logger", new LoggingFilter());
        
        // Bind
        acceptor.setHandler(new TestHandler());
        acceptor.bind(new InetSocketAddress(1234));

        System.out.println("Listening on port " + 1234);
    }

}
