package com.example.cafe.khuffee.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cafe.khuffee.R;
import com.example.cafe.khuffee.database.Dao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import classes.Menuitem;
import classes.User;


public class SplashActivity extends AppCompatActivity {
    private ImageView imgSplash;
    private TextView tvSplash;

    private boolean isDone;

    private Dao dao;
    private String[] log;
    private User user;
    private ArrayList<Menuitem> menuitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setLayout();

        imgSplash.setImageResource(R.drawable.khuffee_logo);       //로고 이미지 띄우기
        tvSplash.setText("loading log...");                         //현재 상태 메세지

        isDone = false;                                             //내부 db에서 로그를 조회 여부

        //메뉴 정보를 가져오기
        new LoadData().execute("https://menu-ckkkkk.c9users.io/menuitems.php");

        //내부 db에서 로그 조회하기
        dao = new Dao(SplashActivity.this);
        log = new String[2];
        log = dao.getLog();

        //로그가 있다면 사용자 정보를 가져오기
        //@@to-do 성찬이 서버가 완료되면 내부db에서 가져오는 것이 아니라 서버와 연결해서 가져오기
        if(log != null) {
            user = dao.getUser(log[0]);
            menuitems = dao.getMenuitem();
        }

        isDone = true;
    }

    private void setLayout() {
        imgSplash = (ImageView) findViewById(R.id.img_splash);
        tvSplash = (TextView) findViewById(R.id.tv_splash);
    }

    private class LoadData extends AsyncTask<String, Void, Void> {
        private String phpData;

        @Override
        protected void onPreExecute() {
            tvSplash.setText("conneting server...");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDefaultUseCaches(false);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                phpData = sb.toString();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(phpData);
                int php_db_version = jsonObject.getInt("version");
                int sql_db_version = dao.getVersion();

                if (log == null || php_db_version != sql_db_version) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSplash.setText("loading menu  data");
                        }
                    });
                    JSONArray jsonArray = jsonObject.getJSONArray("menuitems");
                    menuitems = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Menuitem menuitem = new Menuitem(object.getInt("type"), object.getString("name_kor"),
                                object.getString("name_eng"), object.getInt("price_hot"), object.getInt("price_cold"));
                        dao.insertMenuitem(menuitem);
                        menuitems.add(menuitem);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvSplash.setText("done !");
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dao.close();

            //SplashActivity의 동작이 끝날 때까지 무한루프
            while(!isDone) {}

            //MainActivity 실행
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("user", user);
            intent.putExtra("menuitems", menuitems);
            startActivity(intent);
            super.onPostExecute(aVoid);
        }
    }
}
