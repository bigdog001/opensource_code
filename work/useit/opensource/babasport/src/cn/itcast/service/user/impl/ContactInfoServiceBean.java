package cn.itcast.service.user.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.user.ContactInfo;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.user.ContactInfoService;


@Service @Transactional
public class ContactInfoServiceBean extends DaoSupport<ContactInfo> implements ContactInfoService{

}
