package com.specl.api.codec.http;

import java.util.List;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * Represents a response to an <code>HttpRequest</code>.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596187 $, $Date: 2007-11-19 16:41:14 +1300 (Mon, 19 Nov 2007) $
 */
public interface HttpResponse extends HttpMessage {

    /**
     * Returns the value of the HTTP header with the specified name. If more than one header with the given name is
     * associated with this response, one is selected and returned.
     * 
     * @param name The name of the desired header
     * @return The header value - or null if no header is found with the specified name
     */
    String getHeader(String name);

    /**
     * Returns <tt>true</tt> if the HTTP header with the specified name exists in this response.
     */
    boolean containsHeader(String name);

    /**
     * Returns the {@link Map} of HTTP headers whose key is a {@link String} and whose value is a {@link List} of
     * {@link String}s.
     */
    Map<String, List<String>> getHeaders();

    /**
     * Returns the Content-Type header of the response.
     */
    String getContentType();

    /**
     * Returns the status of this response
     */
    HttpResponseStatus getStatus();

    /**
     * Returns the reason phrase which is associated with the current status of this response.
     */
    String getStatusReasonPhrase();

    /**
     * Returns the content of the response body.
     */
    IoBuffer getContent();
}
