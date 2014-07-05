package com.facemail.mobile.android.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.facemail.mobile.android.util.SystemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/17/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class FaceProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        SystemUtil.log("facemail004,facemailprovider oncreate!");
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SystemUtil.log("facemail005,query from  facemailprovider!");
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SystemUtil.log("facemail006,insert to  facemailprovider!");
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SystemUtil.log("facemail006,delete from  facemailprovider!");
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SystemUtil.log("facemail007,update from  facemailprovider!");
        return 0;
    }
}
