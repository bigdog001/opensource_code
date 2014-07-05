package cn.itcast.service.book.impl;

import org.springframework.stereotype.Service;

import cn.itcast.bean.book.Message;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.book.MessageService;

@Service
public class MessageServiceBean extends DaoSupport<Message> implements MessageService {

}
