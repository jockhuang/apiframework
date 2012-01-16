package com.specl.api.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class RequestCodecFactory implements ProtocolCodecFactory {

    private ProtocolDecoder decoder;

    private ProtocolEncoder encoder;

    public RequestCodecFactory() {
        decoder = new RequestDecoder();
        encoder = new RequestEncoder();
    }

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {

        return decoder;
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {

        return encoder;
    }

}
