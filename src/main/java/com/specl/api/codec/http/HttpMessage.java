package com.specl.api.codec.http;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Base message type of {@link HttpRequest} and {@link HttpResponse}.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596187 $, $Date: 2007-11-19 16:41:14 +1300 (Mon, 19 Nov 2007) $
 */
public interface HttpMessage extends Serializable {

    /**
     * Returns the version of the protocol associated with this request.
     */
    HttpVersion getProtocolVersion();

    /**
     * Gets the <tt>Content-Type</tt> header of the request.
     * 
     * @return The content type.
     */
    String getContentType();

    /**
     * Returns <tt>true</tt> if this message enables keep-alive connection.
     */
    boolean isKeepAlive();

    /**
     * Returns the value of the HTTP header with the specified name. If more than one header with the given name is
     * associated with this request, one is selected and returned.
     * 
     * @param name The name of the desired header
     * @return The header value - or null if no header is found with the specified name
     */
    String getHeader(String name);

    /**
     * Returns <tt>true</tt> if the HTTP header with the specified name exists in this request.
     */
    boolean containsHeader(String name);

    /**
     * Returns a read-only {@link Map} of HTTP headers whose key is a {@link String} and whose value is a {@link List}
     * of {@link String}s.
     */
    Map<String, List<String>> getHeaders();

    /**
     * Returns a read-only {@link Map} of cookies whose key is a {@link String} and whose value is a {@link Cookie}.
     */
    Set<Cookie> getCookies();

    /**
     * Returns the content of the request body.
     */
    Object getContent();
}
