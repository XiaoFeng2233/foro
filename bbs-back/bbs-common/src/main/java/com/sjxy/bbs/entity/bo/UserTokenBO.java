package com.sjxy.bbs.entity.bo;

import com.sjxy.bbs.entity.po.UserPO;
import lombok.Data;

import java.util.List;

@Data
public class UserTokenBO extends UserPO {
    private List<Long> mangedTagIds;
}
