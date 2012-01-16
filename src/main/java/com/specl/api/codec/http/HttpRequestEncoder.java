package com.specl.api.codec.http;

import java.net.URI;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * TODO HttpRequestEncoder.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 */
public class HttpRequestEncoder extends ProtocolEncoderAdapter {

    private final CharsetEncoder asciiEncoder = HttpCodecUtils.US_ASCII_CHARSET.newEncoder();

    public HttpRequestEncoder() {
    }

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {

        HttpRequest req = (HttpRequest) message;
        if (req instanceof MutableHttpRequest) {
            ((MutableHttpRequest) req).normalize();
        }

        asciiEncoder.reset();
        IoBuffer buf = IoBuffer.allocate(256).setAutoExpand(true);

        // Write request line.
        buf.putString(req.getMethod().toString(), asciiEncoder);
        buf.put((byte) ' ');

        URI uri = req.getRequestUri();
        buf.putString(uri.getPath(), asciiEncoder);
        String query = uri.getQuery();
        if (query != null && query.length() > 0) {
            buf.put((byte) '?');
            buf.putString(query, asciiEncoder);
        }
        buf.putString(" HTTP/1.1", asciiEncoder);
        HttpCodecUtils.appendCRLF(buf);

        HttpCodecUtils.encodeHeaders(req, buf, asciiEncoder);
        HttpCodecUtils.encodeBody(req, buf);

        buf.flip();
        out.write(buf);
    }
}
