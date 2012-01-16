package com.specl.api.codec.http;

import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.statemachine.ConsumeToCrLfDecodingState;
import org.apache.mina.filter.codec.statemachine.ConsumeToLinearWhitespaceDecodingState;
import org.apache.mina.filter.codec.statemachine.DecodingState;
import org.apache.mina.filter.codec.statemachine.DecodingStateMachine;
import org.apache.mina.filter.codec.statemachine.LinearWhitespaceSkippingState;

/**
 * Decodes a HTTP response line.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 612019 $, $Date: 2008-01-15 18:24:44 +1300 (Tue, 15 Jan 2008) $
 */
abstract class HttpResponseLineDecodingState extends DecodingStateMachine {

    private final CharsetDecoder asciiDecoder = HttpCodecUtils.US_ASCII_CHARSET.newDecoder();
    private final CharsetDecoder defaultDecoder = HttpCodecUtils.DEFAULT_CHARSET.newDecoder();

    @Override
    protected DecodingState init() throws Exception {
        return READ_PROTOCOL_VERSION;
    }

    @Override
    protected void destroy() throws Exception {
    }

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
            return READ_STATUS_CODE;
        }
    };

    private final DecodingState READ_STATUS_CODE = new ConsumeToLinearWhitespaceDecodingState() {
        @Override
        protected DecodingState finishDecode(IoBuffer product, ProtocolDecoderOutput out) throws Exception {
            String statusCode = product.getString(asciiDecoder);
            try {
                out.write(Integer.parseInt(statusCode));
            } catch (NumberFormatException e) {
                HttpCodecUtils.throwDecoderException("Bad status code");
            }
            return READ_REASON_PHRASE;
        }
    };

    private final DecodingState READ_REASON_PHRASE = new ConsumeToCrLfDecodingState() {
        @Override
        protected DecodingState finishDecode(IoBuffer product, ProtocolDecoderOutput out) throws Exception {
            out.write(product.getString(defaultDecoder).trim());
            return null;
        }
    };
}
