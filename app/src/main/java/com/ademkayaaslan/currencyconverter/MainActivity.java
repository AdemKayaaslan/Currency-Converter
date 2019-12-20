package com.ademkayaaslan.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.clockTextView) TextView clockTextView;
    @BindView(R.id.tryTextView) TextView tryTextView;
    @BindView(R.id.usdTextView) TextView usdTextView;
    @BindView(R.id.jpyTextView) TextView jpyTextView;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        jsonPlaceHolderApi = ApiClient.getClient().create(JsonPlaceHolderApi.class);

        database = this.openOrCreateDatabase("currencyDatabase", MODE_PRIVATE, null);

        timer();
    }

    public void setPosts() {
        Call<Post> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    clockTextView.setText("code:" + response.code());
                }

                Post post = response.body();
                long timeStamp = post.getTimestamp();
                String date = getDate(timeStamp);

                double TRY = post.getRates().getTRY();
                String lira = "TRY:"+TRY;
                double USD = post.getRates().getUSD();
                String dolar = "USD:"+ USD;
                double JPY = post.getRates().getJPY();
                String yen = "JPY:"+JPY;

                database.execSQL("CREATE TABLE IF NOT EXISTS currencydatabase(lira VARCHAR, dolar VARCHAR, yen VARCHAR)");
                database.execSQL("INSERT INTO currencydatabase (lira, dolar, yen) VALUES (?,?,?)", new Object[] {lira, dolar, yen});

                tryTextView.setText(lira);
                usdTextView.setText(dolar);
                jpyTextView.setText(yen);
                clockTextView.setText(date);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Bağlantı hatası!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void timer() {
        new CountDownTimer(10000,2000) {
            @Override
            public void onTick(long l) {
                setPosts();
            }
            @Override
            public void onFinish() {
                timer();
            }
        }.start();
    }


    public  String getDate (long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getTimeZone("+3");
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date date = (Date) calendar.getTime();
            return sdf.format(date);
        }catch (Exception e) {
        }
        return "";
    }
}
