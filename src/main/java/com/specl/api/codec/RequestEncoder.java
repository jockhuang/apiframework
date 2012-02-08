package com.specl.api.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class RequestEncoder implements ProtocolEncoder {

    public void dispose(IoSession session) throws Exception {

    }

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        Request myRequest = (Request) message;

        IoBuffer buffer = IoBuffer.allocate(2 + 8 + myRequest.getMethodLength() , false);

        buffer.put(new Integer(0xAB).byteValue());
        buffer.put(new Integer(0xCD).byteValue());
        buffer.putInt(myRequest.getMethodLength());
        buffer.putString(myRequest.getMethod(), Charset.forName("UTF-8").newEncoder());
        //buffer.putInt(myRequest.getParamLength());
        //buffer.putString(myRequest.getParam(), Charset.forName("UTF-8").newEncoder());

        buffer.flip();

        out.write(buffer);
    }

}
