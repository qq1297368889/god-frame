package gzb.tools;

import gzb.tools.log.LogImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    static gzb.tools.log.Log Log=new LogImpl(DateTime.class);
    public static void main(String[] args) {
        Log.i(new DateTime().toString());
        Log.i(new DateTime().monthAdd(1).toString());
        Log.i(new DateTime().monthAdd(2).toString());
        Log.i(new DateTime().monthAdd(3).toString());
        Log.i(new DateTime().monthAdd(4).toString());
        Log.i(new DateTime().monthAdd(5).toString());
    }
    long t;
    String [] SJ= Tools.toArrayString(
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd-HH-mm-ss",
            "yyyy/MM/dd/HH/mm/ss",
            "yyyy年MM月dd号HH点mm分ss秒",
            "yyyy-MM-dd",
            "HH:mm:ss",
            "yyyy-MM",
            "yyyy"
    );
    public DateTime() {
        t = new Date().getTime();
    }

    public DateTime(long t) {
        this.t = t;
    }

    public DateTime(int t) {
        this.t = t * 1000;
    }

    public DateTime(String t, String format) {
        try {
            this.t = new SimpleDateFormat(format).parse(t).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public DateTime(String t,int i) {
        try {
            this.t = new SimpleDateFormat(SJ[i]).parse(t).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public DateTime(String t) {
        try {
            this.t = new SimpleDateFormat(SJ[0]).parse(t).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int toStampInt() {
        return Integer.valueOf((t + "").substring(0, 10));
    }

    public long toStampLong() {
        return t;
    }

    public Date toDate() {
        return new Date(t);
    }

    @Override
    public String toString() {
        return formatDateTime();
    }

    public String formatYear() {
        return new SimpleDateFormat(SJ[7]).format(new Date(t)).toString();
    }
    public String formatMont() {
        return new SimpleDateFormat(SJ[6]).format(new Date(t)).toString();
    }
    public String formatDate() {
        return new SimpleDateFormat(SJ[4]).format(new Date(t)).toString();
    }

    public String formatTime() {
        return new SimpleDateFormat(SJ[5]).format(new Date(t)).toString();
    }
    public String formatDateTime() {
        return new SimpleDateFormat(SJ[0]).format(new Date(t)).toString();
    }

    public String format(String format) {
        return new SimpleDateFormat(format).format(new Date(t)).toString();
    }

    public DateTime operation(long millisecond) {
        this.t += millisecond;
        return this;
    }
    public DateTime monthAdd(int mont) {
        for (int i = 0; i < mont; i++) {
            String m=formatMont();
            String newM=formatMont();
            while (m.equals(newM)){
                this.t += 1000*60*60*24;
                newM=formatMont();
            }
        }

        return this;
    }
}
