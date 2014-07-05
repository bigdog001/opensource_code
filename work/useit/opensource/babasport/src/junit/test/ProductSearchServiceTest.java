package junit.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.QueryResult;
import cn.itcast.bean.product.ProductInfo;
import cn.itcast.service.book.GeneratedOrderidService;
import cn.itcast.service.book.OrderService;
import cn.itcast.service.product.ProductSearchService;

public class ProductSearchServiceTest {
	private static ProductSearchService productSearchService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
			productSearchService = (ProductSearchService)cxt.getBean("productSearchServiceBean");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Test
	public void testQuery() {
		QueryResult<ProductInfo> qr = productSearchService.query("´«ÖÇ²¥¿Í", 0, 5);
		for(ProductInfo product : qr.getResultlist()){
			System.out.println(product.getName());
		}
		System.out.println(qr.getTotalrecord());
	}

}
