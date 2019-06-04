package org.chobit.jspy.utils;


import org.chobit.jspy.tools.Base62;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ShortCodeGener {



    private static final AtomicInteger SEQ = new AtomicInteger(1);
    private static final DecimalFormat FORMAT = new DecimalFormat("00");

    public static synchronized String genShortCode() {
        StringBuilder builder = new StringBuilder(System.currentTimeMillis() + "");
        if (SEQ.incrementAndGet() % 10 == 0) {
            SEQ.incrementAndGet();
        }
        builder.append(FORMAT.format(SEQ.get()));
        if (99 == SEQ.get()) {
            SEQ.set(1);
        }
        long v = Long.parseLong(builder.reverse().toString());
        return Base62.encode(v);
    }


    private ShortCodeGener() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }
}
