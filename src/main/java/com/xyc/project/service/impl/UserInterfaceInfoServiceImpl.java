package com.xyc.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyc.project.common.ErrorCode;
import com.xyc.project.exception.BusinessException;
import com.xyc.project.mapper.UserInterfaceInfoMapper;
import com.xyc.project.service.UserInterfaceInfoService;
import com.xyc.xuapicommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xuyucheng
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2024-05-04 10:17:01
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements UserInterfaceInfoService {

	@Override
	public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
		if (userInterfaceInfo == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		Long id = userInterfaceInfo.getId();
		Long userId = userInterfaceInfo.getUserId();
		Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
		Integer totalNum = userInterfaceInfo.getTotalNum();
		Integer leftNum = userInterfaceInfo.getLeftNum();
		Integer status = userInterfaceInfo.getStatus();

		// 添加校验规则
		if (add) {
			// 对于添加操作，检查必填字段是否为空
			if (userInterfaceInfo.getInterfaceInfoId() < 0 || userInterfaceInfo.getUserId() <= 0) {
				throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户或者接口不存在");
			}
		}

		// 校验名称长度
		if (userInterfaceInfo.getLeftNum() < 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
		}


	}

	/**
	 * 统计调用次数
	 *
	 * @param interfaceId
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean invokeCount(long interfaceId, long userId) {
		if (interfaceId < 0 || userId < 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("userId", userId);
		updateWrapper.eq("interfaceInfoId", interfaceId);
		updateWrapper.gt("leftNum", 0);
		updateWrapper.setSql("leftNum = leftNum - 1 ,totalNum = totalNum + 1 ");
		return this.update(updateWrapper);

	}
}




