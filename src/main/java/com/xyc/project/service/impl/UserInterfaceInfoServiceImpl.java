package com.xyc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UserInterfaceInfo;
import com.xyc.project.service.UserInterfaceInfoService;
import com.xyc.project.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 27178
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-05-04 10:17:01
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

}




