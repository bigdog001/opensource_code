package cn.itcast.service.product.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.product.Brand;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.product.BrandService;


@Service
@Transactional
public class BrandServiceBean extends DaoSupport<Brand> implements BrandService {

	@Override
	public void save(Brand entity) {
		entity.setCode(UUID.randomUUID().toString());
		super.save(entity);
	}
	
}
