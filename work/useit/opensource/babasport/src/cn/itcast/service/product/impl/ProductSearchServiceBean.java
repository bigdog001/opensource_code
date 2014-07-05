package cn.itcast.service.product.impl;

import javax.annotation.Resource;

import org.compass.core.Compass;
import org.compass.core.CompassTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.QueryResult;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.service.product.ProductSearchService;

@Service @Transactional
public class ProductSearchServiceBean implements ProductSearchService {
	private CompassTemplate compassTemplate;
	
	@Resource
	public void setCompass(Compass compass){
		this.compassTemplate = new CompassTemplate(compass);
	}
	
	public QueryResult<ProductInfo> query(String keyword, int firstResult, int maxResult){
		return compassTemplate.execute(new QueryCallback(keyword, firstResult, maxResult));
	}
}
