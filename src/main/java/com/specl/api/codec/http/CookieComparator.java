package com.specl.api.codec.http;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 596189 $, $Date: 2007-11-19 16:51:38 +1300 (Mon, 19 Nov 2007) $
 */
public class CookieComparator implements Serializable, Comparator<Cookie> {

    private static final long serialVersionUID = -222644341851192813L;

    public static final CookieComparator INSTANCE = new CookieComparator();

    public int compare(Cookie o1, Cookie o2) {
        int answer;

        // Compare the name first.
        answer = o1.getName().compareTo(o2.getName());
        if (answer != 0) {
            return answer;
        }

        // and then path
        if (o1.getPath() == null) {
            if (o2.getPath() != null) {
                answer = -1;
            } else {
                answer = 0;
            }
        } else {
            answer = o1.getPath().compareTo(o2.getPath());
        }

        if (answer != 0) {
            return answer;
        }

        // and then domain
        if (o1.getDomain() == null) {
            if (o2.getDomain() != null) {
                answer = -1;
            } else {
                answer = 0;
            }
        } else {
            answer = o1.getDomain().compareTo(o2.getDomain());
        }

        return answer;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
