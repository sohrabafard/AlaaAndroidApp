package ir.sanatisharif.android.konkur96.utils;

import java.text.DecimalFormat;

public class ShopUtils {

    public static String formatPrice(long number){

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }
}
