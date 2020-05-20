package me.topits.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Wang Ziyue
 * @since 2020/5/20 21:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BaseResponse {
    private String code;
    private String message;
    private String bizCode;
    private String bizMessage;
}
