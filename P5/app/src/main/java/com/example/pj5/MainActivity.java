package com.example.pj5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements ScrollEnd
{

    public static int INIT_SCORE = 0;

    private FloatingActionButton addCredit;
    private ImageScrollView token1, token2, token3;
    public static TextView score_view;
    private static Button play;

    private TextView numJ, ganancia;

    int count_roll = 0;
    int contJ = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCredit = (FloatingActionButton) findViewById(R.id.addCredit);

        token1 = (ImageScrollView) findViewById(R.id.token);
        token2 = (ImageScrollView) findViewById(R.id.token2);
        token3 = (ImageScrollView) findViewById(R.id.token3);

        score_view = (TextView) findViewById(R.id.txt_score);
        score_view.setText(String.valueOf(INIT_SCORE));

        play = (Button) findViewById(R.id.jugar_id);


        //para registrar botones reset e historial

        /////


        numJ = (TextView) findViewById(R.id.numJ);
        ganancia = (TextView) findViewById(R.id.ganancia);


        token1.setScrollEnd(MainActivity.this);
        token2.setScrollEnd(MainActivity.this);
        token3.setScrollEnd(MainActivity.this);





        play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(INIT_SCORE >= 1)
                {
                    token1.setValueRandom(new Random().nextInt(6), new Random().nextInt((15-5)+1)+5);
                    token2.setValueRandom(new Random().nextInt(6), new Random().nextInt((15-5)+1)+5);
                    token3.setValueRandom(new Random().nextInt(6), new Random().nextInt((15-5)+1)+5);

                    INIT_SCORE -= 1;
                    score_view.setText(String.valueOf(INIT_SCORE));

                    contJ++;
                    numJ.setText(String.valueOf(contJ));
                }
                else
                {
                    play.setEnabled(false);
                    Snackbar snackbar = Snackbar.make(play, "Not enough money", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        addCredit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addCreditSB();
            }
        });
    }

    private void addCreditSB()
    {
        addCreditDialog addC = new addCreditDialog();
        addC.show(getSupportFragmentManager(), "Aniadir");
    }


    //cuando terminan de girar las ruletas
    @Override
    public void scrollEnd(int result, int count)
    {
        if(count_roll < 2) // si imgs aun siguen girando
            count_roll++;
        else
        {
            count_roll = 0;

            //calcular puntos/premios

            if(token1.getImgValue() == token2.getImgValue() && token2.getImgValue() == token3.getImgValue())
            {
                Snackbar snackbar = Snackbar.make(play, "Big Prize!", Snackbar.LENGTH_SHORT);
                snackbar.show();
                INIT_SCORE += 3;
                score_view.setText(String.valueOf(INIT_SCORE));
                ganancia.setText(String.valueOf(3));
            }

            else if(token1.getImgValue() == token2.getImgValue() || token2.getImgValue() == token3.getImgValue() || token1.getImgValue() == token3.getImgValue())
            {
                Snackbar snackbar = Snackbar.make(play, "Small Prize!", Snackbar.LENGTH_SHORT);
                snackbar.show();
                INIT_SCORE += 2;
                score_view.setText(String.valueOf(INIT_SCORE));
                ganancia.setText(String.valueOf(2));
            }
            else
            {
                Snackbar snackbar = Snackbar.make(play, "You Lose :(", Snackbar.LENGTH_SHORT);
                snackbar.show();
                ganancia.setText(String.valueOf(0));

            }
        }

    }

    public static TextView getScoreView()
    {
        return score_view;
    }

    public static Button getPlayButton()
    {
        return play;
    }
}
