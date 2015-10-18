package com.example.risto.mull;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.Rect;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.os.CountDownTimer;
        import android.os.Handler;
        import android.os.Vibrator;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.view.animation.TranslateAnimation;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import java.util.Random;
        import java.util.Vector;


public class GameActivity extends Activity {

    private RelativeLayout gameBoard;
    private TextView score;
    private TextView time;
    private TextView scoreFlash;
    private int gameScore;
    private int width;
    private int height;
    private Thread t, t2, t3;
    private boolean color = true, pressed = false;
    private static int SLEEP_TIME = 1200;
    private int lastScore = 0, scoreMulti;
    private MediaPlayer mp;
    private final String endString= "Game over";
    private Animation mover;
    private Vibrator vb;
    private View f, c, setpet;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameBoard = new RelativeLayout(this);
        vb = (Vibrator)   getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(100);
        gameBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vb.vibrate(80);
                gameScore -=1;
                lastScore = -1;
                score.setText("Score: " + gameScore);
                scoreFlasher();

            }
        });

        time = new TextView(this);
        time.setId(View.generateViewId());

        score = new TextView(this);
        score.setTextSize(20);
        score.setTextColor(Color.WHITE);
        score.setBackgroundColor(Color.RED);
        score.setId(View.generateViewId());
        score.setText("Score: 0");

        scoreFlash = new TextView(this);
        scoreFlash.setText("");
        scoreFlash.setTextSize(40);

        mp = MediaPlayer.create(this, R.raw.boing);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.CENTER_IN_PARENT);

        score.setLayoutParams(lp2);
        time.setLayoutParams(lp);
        scoreFlash.setLayoutParams(lp3);

        gameBoard.addView(score);
        gameBoard.addView(time);
        gameBoard.addView(scoreFlash);

        SLEEP_TIME = 1200;
        startGame();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(gameBoard);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        width = gameBoard.getWidth();
        height = gameBoard.getHeight();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            t.interrupt();
            t2.interrupt();
            t3.interrupt();
            submitMessage();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void changeColor() {
        double choose = randomInt(1);
        if (choose > 0.5) {
            score.setBackgroundColor(Color.RED);
            score.invalidate();
            color = true;
        } else {
            score.setBackgroundColor(Color.BLUE);
            score.invalidate();
            color = false;
        }
    }

    private View addButton() {
        double choose = randomInt(1);

        final SquareButton button;
        final CircleButton buttonc;

        if (choose > 0.50) {
            button = new SquareButton(this, null);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameBoard.removeView(v);
                    vb.vibrate(80);
                    if (!color) {
                        mp.start();
                        gameScore +=3*scoreMulti;
                        lastScore = 3*scoreMulti;
                    } else {
                        gameScore -=2;
                        lastScore = -2;
                    }
                    f = addButton();
                    addView(f);
                    scoreFlasher();
                    score.setText("Score: " + gameScore);
                }
            });
        View button2 = setPosition(button);
            System.out.println("" + button2.getLeft());


        return button2;

        } else {
            buttonc = new CircleButton(this,null);

            buttonc.setOnClickListener(new View.OnClickListener() {

                @Override
                    public void onClick(View v) {

                    gameBoard.removeView(v);
                    vb.vibrate(80);

                    if (color) {
                        mp.start();
                        gameScore +=3*scoreMulti;
                        lastScore = 3*scoreMulti;
                    } else {
                        gameScore -=2;
                        lastScore = -2;
                    }
                    f = addButton();
                    addView(f);
                    scoreFlasher();
                    score.setText("Score: " + gameScore);

                }
            });
            View button2 = setPosition(buttonc);
            System.out.println("" + button2.getLeft());
            return button2;
        }
    }

    private int randomInt(int max) {
        return new Random().nextInt(max + 1);
    }

    private void addView(final View f){
        gameBoard.addView(f);
        scoreMulti = 3;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scoreMulti = 2;
                setpet = setPosition(f);
                f.setLeft(setpet.getLeft());
                f.setRight(setpet.getRight());

            }
        }, SLEEP_TIME);

        final Handler handler2 = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scoreMulti = 1;
                setpet = setPosition(f);
                f.setLeft(setpet.getLeft());
                f.setRight(setpet.getRight());
            }
        }, SLEEP_TIME*2);

        final Handler handler3 = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewRemove(f);

                gameScore -=1;
                score.setText("Score: " + gameScore);
                lastScore = -1;
                scoreFlasher();
            }
        }, SLEEP_TIME*3);


    }
    private void viewRemove(View f) {
        gameBoard.removeView(f);
    }

    private View setPosition(View v) {
        boolean notOver;
        double choose = randomInt(1);
        SquareButton button;
        CircleButton buttonc;
        int x, y;
        if (v instanceof SquareButton) {
            System.out.println("seeeee on squaaare");
            SquareButton dummy = new SquareButton(this,null);
            button = (SquareButton) v;
            while(true) {

                notOver = true;
                x = randomInt(width - button.getSize());
                y = randomInt(height-20 - button.getSize())+20;
                dummy.setX(x);
                dummy.setY(y);
                Rect nextButtonBounds = dummy.getClipBounds();
                for(int i=0; i<gameBoard.getChildCount(); ++i) {
                    View nextChild = gameBoard.getChildAt(i);

                    if (nextChild.getClipBounds() == null) break;

                    Rect nextChildBounds = nextChild.getClipBounds();

                    if (nextButtonBounds.intersect(nextChildBounds)) {
                        notOver = false;
                    }
                }

                if (notOver) break;
            }
            button.setX(x);
            button.setY(y);
            return button;
        } else {
            buttonc = (CircleButton) v;
            System.out.println("seeeee on ciiircel");
            CircleButton dummy = new CircleButton(this, null);

            while (true) {

                notOver = true;
                x = randomInt(width - buttonc.getSize());
                y = randomInt(height - 20 - buttonc.getSize()) + 20;
                dummy.setX(x);
                dummy.setY(y);
                Rect nextButtonBounds = dummy.getClipBounds();
                for (int i = 0; i < gameBoard.getChildCount(); ++i) {
                    View nextChild = gameBoard.getChildAt(i);

                    if (nextChild.getClipBounds() == null) break;

                    Rect nextChildBounds = nextChild.getClipBounds();

                    if (nextButtonBounds.intersect(nextChildBounds)) {
                        notOver = false;
                    }
                }

                if (notOver) break;
            }
            buttonc.setX(x);
            buttonc.setY(y);

            return buttonc;
        }
    }

    private void startGame() {

        t2 = new Thread() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addCountDown();
                        }
                    });

                    while(true) {
                        Thread.sleep(2500);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Thread.interrupted();
                                changeColor();

                            }
                        });
                    }

                } catch (Exception e) {}
            }
        };

        t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SLEEP_TIME*2);
                    while (true) {

                        Thread.sleep(SLEEP_TIME*3);

                        Thread.interrupted();



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (gameBoard.getChildCount() < 6) {
                                    f = addButton();
                                    addView(f);
                                }


                                System.out.println("Sssssssssiiiiiiinnnn paneb pildile");

                                if (SLEEP_TIME > 600){
                                    SLEEP_TIME -=15;
                                }
                            }

                        });

                    }
                } catch (InterruptedException e) {}
            }
        };
        t3 = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {

                        Thread.sleep(SLEEP_TIME*3);

                        Thread.interrupted();



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (gameBoard.getChildCount() < 6) {
                                    f = addButton();
                                    addView(f);
                                }



                                System.out.println("Sssssssssiiiiiiinnnn paneb pildile");


                            }

                        });
                        try {
                            Thread.sleep(SLEEP_TIME/3);
                        } catch (InterruptedException e) {}

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (gameBoard.getChildCount() < 6) {
                                    c = addButton();
                                    addView(c);
                                }



                                System.out.println("Sssssssssiiiiiiinnnn paneb pildile");


                            }

                        });

                    }
                } catch (InterruptedException e) {}
            }
        };

        t2.start();
        t.start();
        t3.start();

    }
    private void addCountDown() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                t.interrupt();
                t2.interrupt();
                t3.interrupt();
                scoreFlash.setText(endString);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scoreFlash.setText("");
                        System.out.println("Deleteeeeeeeeeeeed endtext");
                    }
                }, 1000);
                submitMessage();


                time.setText("done!");

            }
        }.start();
    }

    public void submitMessage()
    {
        // get the game score
        String message = String.valueOf(gameScore);
        Intent intentMessage=new Intent();

        // put the message to return as result in Intent
        intentMessage.putExtra("MESSAGE",message);
        // Set The Result in Intent
        setResult(1,intentMessage);
        // finish The activity
        mp.release();
        finish();

    }

    public void scoreFlasher() {
        scoreFlash.setText("" + lastScore);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scoreFlash.setText("");
                System.out.println("Deleteeeeeeeeeeeed lastscore");
            }
        }, 200);
    }



}