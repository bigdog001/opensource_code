package com.bigdog.carfinder.android.dao;

import com.bigdog.carfinder.android.bean.LocationBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/4/13
 * Time: 11:37 PM
 */
public interface LocationDao {
    public long saveLocation(LocationBean locationBean);
    public void updateLocationUploadStatus(String _id);
    public void deleteLocation(String _id);
    public LocationBean getLocationBean(String _id);
    public List<LocationBean> getLocationBean(int size); //取未上传的最近n个记录
    public List<LocationBean> getLocationBeans(int flag,int status); //取未上传的最近n个记录 ,flag指定数据新旧
    public void delete(List<String>ids,int flag);//flag 1指完全删除 0指标记为已经上传
}
