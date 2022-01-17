package com.example.retrofit_exam.retrotif

import com.example.retrofit_exam.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IRetrofit {

  // https://www.unsplash.com/search/photos/?query=""

  //api 엔드포인트는 Constants에 API에 있ㄷ
  @GET(API.SEARCH_PHOTOS)
  fun searchPhotos(@Query("query") searchTerm: String) : Call<JsonElement>

  @GET(API.SEARCH_USERS)
  fun searchUsers(@Query("query") searchTerm: String) : Call<JsonElement>



  //이건 to_drop_api 호출 엔드 포인트
  @POST("/auth/register")
  fun signUp()

}
