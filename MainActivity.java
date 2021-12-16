package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private ImageView virus, virus2, hap, vitamin, agiz;
    private Button basla;
    private LinearLayout yenioyun, tumekran;
    private FrameLayout oyunalani;

    private TextView tvPuan;

    private Timer zamanlayici;
    private Random rnd;

    private int puan=0, xdegeri, rtrn;
    private int virushiz, virus2hiz, haphiz, vitaminhiz;

    private int ekraneni,oyunalanieni,oyunalaniboyu;

    private Boolean yon=false;

    MediaPlayer ses1, ses2, ses3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Programda kullanılacak nesne isimlerini bağla
        virus = findViewById(R.id.virus);
        virus2 = findViewById(R.id.virus2);

        agiz = findViewById(R.id.agiz);
        hap = findViewById(R.id.hap);
        vitamin = findViewById(R.id.vitamin);
        tvPuan = findViewById(R.id.tvPuan);

        basla = findViewById(R.id.basla);

        yenioyun = findViewById(R.id.yenioyun);

        tumekran=findViewById(R.id.tumekran);
        oyunalani = findViewById(R.id.oyunalani);

        //Başla butonuna tıklanma olayı ekle
        basla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baslat();
            }
        });

    }

    public void viruskonumla() {
        runOnUiThread(new Runnable() {  //Kullanıcı tanımlı metotlardan layout üzerindeki nesnelere ulaşmak ve onların özelliklerini değiştirmek runOnUiThread yordamı ile yapılır
            @Override
            public void run() {
                ViewGroup.LayoutParams virPar=virus.getLayoutParams();//Bu satır Layout parametrelerini alır virPar değişkenine yazar
                virPar.width=ekraneni/100*10;  //Belirleyeceğimiz genişliği okunan parametre değerinin üzerine yazar
                virus.setLayoutParams(virPar);  //Belirlediğimiz parametre değerlerini nesnenin üzerine atar
            }
        });

        virus.setY(-100);
        rnd = new Random();
        xdegeri = rnd.nextInt(oyunalanieni-virus.getWidth());
        virus.setX(xdegeri);

        virushiz=rnd.nextInt(6)+3;
    }

    public void virus2konumla() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams virPar=virus2.getLayoutParams();
                virPar.width=ekraneni/100*10;
                virus2.setLayoutParams(virPar);
            }
        });

        virus2.setY(-100);
        rnd = new Random();
        xdegeri = rnd.nextInt(oyunalanieni-virus2.getWidth());
        virus2.setX(xdegeri);

        virus2hiz=rnd.nextInt(6)+3;
    }

    public void hapkonumla() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams hapPar=hap.getLayoutParams();
                hapPar.width=ekraneni/100*10;
                hap.setLayoutParams(hapPar);
            }
        });

        hap.setY(-100);
        rnd = new Random();
        xdegeri = rnd.nextInt(oyunalanieni-hap.getWidth());
        hap.setX(xdegeri);

        haphiz=rnd.nextInt(6)+3;
    }

    public void vitaminkonumla() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams vitPar=vitamin.getLayoutParams();
                vitPar.width=ekraneni/100*10;
                vitamin.setLayoutParams(vitPar);
            }
        });

        vitamin.setY(-100);
        rnd = new Random();
        xdegeri = rnd.nextInt(oyunalanieni-vitamin.getWidth());
        vitamin.setX(xdegeri);

        vitaminhiz=rnd.nextInt(6)+3;
    }

    public void baslat() {
        ekraneni=tumekran.getMeasuredWidth();  //oyun başlarken ekran genişliğini alır

        puan=0;
        tvPuan.setText("0");  //Her yeni oyunda puanı sıfırlar

        ViewGroup.LayoutParams parametreler=oyunalani.getLayoutParams();  // oyunalanı layout u için kullanılan cihazın ekran genişliğinin %80 i belirlenier
        parametreler.width=Math.round(ekraneni/100*80);  //%80 değeri yuvarlanarak tamsayıya çevirilir.
        oyunalani.setLayoutParams(parametreler);

        oyunalanieni=oyunalani.getWidth();  //oyunalanı layout unun genişlikleri ve yüksekliği değişkenlere alınır.
        oyunalaniboyu=oyunalani.getHeight();

        viruskonumla();
        virus2konumla();
        hapkonumla();
        vitaminkonumla();

        agiz.setY(oyunalaniboyu-agiz.getHeight()/2);


        yenioyun.setVisibility(View.INVISIBLE);

        zamanlayici = new Timer();
        zamanlayici.schedule(new TimerTask() {
            @Override
            public void run() {
                adimla();
            }
        }, 0, 20);

    }

    public void adimla() {
        virus.setY(virus.getY() + virushiz);
        hap.setY(hap.getY() + haphiz);
        vitamin.setY(vitamin.getY() + vitaminhiz);

        if (puan>10){
            virus2.setY(virus2.getY() + virus2hiz);
        }

        if (yon==true) {
            if (agiz.getX()<oyunalanieni) {
                agiz.setX(agiz.getX() +10);
            }
        }else if(yon==false){
            if(agiz.getX()>9) {
                agiz.setX(agiz.getX() -10);
            }
        }

        rtrn=cakismavarmi();

        if (rtrn == 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    zamanlayici.cancel();
                    zamanlayici = null;
                    yenioyun.setVisibility(View.VISIBLE);
                    ses1=MediaPlayer.create(MainActivity.this, R.raw.virus);
                    ses1.start();
                }
            });
        }

        if ((rtrn == 2) || (rtrn==3)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvPuan.setText(Integer.toString(puan));

                }
            });
        }

        if(virus.getY()>oyunalaniboyu+50){
            viruskonumla();
        }

        if(virus2.getY()>oyunalaniboyu+50){
            virus2konumla();
        }

        if(vitamin.getY()>oyunalaniboyu+50){
            vitaminkonumla();
        }
        if(hap.getY()>oyunalaniboyu+50){
            hapkonumla();
        }

    }

    public boolean onTouchEvent(MotionEvent olay){
        if(olay.getAction()==MotionEvent.ACTION_DOWN){
            yon=true;
        }
        else if (olay.getAction()==MotionEvent.ACTION_UP){
            yon=false;
        }
        return true;
    }

    public int cakismavarmi() {
        if ((virus.getY() + virus.getHeight() >= agiz.getY()) &&
                ( (virus.getX() >= agiz.getX()-virus.getWidth()) && (virus.getX() <= agiz.getX()+agiz.getWidth()) )){

                return 1;

        }

        if ((virus2.getY() + virus2.getHeight() >= agiz.getY()) &&
                ( (virus2.getX() >= agiz.getX()-virus2.getWidth()) && (virus2.getX() <= agiz.getX()+agiz.getWidth()) )){

            return 1;

        }

        if ( (hap.getY() + hap.getHeight() >= agiz.getY()) &&
                ( (hap.getX() >= agiz.getX()-hap.getWidth()) && (hap.getX() <= agiz.getX()+agiz.getWidth()) )){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        puan++;
                        ses2=MediaPlayer.create(MainActivity.this, R.raw.hap);
                        ses2.start();
                        hapkonumla();
                    }
                });

            return 2;
        }

        if ((vitamin.getY() + vitamin.getHeight() >= agiz.getY()) &&
                ( (vitamin.getX() >= agiz.getX()-vitamin.getWidth()) && (vitamin.getX() <= agiz.getX()+agiz.getWidth()) )){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        puan = puan + 2;
                        ses3=MediaPlayer.create(MainActivity.this, R.raw.vitamin);
                        ses3.start();
                        vitaminkonumla();
                    }
                });
            return 3;
        }
        return 0;
    }
}
