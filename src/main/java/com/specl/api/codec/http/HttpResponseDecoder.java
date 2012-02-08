package com.specl.api.codec.http;

import java.util.List;

import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.statemachine.DecodingState;
import org.apache.mina.filter.codec.statemachine.DecodingStateProtocolDecoder;

/**
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 */
public class HttpResponseDecoder extends DecodingStateProtocolDecoder {
    public HttpResponseDecoder() {
        super(new HttpResponseDecodingState() {
            @Override
            protected DecodingState finishDecode(List<Object> childProducts, ProtocolDecoderOutput out)
                    throws Exception {
                for (Object m : childProducts) {
                    out.write(m);
                }
                return null;
            }
        });
    }
}
