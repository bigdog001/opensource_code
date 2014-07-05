package com.bigdog.carfinder.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.bigdog.carfinder.android.conf.Config;
import com.bigdog.carfinder.android.util.SystemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/4/13
 * Time: 11:39 PM
 */
public class DBFinderHelper  extends SQLiteOpenHelper {
    private static DBFinderHelper dbFinderHelper;

    private static final String SQL_LOCATION_TABLE_NAME_CREATE= "CREATE TABLE " + Config.LocationBean_table.Locationbean_TABLE_NAME + " (" +
            Config.LocationBean_table._ID + " INTEGER PRIMARY KEY," +
            Config.LocationBean_table.latitude +  Config.FIELD_TYPE.latitude_FIELD_TYPE  + Config.COMMA_SEP+
            Config.LocationBean_table.lontitude  +  Config.FIELD_TYPE.lontitude_FIELD_TYPE  + Config.COMMA_SEP+
            Config.LocationBean_table.detacte_time  +  Config.FIELD_TYPE.detacte_time_FIELD_TYPE  + Config.COMMA_SEP+
            Config.LocationBean_table.upload_status  +  Config.FIELD_TYPE.upload_status_FIELD_TYPE  +
            ");";
    private static final String SQL_LOCATION_TABLE_DELETE ="DROP TABLE IF EXISTS " + Config.LocationBean_table.Locationbean_TABLE_NAME;


    public static DBFinderHelper getDBFinderHelper(Context context) {
//        SystemUtil.log("DBFinderHelper,开始获取dbHelper.....");
        if (dbFinderHelper==null) {
            dbFinderHelper=new DBFinderHelper(context);
        }
//        SystemUtil.log("DBFinderHelper,成功获取dbHelper.....");
        return dbFinderHelper;
    }
    private DBFinderHelper(Context context) {
        super(context, Config.dbName, null, Config.dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建所需的数据库表结构
        db.execSQL(SQL_LOCATION_TABLE_NAME_CREATE);
//        db.execSQL(SQL_LOCATION_TABLE_DELETE);
//        SystemUtil.log("facemail019,table "+Config.USER_ACCOUNT.MAILACCOUNT_TABLE_NAME+" and "+Config.USER_FaceMailMessage.MAILMESSAGE_TABLE_NAME+" are created...,and sql is:"+SQL_MAILACCOUNT_TABLE_NAME_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(SQL_LOCATION_TABLE_NAME_CREATE);
        db.execSQL(SQL_LOCATION_TABLE_DELETE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
