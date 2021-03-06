package com.specl.api.codec.http;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Type safe enumeration representing HTTP protocol version
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596189 $, $Date: 2007-11-19 16:51:38 +1300 (Mon, 19 Nov 2007) $
 */
public class HttpVersion implements Serializable {

    private static final long serialVersionUID = -7727691335746596528L;

    /**
     * HTTP 1/1
     */
    public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP/1.1");

    /**
     * HTTP 1/0
     */
    public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP/1.0");

    private final String name;

    /**
     * Returns the {@link HttpVersion} instance from the specified string.
     * 
     * @return The version, or <code>null</code> if no version is found
     */
    public static HttpVersion valueOf(String string) {
        if (HTTP_1_1.toString().equalsIgnoreCase(string)) {
            return HTTP_1_1;
        }

        if (HTTP_1_0.toString().equalsIgnoreCase(string)) {
            return HTTP_1_0;
        }

        return null;
    }

    /**
     * @return A String representation of this version
     */
    @Override
    public String toString() {
        return name;
    }

    private HttpVersion(String name) {
        this.name = name;
    }

    private Object readResolve() throws ObjectStreamException {
        HttpVersion answer = valueOf(this.name);
        if (answer == null) {
            throw new InvalidObjectException("Unknown HTTP version: " + this.name);
        } else {
            return answer;
        }
    }
}
