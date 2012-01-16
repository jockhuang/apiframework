package com.specl.api.codec.http;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596190 $, $Date: 2007-11-19 16:55:48 +1300 (Mon, 19 Nov 2007) $
 */
class HttpHeaderNameComparator implements Comparator<String>, Serializable {
    private static final long serialVersionUID = -3781572057321507963L;

    static final HttpHeaderNameComparator INSTANCE = new HttpHeaderNameComparator();

    public int compare(String o1, String o2) {
        return o1.compareToIgnoreCase(o2);
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
