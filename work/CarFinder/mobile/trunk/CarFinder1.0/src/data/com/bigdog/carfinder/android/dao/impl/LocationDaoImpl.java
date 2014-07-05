package com.bigdog.carfinder.android.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.bigdog.carfinder.android.bean.LocationBean;
import com.bigdog.carfinder.android.conf.Config;
import com.bigdog.carfinder.android.dao.LocationDao;
import com.bigdog.carfinder.android.db.DBFinderHelper;
import com.bigdog.carfinder.android.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/4/13
 * Time: 11:59 PM
 */
public class LocationDaoImpl implements LocationDao {
    private Context mContext;
    private static LocationDao locationDao;

    private LocationDaoImpl(Context mContext) {
        this.mContext = mContext;
    }

    public static LocationDao getFaceAddressDao(Context context) {
        if (locationDao == null) {
            locationDao = new LocationDaoImpl(context);
        }
        return locationDao;
    }

    @Override
    public long saveLocation(LocationBean locationBean) {
        if (locationBean == null) {
            return 0L;
        }

        long newRowId = 0;
        SQLiteDatabase db = DBFinderHelper.getDBFinderHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Config.LocationBean_table.latitude, locationBean.getLatitude());
        values.put(Config.LocationBean_table.lontitude, locationBean.getLontitude());
        values.put(Config.LocationBean_table.detacte_time, locationBean.getDetacte_time());
//        values.put(Config.LocationBean_table.tid, locationBean.getTid());
        values.put(Config.LocationBean_table.upload_status, locationBean.getUpload_status());
        newRowId = db.insert(Config.LocationBean_table.Locationbean_TABLE_NAME, null, values);
        SystemUtil.log("成功插入定位单元:" + locationBean + ",地址编号：" + newRowId);
        return newRowId;
    }

    @Override
    public void updateLocationUploadStatus(String _id) {
        SystemUtil.log("开始update:" + _id);
        ContentValues values = new ContentValues();
        values.put(Config.LocationBean_table.upload_status, 1);
        SQLiteDatabase db = DBFinderHelper.getDBFinderHelper(mContext).getWritableDatabase();
        db.update(Config.LocationBean_table.Locationbean_TABLE_NAME, values, Config.LocationBean_table._ID + " =? ", new String[]{_id});
//        db.close();
        SystemUtil.log("成功update:" + _id);
    }

    @Override
    public void deleteLocation(String _id) {
        SQLiteDatabase db = DBFinderHelper.getDBFinderHelper(mContext).getWritableDatabase();
        db.delete(Config.LocationBean_table.Locationbean_TABLE_NAME, Config.LocationBean_table._ID + " =? ", new String[]{_id});
//        db.close();
        SystemUtil.log("成功删除:" + _id);
    }

    @Override
    public LocationBean getLocationBean(String _id) {
        LocationBean locationBean = null;
//        String sql = "select *  from " + Config.LocationBean_table.Locationbean_TABLE_NAME + " where "+Config.LocationBean_table._ID+" ='" + _id + "' order by "+Config.LocationBean_table.detacte_time+" DESC";
        String sql = "select *  from " + Config.LocationBean_table.Locationbean_TABLE_NAME + " where " + Config.LocationBean_table._ID + " ='" + _id + "'";
        SystemUtil.log("select LocationBean:" + sql);
        SQLiteDatabase db = DBFinderHelper.getDBFinderHelper(mContext).getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToNext()) {
            locationBean = new LocationBean();
            locationBean.set_id(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table._ID)));
            locationBean.setLatitude(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table.latitude)));
            locationBean.setLontitude(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table.lontitude)));
//            locationBean.setTid(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table.tid)));
            locationBean.setDetacte_time(c.getColumnIndexOrThrow(Config.LocationBean_table.detacte_time));
            locationBean.setUpload_status(c.getInt(c.getColumnIndexOrThrow(Config.LocationBean_table.upload_status)));
        }
        c.close();
//        db.close();
        SystemUtil.log("成功query:" + locationBean);
        return locationBean;
    }

    @Override
    public List<LocationBean> getLocationBean(int size) {
        List<LocationBean> locationBeans = null;
        String sql = "select * from " + Config.LocationBean_table.Locationbean_TABLE_NAME + " where " + Config.LocationBean_table.upload_status + "=0 ORDER BY  " + Config.LocationBean_table.detacte_time + " DESC LIMIT " + size;
        SQLiteDatabase db = DBFinderHelper.getDBFinderHelper(mContext).getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToNext()) {
            locationBeans = new ArrayList<LocationBean>();
            do {
                LocationBean locationBean = new LocationBean();
                locationBean.set_id(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table._ID)));
                locationBean.setLatitude(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table.latitude)));
                locationBean.setLontitude(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table.lontitude)));
//                locationBean.setTid(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table.tid)));
                locationBean.setDetacte_time(c.getLong(c.getColumnIndexOrThrow(Config.LocationBean_table.detacte_time)));
                locationBean.setUpload_status(c.getInt(c.getColumnIndexOrThrow(Config.LocationBean_table.upload_status)));
                locationBeans.add(locationBean);
            } while (c.moveToNext());
            c.close();
//            db.close();
        }
        return locationBeans;

    }

    @Override
    public List<LocationBean> getLocationBeans(int flag, int status) {
        List<LocationBean> locationBeans = null;
        String wh = "";
        if (status == 0) {
            wh = " where " + Config.LocationBean_table.upload_status + "=0 "; //未上传的数据
        } else if (status == 1) {
            wh = " where " + Config.LocationBean_table.upload_status + "=1 "; //已经上传的数据
        } else if (status == 2) {

        }

        String sql = "";
        if (flag == 0) {
            //取最新的数据  降序
            sql = "select * from " + Config.LocationBean_table.Locationbean_TABLE_NAME + wh + " ORDER BY  " + Config.LocationBean_table.detacte_time + " DESC ";
        } else {
            //取最旧的数据  升序
            sql = "select * from " + Config.LocationBean_table.Locationbean_TABLE_NAME + wh + " ORDER BY  " + Config.LocationBean_table.detacte_time + " ASC ";
        }

        SystemUtil.log("---===>:" + sql);
        SQLiteDatabase db = DBFinderHelper.getDBFinderHelper(mContext).getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        if (c != null && c.moveToNext()) {
            locationBeans = new ArrayList<LocationBean>();
            do {
                LocationBean locationBean = new LocationBean();
                locationBean.set_id(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table._ID)));
                locationBean.setLatitude(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table.latitude)));
                locationBean.setLontitude(c.getString(c.getColumnIndexOrThrow(Config.LocationBean_table.lontitude)));
                locationBean.setDetacte_time(c.getLong(c.getColumnIndexOrThrow(Config.LocationBean_table.detacte_time)));
                locationBean.setUpload_status(c.getInt(c.getColumnIndexOrThrow(Config.LocationBean_table.upload_status)));
                locationBeans.add(locationBean);
            } while (c.moveToNext());
            c.close();
//            db.close();
        }
        return locationBeans;

    }

    @Override
    public void delete(List<String> ids, int flag) {
        String sql = "";
        switch (flag) {
            case 0:

                break;
            case 1:

                break;
            default:
                break;
        }

        ContentValues values = new ContentValues();
        values.put(Config.LocationBean_table.upload_status, 1);
        SQLiteDatabase db = DBFinderHelper.getDBFinderHelper(mContext).getWritableDatabase();
//        db.update(Config.LocationBean_table.Locationbean_TABLE_NAME, values, Config.LocationBean_table._ID + " =? ", new String[]{_id});
//        db.close();

    }
}
