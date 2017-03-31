package com.jal.http.api;

import com.jal.mvp.entity.PhotoEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by SEELE on 2017/3/14.
 */

public interface PhotoController {

    @GET("data/福利/{size}/{startPage}")
    Observable<PhotoEntity> getPhotoList(
            @Path("size") int size, @Path("startPage") int startPage
    );

}
