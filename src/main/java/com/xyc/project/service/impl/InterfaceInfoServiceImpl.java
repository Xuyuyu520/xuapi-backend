package com.xyc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyc.project.common.ErrorCode;
import com.xyc.project.exception.BusinessException;
import com.xyc.project.model.entity.InterfaceInfo;
import com.xyc.project.service.InterfaceInfoService;
import com.xyc.project.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author xuyucheng
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2024-05-02 13:54:22
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
		implements InterfaceInfoService {

	/**
	 * 有效接口信息 校验
	 *
	 * @param interfaceInfo 接口信息
	 * @param add           add
	 */
	@Override
	public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
		if (interfaceInfo == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String name = interfaceInfo.getName();
		String url = interfaceInfo.getUrl();
		String requestParams = interfaceInfo.getRequestParams();
		String requestHeader = interfaceInfo.getRequestHeader();
		String responseHeader = interfaceInfo.getResponseHeader();
		String method = interfaceInfo.getMethod();
		// 添加校验规则
		if (add) {
			// 对于添加操作，检查必填字段是否为空
			if (StringUtils.isAnyBlank(name, url, method)) {
				throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称、URL和请求类型不能为空");
			}
		}

		// 校验名称长度
		if (StringUtils.isNotBlank(name) && name.length() > 50) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
		}

		// 校验URL格式
		if (StringUtils.isNotBlank(url) && !url.matches("^http?://.*")) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR, "URL格式不正确");
		}

		// 校验请求参数格式
		// 这里假设请求参数是 JSON 格式，你可以根据实际情况修改校验逻辑
		if (StringUtils.isNotBlank(requestParams)) {
			try {
				// 尝试解析请求参数为 JSON 对象，如果失败则抛出异常
				new JSONObject(requestParams);
			} catch (JSONException e) {
				throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数格式不正确");
			}
		}

		// 校验请求头格式
		// 同样，这里假设请求头是 JSON 格式，你可以根据实际情况修改校验逻辑
		if (StringUtils.isNotBlank(requestHeader)) {
			try {
				new JSONObject(requestHeader);
			} catch (JSONException e) {
				throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求头格式不正确");
			}
		}

		// 校验响应头格式
		// 同样，这里假设响应头是 JSON 格式，你可以根据实际情况修改校验逻辑
		if (StringUtils.isNotBlank(responseHeader)) {
			try {
				new JSONObject(responseHeader);
			} catch (JSONException e) {
				throw new BusinessException(ErrorCode.PARAMS_ERROR, "响应头格式不正确");
			}
		}
	}
}




