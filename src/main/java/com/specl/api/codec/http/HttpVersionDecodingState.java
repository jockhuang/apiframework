package com.specl.api.codec.http;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderException;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.statemachine.ConsumeToDynamicTerminatorDecodingState;
import org.apache.mina.filter.codec.statemachine.DecodingState;

/**
 * Decodes HTTP version.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 602839 $, $Date: 2007-12-10 23:10:19 +1300 (Mon, 10 Dec 2007) $
 */
abstract class HttpVersionDecodingState implements DecodingState {

    private final CharsetDecoder asciiDecoder = HttpCodecUtils.US_ASCII_CHARSET.newDecoder();

    private final DecodingState READ_PROTOCOL_VERSION = new ConsumeToDynamicTerminatorDecodingState() {
        @Override
        protected DecodingState finishDecode(IoBuffer product, ProtocolDecoderOutput out) throws Exception {
            String versionStr = null;
            HttpVersion version = null;
            try {
                versionStr = product.getString(asciiDecoder);
                version = HttpVersion.valueOf(versionStr);
            } catch (CharacterCodingException e) {
                // Will take care down the
            }

            if (version == null) {
                if (versionStr != null) {
                    versionStr = product.getHexDump();
                }

                HttpCodecUtils.throwDecoderException("Unsupported HTTP version: " + versionStr,
                        HttpResponseStatus.HTTP_VERSION_NOT_SUPPORTED);
            }

            return HttpVersionDecodingState.this.finishDecode(version, out);
        }

        @Override
        protected boolean isTerminator(byte b) {
            return Character.isWhitespace(b);
        }
    };

    public DecodingState decode(IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        DecodingState nextState = READ_PROTOCOL_VERSION.decode(in, out);
        if (nextState == READ_PROTOCOL_VERSION) {
            return this;
        } else {
            return nextState;
        }
    }

    public DecodingState finishDecode(ProtocolDecoderOutput out) throws Exception {
        throw new ProtocolDecoderException("Unexpected end of session while waiting for a HTTP version field.");
    }

    protected abstract DecodingState finishDecode(HttpVersion version, ProtocolDecoderOutput out) throws Exception;
}
