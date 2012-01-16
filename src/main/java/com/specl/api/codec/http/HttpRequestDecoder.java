package com.specl.api.codec.http;

import java.util.List;

import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.statemachine.DecodingState;
import org.apache.mina.filter.codec.statemachine.DecodingStateProtocolDecoder;

/**
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 601996 $, $Date: 2007-12-07 18:06:18 +1300 (Fri, 07 Dec 2007) $
 */
public class HttpRequestDecoder extends DecodingStateProtocolDecoder {
    public HttpRequestDecoder() {
        super(new HttpRequestDecodingState() {
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
