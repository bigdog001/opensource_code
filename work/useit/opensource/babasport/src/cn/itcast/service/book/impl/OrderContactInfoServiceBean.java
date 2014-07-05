package cn.itcast.service.book.impl;

import org.springframework.stereotype.Service;

import cn.itcast.bean.book.OrderContactInfo;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.book.OrderContactInfoService;

@Service
public class OrderContactInfoServiceBean extends DaoSupport<OrderContactInfo>
		implements OrderContactInfoService {


}
