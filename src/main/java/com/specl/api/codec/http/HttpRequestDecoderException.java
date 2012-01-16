package com.specl.api.codec.http;

import org.apache.mina.filter.codec.ProtocolDecoderException;

/**
 * An exception thrown by HTTP decoders.
 * 
 * This exception enables decoders which are capable of determining the type of failure to specify the response which is
 * ultimately returned to the client.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596189 $, $Date: 2007-11-19 16:51:38 +1300 (Mon, 19 Nov 2007) $
 */
public class HttpRequestDecoderException extends ProtocolDecoderException {

    private static final long serialVersionUID = 3256999969109063480L;

    private HttpResponseStatus responseStatus;

    /**
     * Creates a new instance with the default response status code ({@link HttpResponseStatus#BAD_REQUEST}).
     * 
     * @param message The description of the failure
     */
    public HttpRequestDecoderException(String message) {
        this(message, HttpResponseStatus.BAD_REQUEST);
    }

    /**
     * Creates a new instance.
     * 
     * @param message A description of the failure
     * @param responseStatus The associated response status
     */
    public HttpRequestDecoderException(String message, HttpResponseStatus responseStatus) {
        super(message);
        this.responseStatus = responseStatus;
    }

    /**
     * Returns the response status associated with this exception.
     */
    public HttpResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
