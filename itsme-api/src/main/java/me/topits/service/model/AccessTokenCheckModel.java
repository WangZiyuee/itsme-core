package me.topits.service.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author QingKe
 * @date 2020-09-14 17:29
 **/
@Data
@Accessors(chain = true)
public class AccessTokenCheckModel {
    private boolean isValid = false;
    private String errorMessage;
    private Long userId;
}
