package com.example.retrofit_exam.retrotif

import android.util.Log
import com.example.retrofit_exam.utils.API
import com.example.retrofit_exam.utils.Constants.TAG
import com.example.retrofit_exam.utils.RESPONSE_STATE
import com.example.retrofit_exam.utils.SEARCH_TYPE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {

  companion object{
    val instance = RetrofitManager()
  }

  //레트로핏 인터페이스 가져오기
  private val iRetrofit : IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

  //사진검색 api 호출
  fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit){

    //searchTerm 값이 비어있으면 ""을 넣는다
    val term = searchTerm ?: ""

    //실제 api 호출 부분 (레트로핏 인터페이스의 searchPhotos 부분을 실행)
    val call = iRetrofit?.searchPhotos(searchTerm = term).let {
      it           //it으로 call 에 값을 넣어줌
    }?: return

    //레트로핏 콜백
    call.enqueue(object :retrofit2.Callback<JsonElement>{

      //응답 실패시
      override fun onFailure(call: Call<JsonElement>, t: Throwable) {
        Log.d(TAG, "RetrofitManager - onFailure() called / t: $t ")

        completion(RESPONSE_STATE.FAIL , t.toString()) //실패시 오류 뮨
      }

      //응답 성공시
      override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
        Log.d(TAG, "RetrofitManager - onResponse() called / response : ${response.body()}")

        completion(RESPONSE_STATE.OKAY , response.body().toString())

      }

    })

  }

}
