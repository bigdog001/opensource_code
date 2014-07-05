package com.bigdog.server.web.crimedoc.service.impl;

import com.bigdog.server.web.crimedoc.dao.ArticleDao;
import com.bigdog.server.web.crimedoc.service.IndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/1/13
 * Time: 1:25 AM
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {
    @Resource
    private ArticleDao articleDao;
}
