package com.specl.api.codec.http;

public class DefaultCookie implements MutableCookie {

    private static final long serialVersionUID = 5713305385740914300L;

    private final String name;

    private String value;

    private String domain;

    private String path;

    private String comment;

    private boolean secure;

    private int version = 0;

    private int maxAge = -1;

    /**
     * Constructor used to allow users to create new cookies to be sent back to the client.
     * 
     * @param name the name of the new cookie.
     */
    public DefaultCookie(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }

        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Cookie)) {
            return false;
        }

        Cookie that = (Cookie) o;
        if (!name.equals(that.getName())) {
            return false;
        }

        if (path == null) {
            if (that.getPath() != null) {
                return false;
            }
        } else if (!path.equals(that.getPath())) {
            return false;
        }

        if (domain == null) {
            if (that.getDomain() != null) {
                return false;
            }
        } else if (!domain.equals(that.getDomain())) {
            return false;
        }

        return true;
    }

    public int compareTo(Cookie o) {
        int answer;

        // Compare the name first.
        answer = name.compareTo(o.getName());
        if (answer != 0) {
            return answer;
        }

        // and then path
        if (path == null) {
            if (o.getPath() != null) {
                answer = -1;
            } else {
                answer = 0;
            }
        } else {
            answer = path.compareTo(o.getPath());
        }

        if (answer != 0) {
            return answer;
        }

        // and then domain
        if (domain == null) {
            if (o.getDomain() != null) {
                answer = -1;
            } else {
                answer = 0;
            }
        } else {
            answer = domain.compareTo(o.getDomain());
        }

        return answer;
    }

    @Override
    public String toString() {
        return "name=" + getName() + " value=" + getValue() + " domain=" + getDomain() + " path=" + getPath()
                + " maxAge=" + getMaxAge() + " secure=" + isSecure();
    }
}
