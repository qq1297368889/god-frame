package gzb.tools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class let extends Object {
    public Object obj;

    public let(Object obj) {
        this.obj = obj;
    }

    public boolean equals(String obj) {
        if (this.obj == null && obj == null){
            return true;
        }
        if (this.obj == null || obj == null){
            return false;
        }
        return toString().equals(obj);
    }


    @Override
    public int hashCode() {
        return Objects.hash(obj);
    }

    public boolean equals(Integer obj) {
        if (this.obj == null && obj == null){
            return true;
        }
        if (this.obj == null || obj == null){
            return false;
        }
        return toInteger() + 0 == (obj + 0);
    }

    public boolean equals(Long obj) {
        if (this.obj == null && obj == null){
            return true;
        }
        if (this.obj == null || obj == null){
            return false;
        }
        return toLong() + 0 == (obj + 0);
    }

    @Override
    public String toString() {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public Object toObject() {
        if (obj == null) {
            return null;
        }
        return obj;
    }

    public Integer toInteger() {
        if (obj == null) {
            return null;
        }
        return Integer.valueOf(obj.toString());
    }

    public Long toLong() {
        if (obj == null) {
            return null;
        }
        return Long.valueOf(obj.toString());
    }

    public Map toMap() {
        if (obj == null) {
            return null;
        }
        return (Map) obj;
    }

    public char[] toChar() {
        if (obj == null) {
            return null;
        }
        return obj.toString().toCharArray();
    }

    public String[] toArray(String regex) {
        if (obj == null) {
            return null;
        }
        return obj.toString().split(regex);
    }

    public String[] toArray(String regex, int limit) {
        if (obj == null) {
            return null;
        }
        return obj.toString().split(regex, limit);
    }

    public List<String> toList(String regex, int limit) {
        if (obj == null) {
            return null;
        }
        return Arrays.asList(obj.toString().split(regex, limit));
    }

    public List<String> toList(String regex) {
        if (obj == null) {
            return null;
        }
        return Arrays.asList(obj.toString().split(regex));
    }
}