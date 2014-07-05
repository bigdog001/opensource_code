package cn.itcast.service.product;

import java.util.List;

import cn.itcast.bean.product.ProductType;
import cn.itcast.service.base.DAO;


public interface ProductTypeService extends DAO<ProductType> {
	/**
	 * 获取下类别的id
	 * @param parentids 父类id数组
	 * @return
	 */
	public List<Integer> getSubTypeid(Integer[] parentids);
}