package me.topits.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author QingKe
 * @date 2020-09-14 17:46
 **/
@Data
@Accessors(chain = true)
public class AccessTokenModel {
    private Long userId;
    private Long timestamp;
    private String md5;

    public String getTokenInfo() {
        return userId + "_" + timestamp;
    }
}
