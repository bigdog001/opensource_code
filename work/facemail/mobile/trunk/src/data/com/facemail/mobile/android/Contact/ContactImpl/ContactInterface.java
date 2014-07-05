package com.facemail.mobile.android.Contact.ContactImpl;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/21/13
 * Time: 2:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ContactInterface {
    public List<Map<String,String>> getAllContacts();
    public String lookupNameByPhoneNumber(String phoneNumber);
    public List<String> lookupEmailsByName(String name);
}
