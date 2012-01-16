package com.specl.api.codec.http;

/**
 * HTTP Header Constants.
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596271 $, $Date: 2007-11-20 00:19:45 +1300 (Tue, 20 Nov 2007) $
 */
public class HttpHeaderConstants {

    /**
     * The name of the "connection" header
     */
    public static final String KEY_CONNECTION = "Connection";

    /**
     * The server header
     */
    public static final String KEY_SERVER = "Server";

    /**
     * The header value to indicate connection closure
     */
    public static final String VALUE_CLOSE = "close";

    /**
     * The header value to indicate connection keep-alive (http 1.0)
     */
    public static final String VALUE_KEEP_ALIVE = "Keep-Alive";

    /**
     * The "content-type" header name.
     */
    public static final String KEY_CONTENT_TYPE = "Content-Type";

    /**
     * The value of "content-type" header that indicates the content contains a URL-encoded form.
     */
    public static final String VALUE_URLENCODED_FORM = "application/x-www-form-urlencoded";

    /**
     * The "content-length" header name
     */
    public static final String KEY_CONTENT_LENGTH = "Content-Length";

    /**
     * The "transfer-coding" header name
     */
    public static final String KEY_TRANSFER_CODING = "Transfer-Coding";

    /**
     * The "transfer-encoding" header name
     */
    public static final String KEY_TRANSFER_ENCODING = "Transfer-Encoding";

    /**
     * The chunked coding.
     */
    public static final String VALUE_CHUNKED = "chunked";

    /**
     * The "expect" header name
     */
    public static final String KEY_EXPECT = "Expect";

    /**
     * The continue expectation
     */
    public static final String VALUE_CONTINUE_EXPECTATION = "100-continue";

    /**
     * The "date" header
     */
    public static final String KEY_DATE = "Date";

    /**
     * The "cookie" header.
     */
    public static final String KEY_COOKIE = "Cookie";

    /**
     * The "set-cookie" header.
     */
    public static final String KEY_SET_COOKIE = "Set-Cookie";

    /**
     * The "host" header.
     */
    public static final String KEY_HOST = "Host";

    private HttpHeaderConstants() {
    }
}
