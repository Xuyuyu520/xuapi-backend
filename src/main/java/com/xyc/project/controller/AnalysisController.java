package com.xyc.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyc.project.annotation.AuthCheck;
import com.xyc.project.common.BaseResponse;
import com.xyc.project.common.ErrorCode;
import com.xyc.project.common.ResultUtils;
import com.xyc.project.exception.BusinessException;
import com.xyc.project.mapper.InterfaceInfoMapper;
import com.xyc.project.mapper.UserInterfaceInfoMapper;
import com.xyc.project.model.vo.InterfaceInfoVO;
import com.xyc.project.service.InterfaceInfoService;
import com.xyc.xuapicommon.model.entity.InterfaceInfo;
import com.xyc.xuapicommon.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: xuYuYu
 * @createTime: 2024/5/5 17:01
 * @Description: TODO 分析数据控制层
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {
	@Resource
	private UserInterfaceInfoMapper userInterfaceInfoMapper;
	@Resource
	private InterfaceInfoService interfaceInfoService;

	@GetMapping("/top/interface/invoke")
	@AuthCheck(mustRole = "admin")
	public BaseResponse<List<InterfaceInfoVO>> listToPInterfaceInvoke() {
		//取出接口信息 限制3条
		List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listToPInterfaceInvoke(3);
		//根据  用户接口信息表id 接口进行分组
		Map<Long, List<UserInterfaceInfo>> interfaceInfoObjMap = userInterfaceInfoList.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
		//批量查询根据id
		QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<InterfaceInfo>().in("id", interfaceInfoObjMap.keySet());
		List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
		//判断是否有数据 否则异常
		if (CollectionUtils.isEmpty(interfaceInfoList)) {
			throw new BusinessException(ErrorCode.SYSTEM_ERROR);
		}
		// InterfaceInfo 装成 InterfaceInfoVO
		List<InterfaceInfoVO> interfaceInfoVOList = interfaceInfoList.stream().map(interfaceInfo -> {
			InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
			BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
			int totalNum = interfaceInfoObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
			return interfaceInfoVO;
		}).collect(Collectors.toList());
		//返回数据
		return ResultUtils.success(interfaceInfoVOList);
	}
}
