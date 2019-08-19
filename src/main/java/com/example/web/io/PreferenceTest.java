package com.example.web.io;

import java.util.prefs.Preferences;

/**
 * @Auther: 36560
 * @Date: 2019/8/10 :18:14
 * @Description:
 */
public class PreferenceTest {
    public static void main(String[] args) {
        Preferences preferences = Preferences.userNodeForPackage(PreferenceTest.class);
        preferences.put("1","1");
        preferences.put("compress","321");
        preferences.put("driver","sql.com.mysql");
        int usage = preferences.getInt("usage", 0);
        usage++;
        preferences.putInt("usage",usage);
        System.out.println(usage);
    }
}
