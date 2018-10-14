package com.lzf.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lzf.o2o.BaseTest;
import com.lzf.o2o.entity.Area;

public class AreaServiceTest extends BaseTest{
	@Autowired
	private AreaService areaService;
	@Test
	public void testGetAreaList() {
		try {
			List<Area> aList = areaService.getAreaList();
			assertEquals("东苑", aList.get(0).getAreaName());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
	}
}
