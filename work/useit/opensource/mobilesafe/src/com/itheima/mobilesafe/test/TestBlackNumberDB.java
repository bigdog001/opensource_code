package com.itheima.mobilesafe.test;

import java.util.List;
import java.util.Random;

import android.test.AndroidTestCase;

import com.itheima.mobilesafe.db.BlackNumberDBOpenHelper;
import com.itheima.mobilesafe.db.dao.BlackNumberDao;
import com.itheima.mobilesafe.domain.BlackNumberInfo;

public class TestBlackNumberDB extends AndroidTestCase {
	
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	public void testCreateDB() throws Exception {
		BlackNumberDBOpenHelper helper = new BlackNumberDBOpenHelper(
				getContext());
		helper.getWritableDatabase();
	}

	public void testAdd() throws Exception {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		Random random = new Random();
		for (int i = 0; i < 200; i++) {
			long number = 13512340000l;
			dao.add(Long.toString(number+i), Integer.toString(random.nextInt(3)+1));
		}
	}

	public void testFind() throws Exception {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		boolean result = dao.find("123456");
		assertEquals(true, result);
	}

	public void testUpdate() throws Exception {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.update("123456", "2");
	}

	public void testDelete() throws Exception {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.delete("123456");
	}

	public void testFindAll() throws Exception {
		BlackNumberDao dao = new BlackNumberDao(getContext());
		List<BlackNumberInfo> infos = dao.findAll();
		for(BlackNumberInfo info: infos){
			System.out.println(info.toString());
		}
	}
}
