package com.example.risto.mull;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

public class MainActivity extends Activity {

    public RelativeLayout menuBoard;
    private TextView textViewMessage;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewMessage = (TextView) findViewById(R.id.textView2);
        textViewMessage.setText("");


        Button startGame = (Button) findViewById(R.id.gameStartButton);
        startGame.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                startActivityForResult(intent, 1);
            }

        });

        Button shareScore = (Button) findViewById(R.id.shareScore);
        shareScore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "My high score in Mull is "
                        + message);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }

        });
        menuBoard =  (RelativeLayout) findViewById(R.id.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 1
        if(requestCode==1) {
            // fetch the message String
            message=data.getStringExtra("MESSAGE");
            // Set the message string in textView

            textViewMessage.setText("Your score is: " + message);

        }

    }
}