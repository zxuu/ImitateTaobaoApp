package com.zxu.cniao5shop;

/**
 * Created by zx on 2018/3/1.
 */

public class Contants {


    public static final String  COMPAINGAIN_ID="compaigin_id";
    public static final String  WARE="ware";

    public static final String USER_JSON="user_json";
    public static final String TOKEN="token";

    public  static final String DES_KEY="Cniao5_123456";

    public  static final int REQUEST_CODE=0;




    public static class API {

        public static final String BASE_URL = "http://112.124.22.238:8081/course_api/";

        public static final String CAMPAIGN_HOME = BASE_URL + "campaign/recommend";

        public static final String WARES_HOT=BASE_URL + "wares/hot";

        public static final String BANNER=BASE_URL +"banner/query";

        public static final String WARES_LIST=BASE_URL +"wares/list";

        public static final String CATEGORY_LIST=BASE_URL +"category/list";


        public static final String LOGIN=BASE_URL +"auth/login";

        public static final String USER_DETAIL=BASE_URL +"user/get?id=1";




    }
}
