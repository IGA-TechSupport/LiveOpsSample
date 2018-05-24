package tcs.abx.liveopssample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.igaworks.IgawCommon;
import com.igaworks.liveops.IgawLiveOps;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Button getLocalPushBtn = findViewById(R.id.get_local_push_btn);
        Button disablePushBtn = findViewById(R.id.push_disable_btn);
        Button enablePushBtn = findViewById(R.id.push_enable_btn);

        // 1. SET USER ID : mandatory
        IgawCommon.setUserId(this,"user12345");

        // 2. INIT LIVEOPS SDK : mandatory
        IgawLiveOps.initialize(this);

        // 4. SET onNewIntent to support Deeplinking by push
        onNewIntent(getIntent());


        // Local Push Sample : optional
        getLocalPushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IgawLiveOps.setNormalClientPushEvent(
                        getApplicationContext(),			// Application Context
                        1,                 	// Delay seconds. 몇 초 뒤에 보낼지 설정
                        "Let’s play now!",      // 전송할 메시지
                        1,			// Event ID, 취소할 때 쓰기 위한 값.
                        false			// 앱이 실행 중일 때에도 보이게 할 것인지 설정
                );
            }
        });


        // Enable Push
        enablePushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IgawLiveOps.enableService(MainActivity.this, true);
                Toast.makeText(MainActivity.this,"Push service enabled", Toast.LENGTH_SHORT).show();
            }
        });

        // Disable Push
        disablePushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IgawLiveOps.enableService(MainActivity.this,false);
                Toast.makeText(MainActivity.this,"Push service disabled", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 3. CALL .resume api : mandatory
        IgawLiveOps.resume(MainActivity.this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /* 딥링크 타입에 따라 아래를 참고하여 구현합니다. */

        /* 1. 앱 스키마 타입(myApp://deepLinkAction) 딥링크 구현
         *
         * 딥링크로 전달된 앱스키마 액션을 실행합니다.
         * IgawLiveOps.onNewIntent(MainActivity.this, intent);
         */

        /* 2. Json 문자열 타입({“url”:”deepLinkAction”}) 딥링크 구현
         *
         * 딥링크로 전달된 Json 문자열을 추출하고 Json 오브젝트로 변환합니다.
         * String jsonStr = intent.getStringExtra("com.igaworks.liveops.deepLink");
         * JSONObject jsonObj;
         * try {
         *     jsonObj = new JSONObject(jsonStr);
         *     //Json 오브젝트를 파싱하여 액션을 실행하도록 구현합니다.
         * } catch (Exception e) {
         *     // TODO: handle exception
         * }
         */
    }
}
