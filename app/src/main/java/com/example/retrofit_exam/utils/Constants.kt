package com.example.retrofit_exam.utils

object Constants{
  const val  TAG : String = "로그"
}
enum class SEARCH_TYPE {
  PHOTO,
  USER
}

enum class RESPONSE_STATE {
  OKAY,
  FAIL
}

object API{
  const val BASE_URL : String = "https://api.unsplash.com/"

  //여긴 unsplash의 Access Key를 넣은 곳
  const val CLIENT_ID : String = "0MXAULFRyfF-FRcA8wGhi1718pmf2N9bMyXvlN9Gc9c"

  const val SEARCH_PHOTOS : String = "search/photos"
  const val SEARCH_USERS : String = "search/users"
}
