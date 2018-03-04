package com.zxu.cniao5shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;



import com.zxu.cniao5shop.mine.User;

public class BaseActivity extends AppCompatActivity {



    public void startActivity(Intent intent,boolean isNeedLogin){


        if(isNeedLogin){

            User user =CniaoApplication.getInstance().getUser();
            if(user !=null){
                super.startActivity(intent);
            }
            else{

                CniaoApplication.getInstance().putIntent(intent);
                Intent loginIntent = new Intent(this
                        , LoginActivity.class);
                super.startActivity(intent);

            }

        }
        else{
            super.startActivity(intent);
        }

    }
}
