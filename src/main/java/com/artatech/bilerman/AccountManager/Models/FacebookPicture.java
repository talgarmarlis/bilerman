package com.artatech.bilerman.AccountManager.Models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class FacebookPicture {

    private FacebookPictureData data;

    public FacebookPictureData getData() {
        return data;
    }

    public void setData(FacebookPictureData data) {
        this.data = data;
    }
}
