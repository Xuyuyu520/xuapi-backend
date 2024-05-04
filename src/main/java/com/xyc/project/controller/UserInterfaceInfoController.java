package com.xyc.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.xyc.project.annotation.AuthCheck;
import com.xyc.project.common.*;
import com.xyc.project.constant.CommonConstant;
import com.xyc.project.exception.BusinessException;
import com.xyc.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.xyc.project.model.dto.userinterfaceinfo.UserInterfaceInfoInvokeRequest;
import com.xyc.project.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.xyc.project.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.xyc.project.model.entity.User;
import com.xyc.project.model.entity.UserInterfaceInfo;
import com.xyc.project.model.enums.UserInterfaceInfoStatusEnum;
import com.xyc.project.service.UserInterfaceInfoService;
import com.xyc.project.service.UserService;
import com.xyc.xuapiclientsdk.client.XuApiClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author yupi
 */
@RestController
@RequestMapping("/userInterfaceInfo")
@Slf4j
public class UserInterfaceInfoController {

	@Resource
	private UserInterfaceInfoService userInterfaceInfoService;

	@Resource
	private UserService userService;

	@Resource
	private XuApiClient xuApiClient;
	// region 增删改查

	/**
	 * 创建
	 *
	 * @param userInterfaceInfoAddRequest
	 * @param request
	 * @return
	 */
	@PostMapping("/add")
	public BaseResponse<Long> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest, HttpServletRequest request) {
		if (userInterfaceInfoAddRequest == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
		BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
		// 校验
		userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, true);
		User loginUser = userService.getLoginUser(request);
		userInterfaceInfo.setUserId(loginUser.getId());
		boolean result = userInterfaceInfoService.save(userInterfaceInfo);
		if (!result) {
			throw new BusinessException(ErrorCode.OPERATION_ERROR);
		}
		long newUserInterfaceInfoId = userInterfaceInfo.getId();
		return ResultUtils.success(newUserInterfaceInfoId);
	}

	/**
	 * 删除
	 *
	 * @param deleteRequest
	 * @param request
	 * @return
	 */
	@PostMapping("/delete")
	public BaseResponse<Boolean> deleteUserInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
		if (deleteRequest == null || deleteRequest.getId() <= 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		User user = userService.getLoginUser(request);
		long id = deleteRequest.getId();
		// 判断是否存在
		UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
		if (oldUserInterfaceInfo == null) {
			throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
		}
		// 仅本人或管理员可删除
		if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
			throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
		}
		boolean b = userInterfaceInfoService.removeById(id);
		return ResultUtils.success(b);
	}

	/**
	 * 更新
	 *
	 * @param userInterfaceInfoUpdateRequest
	 * @param request
	 * @return
	 */
	@PostMapping("/update")
	public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest,
	                                                 HttpServletRequest request) {
		if (userInterfaceInfoUpdateRequest == null || userInterfaceInfoUpdateRequest.getId() <= 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
		BeanUtils.copyProperties(userInterfaceInfoUpdateRequest, userInterfaceInfo);
		// 参数校验
		userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, false);
		User user = userService.getLoginUser(request);
		long id = userInterfaceInfoUpdateRequest.getId();
		// 判断是否存在
		UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
		if (oldUserInterfaceInfo == null) {
			throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
		}
		// 仅本人或管理员可修改
		if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
			throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
		}
		boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
		return ResultUtils.success(result);
	}

	/**
	 * 根据 id 获取
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/get")
	public BaseResponse<UserInterfaceInfo> getUserInterfaceInfoById(long id) {
		if (id <= 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
		return ResultUtils.success(userInterfaceInfo);
	}

	/**
	 * 获取列表（仅管理员可使用）
	 *
	 * @param userInterfaceInfoQueryRequest
	 * @return
	 */
	@AuthCheck(mustRole = "admin")
	@GetMapping("/list")
	public BaseResponse<List<UserInterfaceInfo>> listUserInterfaceInfo(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest) {
		UserInterfaceInfo userInterfaceInfoQuery = new UserInterfaceInfo();
		if (userInterfaceInfoQueryRequest != null) {
			BeanUtils.copyProperties(userInterfaceInfoQueryRequest, userInterfaceInfoQuery);
		}
		QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userInterfaceInfoQuery);
		List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.list(queryWrapper);
		return ResultUtils.success(userInterfaceInfoList);
	}

	/**
	 * 分页获取列表
	 *
	 * @param userInterfaceInfoQueryRequest
	 * @param request
	 * @return
	 */
	@GetMapping("/list/page")
	public BaseResponse<Page<UserInterfaceInfo>> listUserInterfaceInfoByPage(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest, HttpServletRequest request) {
		if (userInterfaceInfoQueryRequest == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo userInterfaceInfoQuery = new UserInterfaceInfo();
		BeanUtils.copyProperties(userInterfaceInfoQueryRequest, userInterfaceInfoQuery);
		long current = userInterfaceInfoQueryRequest.getCurrent();
		long size = userInterfaceInfoQueryRequest.getPageSize();
		String sortField = userInterfaceInfoQueryRequest.getSortField();
		String sortOrder = userInterfaceInfoQueryRequest.getSortOrder();

		// description 需支持模糊搜索

		// 限制爬虫
		if (size > 50) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userInterfaceInfoQuery);

		queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
				sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
		Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size), queryWrapper);
		return ResultUtils.success(userInterfaceInfoPage);
	}

	// endregion

	/**
	 * 上线
	 *
	 * @param idRequest
	 * @param request
	 * @return
	 */
	@AuthCheck(mustRole = "admin")
	@PostMapping("/online")
	public BaseResponse<Boolean> onlineUserInterfaceInfo(@RequestBody IdRequest idRequest,
	                                                 HttpServletRequest request) {
		// 判断是否为空
		if (idRequest == null || idRequest.getId() <= 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		// 判断接口是否存在
		long id = idRequest.getId();
		UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
		if (oldUserInterfaceInfo == null) {
			throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
		}
		// 判断接口是否可以调用
		com.xyc.xuapiclientsdk.model.User user = new com.xyc.xuapiclientsdk.model.User();
		user.setUsername("test");
		String username = xuApiClient.getUsernameByPost(user);
		if (StringUtils.isBlank(username)) {
			throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口验证失败");
		}

		// 仅本人或管理员可修改
		UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
		userInterfaceInfo.setUserId(id);
		userInterfaceInfo.setStatus(UserInterfaceInfoStatusEnum.ONLINE.getValue());
		boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
		return ResultUtils.success(result);
	}

	/**
	 * 下线
	 *
	 * @param idRequest
	 * @param request
	 * @return
	 */
	@AuthCheck(mustRole = "admin")
	@PostMapping("/offline")
	public BaseResponse<Boolean> offlineUserInterfaceInfo(@RequestBody IdRequest idRequest,
	                                                  HttpServletRequest request) {
		// 判断是否为空
		if (idRequest == null || idRequest.getId() <= 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		// 判断接口是否存在
		long id = idRequest.getId();
		UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
		if (oldUserInterfaceInfo == null) {
			throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
		}

		// 仅本人或管理员可修改
		UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
		userInterfaceInfo.setUserId(id);
		userInterfaceInfo.setStatus(UserInterfaceInfoStatusEnum.OFFLINE.getValue());
		boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
		return ResultUtils.success(result);
	}

	/**
	 *
	 * @param userInterfaceInfoInvokeRequest
	 * @param request
	 * @return
	 */
	@PostMapping("/invoke")
	public BaseResponse<Object> invokeUserInterfaceInfo(@RequestBody UserInterfaceInfoInvokeRequest userInterfaceInfoInvokeRequest,
	                                                HttpServletRequest request) {
		// 判断是否为空
		if (userInterfaceInfoInvokeRequest == null || userInterfaceInfoInvokeRequest.getId() <= 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		// 判断接口是否存在
		long id = userInterfaceInfoInvokeRequest.getId();
		String userRequestParams = userInterfaceInfoInvokeRequest.getUserRequestParams();
		UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
		if (oldUserInterfaceInfo == null) {
			throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
		}
		if (oldUserInterfaceInfo.getStatus() == UserInterfaceInfoStatusEnum.OFFLINE.getValue()) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口状态未启用");
		}
		// 仅本人或管理员可修改
		User loginUser = userService.getLoginUser(request);
		String accessKey = loginUser.getAccessKey();
		String secretKey = loginUser.getSecretKey();
		XuApiClient tempClient = new XuApiClient(accessKey, secretKey);
		Gson gson = new Gson();
		com.xyc.xuapiclientsdk.model.User user = gson.fromJson(userRequestParams, com.xyc.xuapiclientsdk.model.User.class);
		String usernameByPost = tempClient.getUsernameByPost(user);
		return ResultUtils.success(usernameByPost);
	}

}
