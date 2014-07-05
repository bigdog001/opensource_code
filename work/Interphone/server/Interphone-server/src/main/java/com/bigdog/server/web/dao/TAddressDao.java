package com.bigdog.server.web.dao;

import com.bigdog.server.web.bean.TAddress;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/2/13
 * Time: 7:28 AM
 */
public interface TAddressDao {
    public void save(TAddress tAddress,String tid);
    public void saveBatch(List<TAddress> tAddresses,String tid);
    public void delete(int id);
    public TAddress getById(int id);
    public List<TAddress> getByRange(int start,int size);

}
