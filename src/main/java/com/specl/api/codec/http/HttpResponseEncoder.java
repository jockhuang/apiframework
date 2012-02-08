package com.specl.api.codec.http;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596271 $, $Date: 2007-11-20 00:19:45 +1300 (Tue, 20 Nov 2007) $
 */
public class HttpResponseEncoder implements ProtocolEncoder {

    private final CharsetEncoder asciiEncoder = HttpCodecUtils.US_ASCII_CHARSET.newEncoder();

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        asciiEncoder.reset();
        HttpResponse response = (HttpResponse) message;
        IoBuffer buffer = IoBuffer.allocate(512);
        buffer.setAutoExpand(true);

        encodeStatusLine(response, buffer);
        HttpCodecUtils.encodeHeaders(response, buffer, asciiEncoder);
        HttpCodecUtils.encodeBody(response, buffer);

        buffer.flip();
        out.write(buffer);
    }

    public void dispose(IoSession session) throws Exception {
    }

    /**
     * Encodes the status line of a <code>Response</code> to a specified buffer. The status line takes the form:<br/>
     * 
     * <pre>
     *   HTTP-Version SP Status-Code SP Reason-Phrase CRLF
     * </pre>
     * 
     * @param response The response
     * @param buffer The buffer
     * @throws CharacterCodingException
     */
    private void encodeStatusLine(HttpResponse response, IoBuffer buffer) throws CharacterCodingException {
        // Write protocol version.
        buffer.putString(response.getProtocolVersion().toString(), asciiEncoder);
        buffer.put((byte) ' ');

        // Write status code.
        HttpResponseStatus status = response.getStatus();
        // TODO: Cached buffers for response codes / descriptions?
        HttpCodecUtils.appendString(buffer, String.valueOf(status.getCode()));
        buffer.put((byte) ' ');

        // Write reason phrase.
        HttpCodecUtils.appendString(buffer, response.getStatusReasonPhrase());
        HttpCodecUtils.appendCRLF(buffer);
    }
}
