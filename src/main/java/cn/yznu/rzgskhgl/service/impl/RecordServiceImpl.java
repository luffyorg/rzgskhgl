package cn.yznu.rzgskhgl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.yznu.rzgskhgl.pojo.Record;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.IRecordService;

@Service("recordService")
public class RecordServiceImpl extends CommonServiceimpl implements IRecordService {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Override
	public void add(Record record) {
		commonService.saveOrUpdate(record);
	}

}
