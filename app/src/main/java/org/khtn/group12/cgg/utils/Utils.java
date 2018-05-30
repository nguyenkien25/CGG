package org.khtn.group12.cgg.utils;

public class Utils {

    public static boolean checkUserLogin() {
        if (AppController.getInstance().getPrefManager().getUID() != null && !AppController.getInstance().getPrefManager().getUID().equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
