package com.bigdog.carfinder.android.conf;

import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 7/25/13
 * Time: 11:31 AM
 */
public class Config {
    public static final String dbName = "carfinder";
    public static final int dbVersion = 1;


    // server communication setting ====================================
//    public static String api_finder_Host = "http://10.10.52.28:8080/";
    public static String api_finder_Host = "http://bbpd824.j1.fjjsp.net/";
    public static String api_finder_Url = api_finder_Host+"api.action";
    // ====================================server communication setting


    public static final String Config_tracker_id_name = "tracker_id";
    public static final String Config_tracker_id_passwd = "tracker_passwd";
    public static final String ConfigFileSp = "carfinder";
    public static final String work_declare = "declare";
    public static final String work_model = "model";//1 探测模式 2 观测模式
    public static final String come_from = "comefrom";
    public static final String come_from_welcome = "welcome";
    public static final String detect_rcv = "com.bigdog.carfinder.android.service.LocationService";
    public static final long location_frequency_default = 1*1000*60;//默认的位置探测时间差
    public static final long location_upload_frequency_default = 1*1000*60*60;//默认的位置上报时间差
    public static final String location_frequency_name = "location_frequency";
    public static final String location_upload_frequency_name = "location_upload_frequency";
    public static final String COMMA_SEP = ",";

    private static final class BASIC_TYPE{
        /**
         * TEXT
         NUMERIC
         INTEGER
         REAL
         NONE
         */
        public static final String INTEGER = " INTEGER ";
        public static final String TEXT = " TEXT ";
        public static final String DATE = " DATE ";

    }


    public static final class LocationBean_table  implements BaseColumns {
        public static final String Locationbean_TABLE_NAME = "location";
        public static final String latitude = "latitude";
        public static final String lontitude = "lontitude";
        public static final String detacte_time = "detacte_time";
        public static final String tid = "tid";
        public static final String upload_status = "upload_status";
    }

    public static final class FIELD_TYPE{
        public static final String latitude_FIELD_TYPE = BASIC_TYPE.TEXT; //必须是标准的email地址
        public static final String lontitude_FIELD_TYPE =  BASIC_TYPE.TEXT;
        public static final String detacte_time_FIELD_TYPE =  BASIC_TYPE.INTEGER;
        public static final String tid_FIELD_TYPE =  BASIC_TYPE.TEXT;
        public static final String upload_status_FIELD_TYPE =  BASIC_TYPE.INTEGER;

    }

    }
