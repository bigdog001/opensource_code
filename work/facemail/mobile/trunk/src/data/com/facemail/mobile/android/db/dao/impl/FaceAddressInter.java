package com.facemail.mobile.android.db.dao.impl;

import com.facemail.mobile.android.db.bean.FaceAddress;
import com.facemail.mobile.android.db.bean.FaceMailMessage;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/4/13
 * Time: 6:31 AM
 * To change this template use File | Settings | File Templates.
 */
public interface FaceAddressInter {
    public long[] saveAddress(List<FaceAddress> faceAddresses);
    public long saveAddres(FaceAddress faceAddress);
    public Map<String,List<FaceAddress>> getFaceAddress(FaceMailMessage faceMailMessage);
}
