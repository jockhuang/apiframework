package com.specl.api.codec.http;

/**
 * A mutable {@link Cookie}.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596189 $, $Date: 2007-11-19 16:51:38 +1300 (Mon, 19 Nov 2007) $
 */
public interface MutableCookie extends Cookie {

    /**
     * Sets the value of this cookie
     * 
     * @param value The cookie value
     */
    void setValue(String value);

    /**
     * Sets the domain of this cookie.
     */
    void setDomain(String domain);

    /**
     * Sets the path on the server to which the client returns this cookie.
     * 
     * @param path The path
     */
    void setPath(String path);

    /**
     * Sets the version nubmer of this cookie
     * 
     * @param version The version number
     */
    void setVersion(int version);

    /**
     * Sets whether this cookie should be marked as "secure". Secure cookies should be sent back by a client over a
     * transport as least as secure as that upon which they were received
     * 
     * @param secure <code>true</code> if this cookie should be marked as secure
     */
    void setSecure(boolean secure);

    /**
     * Sets the maximum age of the cookie in seconds. A positive value indicates that the cookie will expire after the
     * specified number of seconds.
     * <p>
     * A value of zero indicates that the cookie should be deleted.
     * <p>
     * A negative value indicates that the cookie should be discarded at the end of the user session
     * 
     * @param expiry The expiry time in seconds
     */
    void setMaxAge(int expiry);

    /**
     * Sets the comment of this cookie. Comments are not supported by version 0 cookies.
     */
    void setComment(String comment);
}
