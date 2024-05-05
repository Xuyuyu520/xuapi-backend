package com.xyc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyc.xuapicommon.model.entity.UserInterfaceInfo;

/**
* @author xuyucheng
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-05-04 10:17:01
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
	/**
	 * 校验
	 * @param userInterfaceInfo
	 * @param b
	 */
	void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);

	/**
	 * 统计调用次数
	 *
	 * @param interfaceId
	 * @param userId
	 * @return
	 */
	boolean invokeCount(long interfaceId , long userId);
}
