package me.topits.datasource;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;

/**
 * @author Wang Ziyue
 * @date 2020-08-19 15:50
 * 写库Service基础实现(默认master/write)
 **/
@DS("write")
@Service
public class WriteBaseServiceImpl {
}
