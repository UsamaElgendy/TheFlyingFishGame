package com.example.theflyingfishgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

// this class allow the fish to fly in the air
public class FlyingFishView extends View {


    private static final String TAG = "";


    private Bitmap fish[] = new Bitmap[2];
    private int fishx = 10;
    private int fishY;
    private int fishSpeed;
    private boolean touch = false;
    private int canvasWidth, canvasHight;
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];


    // TODO : STEP 3
    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yelloPaint = new Paint();
    private int score;


    // TODO STEP 4 --> MAKE A GREEN BALL
    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();


    // TODO : STEP 5 --> MAKE A RED BALL
    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();

    // TODO : STEP 6
    private int lifeCounterOfFish;

    public FlyingFishView(Context context) {
        super(context);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        // TODO : STEP 1
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
        fishY = 550;


        // TODO : STEP 3
        yelloPaint.setColor(Color.YELLOW);
        yelloPaint.setAntiAlias(false);

        // make first hitBallChecker()
        score = 0;

        // TODO : STEP 4
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        // TODO : STEP 5
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        // TODO : STEP 6
        lifeCounterOfFish = 3;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO : STEP 1
        canvasWidth = canvas.getWidth();
        canvasHight = canvas.getHeight();
        canvas.drawBitmap(backgroundImage, 0, 0, null);
        // TODO : STEP 6 WE WILL USE THIS CODE IN THE FOR LOOP TO COUNT THE LIFE OF THE FISH
//        canvas.drawBitmap(life[0], 580, 10, null);
//        canvas.drawBitmap(life[0], 680, 10, null);
//        canvas.drawBitmap(life[0], 780, 10, null);


        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHight - fish[0].getHeight() * 2;

        fishY = fishY + fishSpeed;

        if (fishY < minFishY) {
            fishY = minFishY;
        }
        if (fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed += 2;
        if (touch) {
            canvas.drawBitmap(fish[1], fishx, fishY, null);
            touch = false;
        } else {
            canvas.drawBitmap(fish[0], fishx, fishY, null);
        }


        // TODO : AFTER  STEP 3 PART 2 YOU CAN PUT SCORE AFTER :
        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        // TODO : STEP 3 AFTER PART 1
        yellowX -= yellowSpeed;

        // TODO : STEP 3 PART 2
        if (hitBallChecker(yellowX, yellowY)) {
            score += 10;
            yellowY = -100;
        }

        // TODO : STEP 3 PART 1
        if (yellowX < 0) {
            yellowX = canvasWidth + 20;
            yellowY = (int) Math.floor(Math.random() * (maxFishY) - minFishY) + minFishY;
            Log.d(TAG, "Yellow = " + yellowY);
            if (yellowY < 200) {
                yellowY = 200;
            }
        }
        // here we create a ball in the sea
        canvas.drawCircle(yellowX, yellowY, 25, yelloPaint);


        // TODO : STEP 4
        if (greenX < 0) {
            greenX = canvasWidth + 20;
            greenY = (int) Math.floor(Math.random() * (maxFishY) - minFishY) + minFishY;
            Log.d(TAG, "greenY = " + greenY);
            if (greenY < 200) {
                greenY = 200;
            }
        }
        greenX -= greenSpeed;

        if (hitBallChecker(greenX, greenY)) {
            score += 20;
            greenY = -100;
        }
        canvas.drawText("Score : " + score, 20, 60, scorePaint);
        canvas.drawCircle(greenX, greenY, 25, greenPaint);


        // TODO : STEP 5
        if (redX < 0) {
            redX = canvasWidth + 20;
            redY = (int) Math.floor(Math.random() * (maxFishY) - minFishY) + maxFishY;
            Log.d(TAG, "RedX = " + redX);
            if (redX < 200) {
                redX = 210;
            }
        }
        redX -= redSpeed;
        if (hitBallChecker(redX, redY)) {
            redX = -100;
            // TODO : STEP 6 AFTER UPPER
            lifeCounterOfFish--;
            if (lifeCounterOfFish == 0) {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), GameOverActivity.class);
                intent.putExtra("TotalScore",score);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(intent);
            }

        }
        canvas.drawCircle(redX, redY, 30, redPaint);

        // TODO : STEP 6 part 2
        for (int i = 0; i < 3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;
            if (i < lifeCounterOfFish) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

        // TODO : STEP 7 GO TO XML GAME OVER
    }


    // TODO : STEP 3
    public boolean hitBallChecker(int x, int y) {
        if (fishx < x && x < (fishx + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;

            // if you want to speed the fish --> you can from here
            fishSpeed = -22;
        }
        return true;
    }
}
