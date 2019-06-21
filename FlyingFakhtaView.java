package //your-package

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

public class FlyingFakhtaView extends View
{
    private Bitmap fakhta[] = new Bitmap[2];
    private int fakhtaX = 10;
    private int fakhtaY;
    private int fakhtaSpeed;


    private int canvasWidth;
    private int canvasHeight;


    //Blue Ball
    private int blueX;
    private int blueY;
    private int blueSpeed = 15;
    private Paint bluePaint = new Paint();

    //Green Ball
    private int greenX;
    private int greenY;
    private int greenSpeed = 40;
    private Paint greenPaint = new Paint();

    //Black Ball
    private int blackX;
    private int blackY;
    private int blackSpeed = 20;
    private Paint blackPaint = new Paint();

    private Bitmap bgImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];
    private int lifeCount;

    private boolean touch_flg = false;

    private int score;


    public FlyingFakhtaView(Context context)
    {
        super(context);

        fakhta[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
        fakhta[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird2);


        bgImage = BitmapFactory.decodeResource(getResources(), R.drawable.bgi);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        bluePaint.setColor(Color.BLUE);
        bluePaint.setAntiAlias(false);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(false);


        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);

        fakhtaY = 550;
        score = 0;
        lifeCount = 3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        canvas.drawBitmap(bgImage, 0, 0, null);

        int minFakhtaY = fakhta[0].getHeight();
        int maxFakhtaY = canvasHeight - fakhta[0].getHeight() * 3;
        fakhtaY = fakhtaY + fakhtaSpeed;

        if (fakhtaY < minFakhtaY) {
            fakhtaY = minFakhtaY;
        }
        if (fakhtaY > maxFakhtaY){
            fakhtaY = maxFakhtaY;
        }

        fakhtaSpeed = fakhtaSpeed + 2;

        if (touch_flg){
            //Flag wings
            canvas.drawBitmap(fakhta[1],fakhtaX, fakhtaY, null);
            touch_flg = false;
        }
        else {
            canvas.drawBitmap(fakhta[0], fakhtaX, fakhtaY, null);
        }

        //Blue
        blueX -= blueSpeed;
        if (hitCheck(blueX,blueY)){
            score += 10;
            blueX = -100;
        }
        if (blueX < 0){
            blueX = canvasWidth + 20;
            blueY = (int) Math.floor(Math.random()*(maxFakhtaY - minFakhtaY)) + minFakhtaY;
        }
        canvas.drawCircle(blueX, blueY, 30, bluePaint );

        //Green
        greenX -= greenSpeed;
        if (hitCheck(greenX,greenY)){
            score += 50;
            greenX = -100;
        }
        if (greenX < 0){
            greenX = canvasWidth + 20;
            greenY = (int) Math.floor(Math.random()*(maxFakhtaY - minFakhtaY)) + minFakhtaY;
        }
        canvas.drawCircle(greenX, greenY, 15, greenPaint );

        //Black
        blackX = blackX - blackSpeed;
        if (hitCheck(blackX,blackY)){
            blackX =- 100;
            lifeCount--;
            if (lifeCount ==0){
                //Game Over
                Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_SHORT).show();

                Intent endGameIntent = new Intent(getContext(), EndGameActivity.class);
                endGameIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                endGameIntent.putExtra("score", score);
                getContext().startActivity(endGameIntent);
            }


        }
        if (blackX < 0){
            blackX = canvasWidth + 200;
            blackY = (int) Math.floor(Math.random()*(maxFakhtaY - minFakhtaY)) + minFakhtaY;
        }
        canvas.drawCircle(blackX,blackY, 50,blackPaint);




        canvas.drawText("Score: " + score, 20, 60, scorePaint);

        //Life
        for (int i=0; i<3; i++){
            int x = (int) (560 + life[0].getWidth()*1.5*i);
            int y = 30;

            if (i < lifeCount){
                canvas.drawBitmap(life[0],x,y, null);
            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }


//        canvas.drawBitmap(life[0], 580, 10, null );
//        canvas.drawBitmap(life[0], 680, 10, null );
//        canvas.drawBitmap(life[0], 780, 10, null );
    }

    public boolean hitCheck(int x, int y){
        if (fakhtaX < x && x< (fakhtaX + fakhta[0].getWidth()) && fakhtaY < y && y < (fakhtaY + fakhta[0].getHeight()) ){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            touch_flg = true;
            fakhtaSpeed = -30;
        }
        return true;
    }
}
