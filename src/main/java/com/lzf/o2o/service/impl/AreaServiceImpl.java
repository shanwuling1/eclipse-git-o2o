package com.lzf.o2o.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lzf.o2o.dao.AreaDao;
import com.lzf.o2o.entity.Area;
import com.lzf.o2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;

	@Override
	public List<Area> getAreaList() throws JsonParseException,
			JsonMappingException, IOException {
		return areaDao.queryArea();
	}

}
