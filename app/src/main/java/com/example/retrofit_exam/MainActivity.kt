package com.example.retrofit_exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.retrofit_exam.retrotif.RetrofitManager
import com.example.retrofit_exam.utils.Constants
import com.example.retrofit_exam.utils.Constants.TAG
import com.example.retrofit_exam.utils.RESPONSE_STATE
import com.example.retrofit_exam.utils.SEARCH_TYPE
import com.example.retrofit_exam.utils.onMyTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_button_search.*

class MainActivity : AppCompatActivity() {

  private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    Log.d(Constants.TAG, "MainActivity - onCreate() called ")


    // RadioGroup 선택시 이벤트
    search_term_radio_group.setOnCheckedChangeListener { _, checkedId ->

      //switch 문
      when (checkedId) {
        R.id.photo_search_radio_btn -> {
          Log.d(TAG, "사진 검색 버튼 클릭!")
          search_term_text_layout.hint = "사진검색"
          search_term_text_layout.startIconDrawable =
            resources.getDrawable(R.drawable.ic_baseline_photo_library_24, resources.newTheme())
          this.currentSearchType = SEARCH_TYPE.PHOTO
        }

        R.id.user_search_radio_btn -> {
          Log.d(TAG, "사용자 검색 버튼 클릭!")
          search_term_text_layout.hint = "사용자 검색"
          search_term_text_layout.startIconDrawable =
            resources.getDrawable(R.drawable.ic_baseline_person_24, resources.newTheme())
          this.currentSearchType = SEARCH_TYPE.USER
        }
      }
      Log.d(
        TAG,
        " MainActivity - OnCheckedChange() called / currentSearchType : $currentSearchType "
      )
    }

    search_term_edit_text.onMyTextChanged {
      //입력된 글자가 하나라도 있다면
      if (it.toString().count() > 0) {

        //검색 버튼을 보여준다
        frame_search_btn.visibility = View.VISIBLE

        //EditText 밑에 있는 helptext 지우기
        search_term_text_layout.helperText = " "

        //스크롤뷰를 올라가있는 형태로 고정
        main_scrollview.scrollTo(0, 200)

      } else {
        frame_search_btn.visibility = View.INVISIBLE
      }

      if (it.toString().count() == 12) {
        Log.d(TAG, "MainActivity - 에러 띄우기")
        Toast.makeText(this, "검색어는 12자 까지만 입력이 가능합니다", Toast.LENGTH_SHORT).show()
      }
    }

    //검색 버튼 클릭시시
   btn_search.setOnClickListener{
      Log.d(TAG, "MainActivity - 검색 버튼이 클릭 됨 / currentSearchType : $currentSearchType")

     //검색 api 호출 (EditText에 있는 글자를 가져오는 부분)
     RetrofitManager.instance.searchPhotos(searchTerm = search_term_edit_text.toString(), completion = {
       responseState, responseBody ->

       when(responseState){

         RESPONSE_STATE.OKAY -> {
           Toast.makeText(this , "api 호출에 성공 하셨습니다" , Toast.LENGTH_SHORT).show()
           Log.d(TAG, "api 호출 성공 : $responseBody")
        }
         RESPONSE_STATE.FAIL ->{
           Toast.makeText(this , "api 호출 에러입니다." , Toast.LENGTH_SHORT).show()
           Log.d(TAG, "api 호출 실패 : $responseBody")
         }

       }
     })

      this.handleSearchButtonUi()
    }


  }//onCreate

  private fun handleSearchButtonUi(){

    btn_progress.visibility = View.VISIBLE
    btn_search.text = ""

    Handler().postDelayed({
      btn_progress.visibility = View.INVISIBLE
      btn_search.text = "검색"
    },1500)
  }
}
