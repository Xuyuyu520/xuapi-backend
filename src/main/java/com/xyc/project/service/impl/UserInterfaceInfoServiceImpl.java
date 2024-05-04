package com.xyc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyc.project.mapper.UserInterfaceInfoMapper;
import com.xyc.project.model.entity.UserInterfaceInfo;
import com.xyc.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
 * @author xuyucheng
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2024-05-04 10:17:01
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements UserInterfaceInfoService {

	@Override
	public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b) {

	}
}




