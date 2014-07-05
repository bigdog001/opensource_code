package com.bigdog.server.web.service.impl;

import com.bigdog.server.web.action.base.FinderBaseAction;
import com.bigdog.server.web.bean.TAddress;
import com.bigdog.server.web.bean.TrackerBean;
import com.bigdog.server.web.bean.UserBean;
import com.bigdog.server.web.bean.base.FinderBaseBean;
import com.bigdog.server.web.cfg.Config;
import com.bigdog.server.web.dao.TAddressDao;
import com.bigdog.server.web.dao.TrackerDao;
import com.bigdog.server.web.dao.UserDao;
import com.bigdog.server.web.service.TrackerService;
import com.bigdog.server.web.util.Tools;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 12:17 PM
 */
@Service("trackerService")
public class TrackerServiceImpl implements TrackerService {
    Logger logger = Logger.getLogger(this.getClass());
    private FinderBaseAction finderBaseAction;

    @Resource
    private TrackerDao trackerDao;

    @Resource
    private TAddressDao taddressDao;

    @Resource
    private UserDao userDao;

    public TrackerDao getTrackerDao() {
        return trackerDao;
    }

    public void setTrackerDao(TrackerDao trackerDao) {
        this.trackerDao = trackerDao;
    }

    @Override
    public void setBaseAction(FinderBaseAction finderBaseAction) {
        this.finderBaseAction = finderBaseAction;
    }

    public void getTrackerId() {
        String mac = finderBaseAction.getFinderBaseBean().getMac();
        logger.info("mac:" + mac);
        if ("".equals(mac) || mac == null) {
            JSONObject obj = new JSONObject();
            obj.put(Config.msg_codeName, Config.error_code_emptyMac);
            obj.put(Config.MsgName, Config.error_code_emptyMacMsg);
            finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
        } else {
            TrackerBean trackerBean = trackerDao.getByMac(mac);
            if (trackerBean == null) {
                JSONObject obj = new JSONObject();
                obj.put("mac", mac);
                obj.put("t_status", "0");
                String tid = Tools.GenerateTid();
                while (true) {
                    if (trackerDao.getByTid(tid) != null) {
                        tid = Tools.GenerateTid();
                    } else {
                        break;
                    }
                }
                obj.put("tid", tid);
                //此时得到一个全新的tid,立即入库===============================
                TrackerBean tb = new TrackerBean();
                tb.setId(tid);
                tb.setMac(mac);
                tb.setT_status(1);
                tb.setUserBean(null);
                tb.settAddresses(null);
                trackerDao.save(tb);
                //===============================此时得到一个全新的tid,立即入库
                finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
            } else {
                JSONObject obj = new JSONObject();
                obj.put("mac", mac);
                obj.put("t_status", "1");
                obj.put("tid", trackerBean.getId());
                finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
            }
        }
    }

    @Override
    public void saveTrackerPosition() {
        Long latitude = finderBaseAction.getFinderBaseBean().getLatitude();
        Long lonitude = finderBaseAction.getFinderBaseBean().getLonitude();
        Long detacte_time = finderBaseAction.getFinderBaseBean().getDetacte_time();
        String tid = finderBaseAction.getFinderBaseBean().getTid();
//        logger.info("latitude:"+latitude+",lonitude:"+lonitude+",detacte_time:"+detacte_time+",tid:"+tid);
        if (latitude != 0L && lonitude != 0L && detacte_time != 0L && !"".equals(tid) && tid != null) {
            if (trackerDao.getByTid(tid.trim()) != null) {
                TAddress tAddress = new TAddress();
                tAddress.setLatitude(latitude);
                tAddress.setLonitude(lonitude);
                tAddress.setDetacte_time(detacte_time);
                taddressDao.save(tAddress, tid.trim());
                JSONObject obj = new JSONObject();
                obj.put(Config.msg_codeName, "OK");
                finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
            } else {
                JSONObject obj = new JSONObject();
                obj.put(Config.msg_codeName, "FAILED");
                obj.put(Config.MsgName, "tid error");
                finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
            }

        } else {
            JSONObject obj = new JSONObject();
            obj.put(Config.error_codeName, Config.error_code_wrrong_position);
            obj.put(Config.error_MsgName, Config.error_code_wrrong_positionMsg);
            JSONObject obj_de = new JSONObject();
            obj_de.put("latitude", latitude);
            obj_de.put("lonitude", lonitude);
            obj_de.put("detacte_time", detacte_time);
            obj.put("errDesc", obj_de);
            finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
        }

    }

    @Override
    public void batchTrackerLocation() {
        String batch_location = finderBaseAction.getFinderBaseBean().getBatch_location();
        String tid = finderBaseAction.getFinderBaseBean().getTid();
        if (!"".equals(batch_location) && batch_location != null && !"".equals(tid) && tid != null) {
//            logger.info(batch_location);
            String[] b_location = batch_location.split(",");
            if (b_location == null) {
                JSONObject obj = new JSONObject();
                obj.put(Config.error_codeName, "BNO");
                obj.put(Config.error_MsgName, "批量格式非法,you need the format:ec:55:f9:c0:03:32#27813054#-80425241#2L3QR92I#13990095556772");
                finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
            } else {
                List<TAddress> tAddresses = new ArrayList<TAddress>();
                for (String l : b_location) {
                    String[] batch_l = l.split("#");
                    if (batch_l == null || batch_l.length != 5) {
                        logger.info("批量格式非法,you need the format:ec:55:f9:c0:03:32#27813054#-80425241#2L3QR92I#13990095556772");
                    } else {
                        TAddress tAddress = new TAddress();
                        String mac = batch_l[0];
                        String latitude = batch_l[1];
                        String lonitude = batch_l[2];
                        String tid_ = batch_l[3]; //目前废弃
                        String detacte_time = batch_l[4];
                        long lat = 0;
                        long lon = 0;
                        long dt = 0;
                        lat = Long.valueOf(latitude);
                        lon = Long.valueOf(lonitude);
                        dt = Long.valueOf(detacte_time);
                        tAddress.setLatitude(lat);
                        tAddress.setLonitude(lon);
                        tAddress.setDetacte_time(dt);
                        tAddresses.add(tAddress);
                    }
                }

                taddressDao.saveBatch(tAddresses, tid);
                JSONObject obj = new JSONObject();
                obj.put(Config.msg_codeName, "BOK");
                obj.put(Config.MsgName, "批量操作成功!");
                finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));

            }

        } else {
            JSONObject obj = new JSONObject();
            obj.put(Config.error_codeName, "RBTNO");
            obj.put(Config.error_MsgName, "批量操作失败，批量坐标不存在!");
            finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
        }
    }

    @Override
    public void Registe() {
        String username = finderBaseAction.getFinderBaseBean().getUserName();
        String passwd = finderBaseAction.getFinderBaseBean().getPasswd();
        if (!"".equals(username) && !"".equals(passwd) && passwd != null && username != null) {

            if (Tools.isEmail(username)) {
                passwd = Tools.getMD5(passwd);
                UserBean userBean = new UserBean();
                userBean.setUsername(username);
                userBean.setPasswd(passwd);

                UserBean userBean_ = userDao.login(userBean);
                if (userBean_ == null) {
                    userDao.save(userBean);
                    JSONObject obj = new JSONObject();
                    obj.put(Config.msg_codeName, "ROK");
                    obj.put(Config.MsgName, "注册成功!");
                    obj.put("uname", username);
                    finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
                } else {
                    JSONObject obj = new JSONObject();
                    obj.put(Config.error_codeName, "RRNO");
                    obj.put(Config.error_MsgName, "注册失败，用户名已经存在!");
                    finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
                }
            } else {
                JSONObject obj = new JSONObject();
                obj.put(Config.error_codeName, "RLNO");
                obj.put(Config.error_MsgName, "注册失败,用户名非邮箱地址!");
                finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));


            }


        } else {
            JSONObject obj = new JSONObject();
            obj.put(Config.error_codeName, "RNO");
            obj.put(Config.error_MsgName, "注册失败,用户名或密码缺失!");
            finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
        }


    }

    @Override
    public void Login() {
        String username = finderBaseAction.getFinderBaseBean().getUserName();
        String passwd = finderBaseAction.getFinderBaseBean().getPasswd();
        if (!"".equals(username) && !"".equals(passwd) && passwd != null && username != null) {
            UserBean userBean = new UserBean();
            passwd = Tools.getMD5(passwd);
            userBean.setUsername(username);
            userBean.setPasswd(passwd);
            UserBean userBean_ = userDao.login(userBean);
            if (userBean_ == null) {
                JSONObject obj = new JSONObject();
                obj.put(Config.error_codeName, "LNO");
                obj.put(Config.error_MsgName, "登录失败,密码错误!");
                finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
            } else {
                JSONObject obj = new JSONObject();
                obj.put(Config.msg_codeName, "LOK");
                obj.put(Config.MsgName, "登录成功!");
                obj.put("uid", userBean_.getId());
                obj.put("sid", Tools.getSessionId());
                finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
            }
        } else {
            JSONObject obj = new JSONObject();
            obj.put(Config.error_codeName, "LNO");
            obj.put(Config.error_MsgName, "登录失败,用户名或密码缺失!");
            finderBaseAction.setInputStream(Tools.getInputStream(obj.toString()));
        }

    }
}
