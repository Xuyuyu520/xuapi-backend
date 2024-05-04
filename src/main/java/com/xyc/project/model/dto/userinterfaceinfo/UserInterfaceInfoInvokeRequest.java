package com.xyc.project.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口信息调用请求
 *
 * @author xuYuYu
 * @date 2024-05-03 14:24:17
 */
@Data
public class UserInterfaceInfoInvokeRequest implements Serializable {
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
