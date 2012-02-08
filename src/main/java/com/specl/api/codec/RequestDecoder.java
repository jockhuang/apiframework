package com.specl.api.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class RequestDecoder extends CumulativeProtocolDecoder {

    @Override
    public boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        if (in.remaining() >= 6) {
            byte ab = in.get();
            byte cd = in.get();
            if (ab == new Integer(0xAB).byteValue() && cd == new Integer(0xCD).byteValue()) {
                int requestLength = in.getInt();
                String request = in.getString(requestLength, Charset.forName("UTF-8").newDecoder());
                int paramLength = in.getInt();
                String param = in.getString(paramLength, Charset.forName("UTF-8").newDecoder());
                Request message = new Request();
                message.setMethod(request);
                message.setParam(param);
                out.write(message);
                return true;
            }
        }
        return false;

    }
}
