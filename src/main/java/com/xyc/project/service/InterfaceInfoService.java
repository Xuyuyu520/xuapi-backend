package com.xyc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyc.xuapicommon.model.entity.InterfaceInfo;

/**
* @author xuyucheng
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-05-02 13:54:22
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

	/**
	 * 有效接口信息 校验
	 *
	 * @param interfaceInfo 接口信息
	 * @param b             b
	 */
	 void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b);
}
