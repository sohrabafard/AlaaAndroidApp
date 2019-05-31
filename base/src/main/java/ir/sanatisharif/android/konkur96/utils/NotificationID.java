package ir.sanatisharif.android.konkur96.utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationID {
    private static       Random        rand = new Random();
    private final static AtomicInteger c    = new AtomicInteger(rand.nextInt(6000));

    public static int getID() {
        return c.incrementAndGet();
    }
}
