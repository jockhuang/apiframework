package com.specl.api.codec.http;

/**
 * A mutable {@link HttpResponse}
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596187 $, $Date: 2007-11-19 16:41:14 +1300 (Mon, 19 Nov 2007) $
 */
public interface MutableHttpResponse extends MutableHttpMessage, HttpResponse {

    /**
     * Adds the cookie of this message by parsing the specified <tt>headerValue</tt>.
     */
    void addCookie(String headerValue);

    /**
     * Sets the status of this response
     * 
     * @param status The response status
     */
    void setStatus(HttpResponseStatus status);

    /**
     * Sets the reason phrase which is associated with the current status of this response.
     */
    void setStatusReasonPhrase(String reasonPhrase);

    /**
     * Normalizes this response to fix possible protocol violations. The following is the normalization step:
     * <ol>
     * <li>Adds '<tt>Connection</tt>' header with an appropriate value determined from the specified <tt>request</tt>
     * and the status of this response.</li>
     * <li>Adds '<tt>Date</tt>' header with current time.</li>
     * <li>Removes body content if the {@link HttpMethod} of the specified <tt>request</tt> doesn't allow body or the
     * status of this response doesn't allow body.</li>
     * <li>Adds '<tt>Content-length</tt>' header if '<tt>Transfer-coding</tt>' header doesn't exist.</li>
     * </ol>
     * 
     * @param request the request that pairs with this response
     */
    void normalize(HttpRequest request);
}
