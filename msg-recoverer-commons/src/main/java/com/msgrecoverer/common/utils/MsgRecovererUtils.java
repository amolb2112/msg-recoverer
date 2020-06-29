package com.msgrecoverer.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Utils class
 *
 * Created by amolb2112 on 5/3/17.
 */
public class MsgRecovererUtils {

    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
