package com.specl.api.codec.http;

import java.net.URI;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.statemachine.ConsumeToLinearWhitespaceDecodingState;
import org.apache.mina.filter.codec.statemachine.CrLfDecodingState;
import org.apache.mina.filter.codec.statemachine.DecodingState;
import org.apache.mina.filter.codec.statemachine.DecodingStateMachine;
import org.apache.mina.filter.codec.statemachine.LinearWhitespaceSkippingState;

/**
 * Decodes an HTTP request line - populating an associated <code>Request</code> This decoder expects the following
 * format:
 * 
 * <pre>
 * Request-Line = Method SP Request-URI SP HTTP-Version CRLF
 * </pre>
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596260 $, $Date: 2007-11-19 23:41:01 +1300 (Mon, 19 Nov 2007) $
 */
abstract class HttpRequestLineDecodingState extends DecodingStateMachine {

    private final CharsetDecoder asciiDecoder = HttpCodecUtils.US_ASCII_CHARSET.newDecoder();
    private final CharsetDecoder defaultDecoder = HttpCodecUtils.DEFAULT_CHARSET.newDecoder();

    @Override
    protected DecodingState init() throws Exception {
        return READ_METHOD;
    }

    @Override
    protected void destroy() throws Exception {
    }

    private final DecodingState READ_METHOD = new ConsumeToLinearWhitespaceDecodingState() {
        @Override
        protected DecodingState finishDecode(IoBuffer product, ProtocolDecoderOutput out) throws Exception {
            HttpMethod method = HttpMethod.valueOf(product.getString(asciiDecoder));

            if (method == null) {
                HttpCodecUtils.throwDecoderException("Bad method", HttpResponseStatus.NOT_IMPLEMENTED);
            }

            out.write(method);

            return AFTER_READ_METHOD;
        }
    };

    private final DecodingState AFTER_READ_METHOD = new LinearWhitespaceSkippingState() {
        @Override
        protected DecodingState finishDecode(int skippedBytes) throws Exception {
            return READ_REQUEST_URI;
        }
    };

    private final DecodingState READ_REQUEST_URI = new ConsumeToLinearWhitespaceDecodingState() {
        @Override
        protected DecodingState finishDecode(IoBuffer product, ProtocolDecoderOutput out) throws Exception {
            out.write(new URI(product.getString(defaultDecoder)));
            return AFTER_READ_REQUEST_URI;
        }
    };

    private final DecodingState AFTER_READ_REQUEST_URI = new LinearWhitespaceSkippingState() {
        @Override
        protected DecodingState finishDecode(int skippedBytes) throws Exception {
            return READ_PROTOCOL_VERSION;
        }
    };

    private final DecodingState READ_PROTOCOL_VERSION = new HttpVersionDecodingState() {
        @Override
        protected DecodingState finishDecode(HttpVersion version, ProtocolDecoderOutput out) throws Exception {
            out.write(version);
            return AFTER_READ_PROTOCOL_VERSION;
        }
    };

    private final DecodingState AFTER_READ_PROTOCOL_VERSION = new LinearWhitespaceSkippingState() {
        @Override
        protected DecodingState finishDecode(int skippedBytes) throws Exception {
            return FINISH;
        }
    };

    private final DecodingState FINISH = new CrLfDecodingState() {
        @Override
        protected DecodingState finishDecode(boolean foundCRLF, ProtocolDecoderOutput out) throws Exception {
            if (!foundCRLF) {
                HttpCodecUtils.throwDecoderException("Expected CR/LF at the end of the request line.");
            }

            return null;
        }
    };
}
