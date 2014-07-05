package com.bigdog.carfinder.android.util;

import android.telephony.NeighboringCellInfo;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/26/13
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class CellInfoUtil {
    public static final int getLac(NeighboringCellInfo info) {
        if (SystemUtil.fitApiLevel(5)) {
            return info.getLac();
        } else {
            return 0;
        }
    }
}
