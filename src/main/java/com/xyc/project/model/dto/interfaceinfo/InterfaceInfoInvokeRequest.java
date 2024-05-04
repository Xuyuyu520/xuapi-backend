package com.xyc.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口信息调用请求
 *
 * @author xuYuYu
 * @date 2024-05-03 14:24:17
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;



    /**
     * 用户传递请求参数
     */
    private String userRequestParams;


    private static final long serialVersionUID = -131312290152957754L;
}
