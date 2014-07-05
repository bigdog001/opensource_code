/*
package com.facemail.mobile.android.Contact;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

*/
/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 4/21/13
 * Time: 2:51 AM
 * To change this template use File | Settings | File Templates.
 *//*

public class ContactManager {

    private static ContactManager instance;

    */
/**
     * 如从syncadapter同步,则需要设置currentAccount
     *//*

    private Account currentAccount;

    public static ContactManager getInstance(Context context) {
        if (instance == null) {
            synchronized (ContactManager.class) {
                if (instance == null) {
                    ContactManager.instance = new ContactManager(context);
                }
            }
        }

        return instance;
    }

    */
/* 查询Data表时的投影 *//*

    private final static String[] PROJECTION = new String[] {
            // Metadata
            ContactsContract.Data.RAW_CONTACT_ID,
            ContactsContract.Data.MIMETYPE,

            // Name
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
            // DISPLAY_NAME由FAMILY_NAME，GIVEN_NAME拼装而来，对用户没有UI接口
            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,

            // Phone
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.LABEL,

            // EMail
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.Email.TYPE,
            ContactsContract.CommonDataKinds.Email.LABEL,

            ContactsContract.Data.CONTACT_ID
    };

    */
/* 查询Data表时的选择 *//*

    private final static String SELECTION =
            ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'"
                    + " OR "
                    + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "'"
                    + " OR "
                    + ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE + "'";

    protected static final String LOG_TAG = "CONTACT";

    private Context context;


    private ContactManager() {
    }

    private ContactManager (Context context) {
        this.context = context;
    }

    public void setCurrentAccount (Account account) {
        currentAccount = account;
    }



    */
/**
     * 读取通讯录中一个人的记录
     * @return
     *//*

    public Contact[] loadContact (long contactId) {

        final String[] projection = PROJECTION;
        final String selection = ContactsContract.Data.CONTACT_ID + "=?";
        final String[] selectionArgs = new String[]{String.valueOf(contactId)};

        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Data.CONTENT_URI, projection, selection, selectionArgs, null);
        Contact[] c = loadContacts(cursor);
        if(cursor!=null&&!cursor.isClosed()){
            cursor.close();
        }
        return c;
    }


    */
/**
     * 读出通讯录中的所有记录。
     * @return
     * 		通讯录中包含的所有记录。
     *//*

    public Contact[] loadAllContacts () {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                SELECTION,
                null,
                null);
        Contact[] c = loadContacts(cursor);
        if(!cursor.isClosed()){
            cursor.close();
        }
        return c;
    }



    */
/**
     * 插入联系人 并设置头像
     * @param contact
     *//*

    public boolean addNewContact(Contact contact) {

        Account account = getCurrentAccount();

        if (account != null && contact != null) {
            ContactOperations contactOps = ContactOperations.addContactOperation(account.name, contact, context);
            boolean result = contactOps.execute();
            if (result) {
                long rawContactId = getRawContactIdFromExecResult(contactOps);
                contact.rawContactId = rawContactId;
//				contactOps.addHeadPhoto(rawContactId, ContactOperations.INVALID_ID, contact, false, totalCount, true);
            }
            return result;
        }
        return false;

    }

    */
/**
     * 更新联系人信息
     * @param replace
     * @param contact
     *//*

    public Contact updateExistingContact(Contact contact, boolean replace) {

        Account account = getCurrentAccount();
        Contact[] contacts = loadContact(contact.getSerialNumber());
        Contact existingContact = null;
        if (contacts != null && contacts.length > 0) {
            existingContact = contacts[0];
        }

        if (existingContact != null && account != null && contact != null) {
            // try insert first
            Contact joinedContact = existingContact.joinContact(contact);
            ContactOperations contactInsertOps = ContactOperations.addContactOperation(account.name, joinedContact, context);
//			System.out.println("-----------------------ttk--------------------");
            boolean result = contactInsertOps.execute();
            long rawContactId = ContactOperations.INVALID_ID;
//			System.out.println("-----------------------ttend--------------------");
            long serialNumber = contact.getSerialNumber();
            if (!result) {
                ContactOperations contactUpdateOps = ContactOperations.updateContactOperation(account.name, existingContact, contact, context);
                result = contactUpdateOps.execute();

            } else {
                //下面这些逻辑主要是为了补救raw contact合并失败的联系人，如合并失败，会每次同步都多一个相同的联系人
                //先插入raw_contact 如果contactid与serial number相同，则表明合并成功
                //如合并不成功，则在raw contact的sync1字段中标示该raw contact合并失败，在sync2字段中记录contactid供copy头像用
                //然后删除除此条以外，raw contact的sync1标记失败，并且sourceid与此contact相同的记录

                //TODO 可能用户手动合并

                rawContactId = getRawContactIdFromExecResult(contactInsertOps);

                long insertContactId = getContactId(rawContactId);

                boolean aggregationSuccess = insertContactId == contact.getSerialNumber();
                if (!aggregationSuccess) {

                    copyPhotoData (account, contact, rawContactId);				//copy照片数据，防止重复下载
                    deleteDuplicateMarkedRawContacts(account, contact);			//删除之前插入的重复raw contact
                    markRawContactAsAggFailed(rawContactId, contact.getSerialNumber());	//将当前的raw contact标记为合并失败，以便于下次删除


                    checkAggregationExceptions (contact, account, rawContactId);	//有可能是用户手动分离
                    serialNumber = ContactOperations.INVALID_ID;
                }

            }
            joinedContact.serialNumber = serialNumber;
            joinedContact.rawContactId = rawContactId;
//			contactInsertOps.addHeadPhoto(rawContactId, serialNumber, joinedContact, replaceHeadPhoto, totalCount, showProgress);
            joinedContact.needUpdateHeadPhoto = contactInsertOps.needUpdateHeadPhoto(joinedContact, replace);
            return joinedContact;

        }

        return contact;
    }

    */
/**
     * 取消同步
     *//*

    public void cancelSync() {
        HttpProviderWrapper.getInstance().stop();
        if (contactSyncResponse != null) {
            contactSyncResponse.onSyncCancel();
        }
    }

    */
/**
     * 检查是否手动分离
     * 如有两个同样的sourceid的联系人 则查看该rawcontact是否在aggregation exception中
     * 如在 则表示手动分离， 则删除老的，并更新aggregation exception
     *//*

    private void checkAggregationExceptions(Contact contact, Account account, long insertRawContactId) {

        ContentResolver cr = context.getContentResolver();

        String[] rawContactProjection = new String [] {ContactsContract.RawContacts._ID};
        String rawContactSelection = ContactsContract.RawContacts.SOURCE_ID + " =? AND " +
                ContactsContract.RawContacts.ACCOUNT_TYPE + " in (?) AND " +
                ContactsContract.RawContacts.ACCOUNT_NAME + " =? AND " +
                ContactsContract.RawContacts.DELETED + " = 0 AND " +
                ContactsContract.RawContacts._ID + " <> ?";
        String[] rawContactSelectionArgs = new String [] {String.valueOf(contact.getUid()), RRAuthConstants.ACCOUNT_TYPE, account.name, String.valueOf(insertRawContactId)};
        Cursor rawContactCursor = cr.query(ContactsContract.RawContacts.CONTENT_URI, rawContactProjection, rawContactSelection, rawContactSelectionArgs, null);
        Cursor aggreCursor = null;

        if (rawContactCursor != null && rawContactCursor.moveToFirst()) {

            int index = rawContactCursor.getColumnIndexOrThrow(ContactsContract.RawContacts._ID);
            long rawContactId = rawContactCursor.getLong(index);

            String[] aggreProjection = new String []{ContactsContract.AggregationExceptions._ID,
                    ContactsContract.AggregationExceptions.RAW_CONTACT_ID1,
                    ContactsContract.AggregationExceptions.RAW_CONTACT_ID2};
            String aggreSelection = "(" + ContactsContract.AggregationExceptions.RAW_CONTACT_ID1 + " =? OR " +
                    ContactsContract.AggregationExceptions.RAW_CONTACT_ID2 + " =? ) AND " +
                    ContactsContract.AggregationExceptions.TYPE + " = " + ContactsContract.AggregationExceptions.TYPE_KEEP_SEPARATE;
            String[] aggreSelectionArgs = new String[]{String.valueOf(rawContactId), String.valueOf(rawContactId)};
            aggreCursor = cr.query(ContactsContract.AggregationExceptions.CONTENT_URI, aggreProjection, aggreSelection, aggreSelectionArgs, null);
            if (aggreCursor != null && aggreCursor.moveToFirst()) {
                //aggregation exception

                index = aggreCursor.getColumnIndexOrThrow(ContactsContract.AggregationExceptions._ID);
                long aggreId = aggreCursor.getLong(index);
                index = aggreCursor.getColumnIndexOrThrow(ContactsContract.AggregationExceptions.RAW_CONTACT_ID1);
                long rawContactId1 = aggreCursor.getLong(index);

                copyPhotoData (account, contact, insertRawContactId);
                ContactOperations contactOps = ContactOperations.deleteRawContact(account, context, rawContactId);
                contactOps.execute();

                ContentValues values = new ContentValues();
                String column = null;
                if (rawContactId1 == rawContactId) {
                    column = ContactsContract.AggregationExceptions.RAW_CONTACT_ID2;
                } else {
                    column = ContactsContract.AggregationExceptions.RAW_CONTACT_ID1;
                }
                values.put(column, insertRawContactId);
                cr.update(ContactsContract.AggregationExceptions.CONTENT_URI, values, ContactsContract.AggregationExceptions._ID + " =?", new String[]{String.valueOf(aggreId)});

            }

        }

    }

    */
/**
     * copy photo data to new raw contact
     * @param account
     * @param contact
     * @param rawContactId
     * @param insertContactId
     *//*

    private void copyPhotoData(Account account, Contact contact, long rawContactId) {

        long existingRawContactId = ContactOperations.INVALID_ID;
        ContentResolver cr = context.getContentResolver();

        String[] projection = new String[] {ContactsContract.RawContacts._ID};
        String selection = ContactsContract.RawContacts.SYNC1 + " =? AND " +
                ContactsContract.RawContacts.SYNC2 + " =?";
        String[] selectionArgs = new String [] {"1", String.valueOf(contact.getSerialNumber())};
        Cursor cursor = cr.query(ContactsContract.RawContacts.CONTENT_URI, projection, selection, selectionArgs, null);
        Cursor dataCursor = null;

        try {

            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(ContactsContract.RawContacts._ID);
                existingRawContactId = cursor.getLong(index);

                String[] dataProjection = new String[] {ContactsContract.CommonDataKinds.Photo.PHOTO, ContactsContract.Data.SYNC2};
                String dataSeletion = ContactsContract.Data.RAW_CONTACT_ID + " =? AND " +
                        ContactsContract.Data.MIMETYPE + " =?";
                String[] dataSelectionArgs = new String [] {String.valueOf(existingRawContactId), ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE};
                dataCursor = cr.query(ContactsContract.Data.CONTENT_URI, dataProjection, dataSeletion, dataSelectionArgs, null);

                if (dataCursor != null && dataCursor.moveToFirst()) {
                    ContentValues values = new ContentValues();
                    int photoIndex = dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Photo.PHOTO);
                    values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, dataCursor.getBlob(photoIndex));
                    int synIndex = dataCursor.getColumnIndexOrThrow(ContactsContract.Data.SYNC2);
                    values.put(ContactsContract.Data.SYNC2, dataCursor.getString(synIndex));
                    values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                    values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);

                    cr.insert(ContactsContract.Data.CONTENT_URI, values);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (dataCursor != null) {
                dataCursor.close();
            }
        }

    }



    */
/**
     * 标记合并失败的raw contact
     * @param rawContactId
     * @param contactId
     *//*

    private void markRawContactAsAggFailed (long rawContactId, long contactId) {
        ContentResolver cr = context.getContentResolver();

        ContentValues values = new ContentValues();
        values.put(ContactsContract.RawContacts.SYNC1, "1");
        values.put(ContactsContract.RawContacts.SYNC2, contactId);

        String where = ContactsContract.RawContacts._ID + " =?";
        String[] selectionArgs = new String[]{String.valueOf(rawContactId)};

        cr.update(ContactsContract.RawContacts.CONTENT_URI, values, where, selectionArgs);
    }

    */
/**
     * 从content provider results中获取 raw contact id
     * @param contentProviderResults
     * @return
     *//*

    private long getRawContactIdFromExecResult (ContactOperations contactOps) {
        long rawContactId = ContactOperations.INVALID_ID;
        ContentProviderResult[] r = contactOps.getExecuteResults();
        if (r != null && r.length > contactOps.rawContactBackReference) {
            Uri rawContactUri = r[contactOps.rawContactBackReference].uri;
            if (rawContactUri != null) {
                rawContactId = ContentUris.parseId(rawContactUri);
            }
        }
        return rawContactId;
    }

    */
/**
     * 删除所有当前帐户联系人
     *//*

    public void deleteAllContacts() {

        Account account = getCurrentAccount();
        if (account != null) {
            ContactOperations contactDelOps = ContactOperations.deleteAllContactOperation (account, context);
            contactDelOps.execute();
        }

    }


    */
/**
     * 获取raw contact的contact id
     * @param rawContactId
     * @return
     *//*

    private long getContactId (long rawContactId) {
        long contactId = ContactOperations.INVALID_ID;

        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{ContactsContract.RawContacts.CONTACT_ID};
        String selection = ContactsContract.RawContacts._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(rawContactId)};

        Cursor cursor = cr.query(ContactsContract.RawContacts.CONTENT_URI, projection, selection, selectionArgs, null);
        try {
            if(cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(ContactsContract.RawContacts.CONTACT_ID);
                contactId = cursor.getLong(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return contactId;
    }


    */
/**
     * 读取cursor中的全部contact
     * @param cursor
     * @return
     *//*

    private Contact[] loadContacts (Cursor cursor) {



        if (cursor == null || !cursor.moveToFirst()){
            return new Contact[0];
        }

        Hashtable<Long, Contact> table = new Hashtable<Long, Contact>();

        do {

            int index = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE);
            String mime = cursor.getString(index);

            index = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID);
            long rawContactId = cursor.getLong(index);

            // Name
            if (ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE.equals(mime)) {

                index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME);
                String displayName = cursor.getString(index);

                index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);
                String familyName = cursor.getString(index);

                index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
                String givenName = cursor.getString(index);

                Contact contact = table.get(rawContactId);
                if (contact == null) {
                    contact = new Contact();
                    table.put(new Long(rawContactId), contact);

                    contact.setSerialNumber(rawContactId);
                }

                contact.setLastName(familyName);
                contact.setFirstName(givenName);
                // TODO: check below.     update - he.cao - use display name
                contact.setFullName(displayName);

                // Phone
            } else if (ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE.equals(mime)) {

//				index = cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID);
//				long rawContactId = cursor.getLong(index);

                index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                int phoneType = cursor.getInt(index);

                index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = cursor.getString(index);

                Contact contact = table.get(rawContactId);
                if (contact == null) {
                    contact = new Contact();
                    table.put(new Long(rawContactId), contact);

                    contact.setSerialNumber(rawContactId);
                }

                String phoneLabel = null;
                switch (phoneType) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        phoneLabel = Contact.Phone.LABEL_MOBILE;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        phoneLabel = Contact.Phone.LABEL_WORK;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        phoneLabel = Contact.Phone.LABEL_HOME;
                        break;
                    default:
                    case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                        phoneLabel = Contact.Phone.LABEL_OTHER;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
                        index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LABEL);
                        phoneLabel = cursor.getString(index);
                        break;
                }

                contact.addPhone(phoneNumber, phoneLabel, phoneType);

            } else if (ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE.equals(mime)) {

//				index = cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID);
//				long rawContactId = cursor.getLong(index);

                index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                String email = cursor.getString(index);

                index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE);
                int emailType = cursor.getInt(index);

                Contact contact = table.get(rawContactId);
                if (contact == null) {
                    contact = new Contact();
                    table.put(new Long(rawContactId), contact);

                    contact.setSerialNumber(rawContactId);
                }

                String emailLabel = null;
                switch (emailType) {
                    case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                        emailLabel = Contact.EMail.LABEL_MOBILE;
                        break;
                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                        emailLabel = Contact.EMail.LABEL_HOME;
                        break;
                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                        emailLabel = Contact.EMail.LABEL_WORK;
                        break;
                    default:
                    case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                        emailLabel = Contact.EMail.LABEL_OTHER;
                        break;
                    case ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM:
                        index = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.LABEL);
                        emailLabel = cursor.getString(index);
                        break;
                }

                contact.addEMail(email, emailLabel, emailType);

            } else {

            }


        } while (cursor.moveToNext());

        Collection<Contact> col = table.values();
        Contact[] contacts = new Contact[col.size()];
        col.toArray(contacts);

        return contacts;
    }



}

*/
