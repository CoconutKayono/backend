package com.coconut.backend.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coconut.backend.entity.dto.Premium;
import com.coconut.backend.service.PremiumService;
import com.coconut.backend.mapper.mapper.PremiumMapper;
import org.springframework.stereotype.Service;

@Service
public class PremiumServiceImpl extends ServiceImpl<PremiumMapper, Premium>
    implements PremiumService{

}




