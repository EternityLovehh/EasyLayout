package com.shufa.easylayout.easylayout;

import cn.jiguang.imui.commons.models.IUser;

/**
 * Created by Administrator on 2018/4/24 0024.
 */

public class DefaultUser implements IUser {
    private String id;
    private String displayName;
    private String avatar;

    public DefaultUser(String id, String displayName, String avatar) {
        this.id = id;
        this.displayName = displayName;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getAvatarFilePath() {
        return avatar;
    }
}
