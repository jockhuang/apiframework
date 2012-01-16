package com.specl.api.codec.http;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * A mutable {@link HttpMessage}.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596189 $, $Date: 2007-11-19 16:51:38 +1300 (Mon, 19 Nov 2007) $
 */
public interface MutableHttpMessage extends HttpMessage {

    /**
     * Sets the version of the protocol associated with this request.
     */
    void setProtocolVersion(HttpVersion protocolVersion);

    /**
     * Sets the <tt>Content-Type</tt> header of the response.
     * 
     * @param type The content type.
     */
    void setContentType(String type);

    /**
     * Sets the <tt>Connection</tt> header of the response.
     */
    void setKeepAlive(boolean keepAlive);

    /**
     * Adds an HTTP header to this response. Adding a header does not cause any existing headers with the same name to
     * be overwritten
     * 
     * @param name The header name
     * @param value The header value
     */
    void addHeader(String name, String value);

    /**
     * Removes all HTTP headers with the specified name.
     */
    boolean removeHeader(String name);

    /**
     * Sets the value of an HTTP header. Any existing headers with the specified name are removed
     * 
     * @param name The header name
     * @param value The header value
     */
    void setHeader(String name, String value);

    /**
     * Sets the HTTP headers of this response.
     */
    void setHeaders(Map<String, List<String>> headers);

    /**
     * Removes all HTTP headers from this response.
     */
    void clearHeaders();

    /**
     * Adds the specified cookie.
     */
    void addCookie(Cookie cookie);

    /**
     * Removed the specified cookie.
     * 
     * @return <tt>true</tt> if the specified cookie has been removed
     */
    boolean removeCookie(Cookie cookie);

    /**
     * Sets the cookies of this message.
     */
    void setCookies(Collection<Cookie> cookies);

    /**
     * Removed all cookies from this message.
     */
    void clearCookies();

    /**
     * Sets the content of the response body.
     * 
     * @param content <tt>null</tt> to clear the body content
     */
    void setContent(IoBuffer content);
}
