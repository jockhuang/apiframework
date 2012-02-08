package com.specl.api.codec.http;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * TODO HttpProtocolCodecFactory.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 */
public class HttpCodecFactory implements ProtocolCodecFactory {

    public HttpCodecFactory() {
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        if (session.getService() instanceof IoAcceptor) {
            return new HttpResponseEncoder();
        } else {
            return new HttpRequestEncoder();
        }
    }

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        if (session.getService() instanceof IoAcceptor) {
            return new HttpRequestDecoder();
        } else {
            return new HttpResponseDecoder();
        }
    }
}
