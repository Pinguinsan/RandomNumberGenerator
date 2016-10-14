package com.pinguinsan.randomnumbergenerator;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int _numberToDisplayInt;
    private long _numberToDisplayLong;
    private String _numberToDisplayString;
    private final int RANDOM_NUMBER_REQUEST_CODE = 1;
    private int _screenWidth;
    private int _screenHeight;

    private EditText lowerLimitEntryBox;
    private EditText upperLimitEntryBox;
    private TextView randomNumber;

    private Button generateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        _screenHeight = size.y;
        _screenWidth = size.x;
        System.out.println("Screen dimensions: " + _screenHeight + "x" + _screenWidth);

        lowerLimitEntryBox = (EditText) findViewById(R.id.lowerLimitEntryBox);
        upperLimitEntryBox = (EditText) findViewById(R.id.upperLimitEntryBox);


        TextView randomNumber = (TextView) findViewById(R.id.randomNumber);

        Button generateButton = (Button) findViewById(R.id.generateButton);

        assert (generateButton != null);
        assert (randomNumber != null);
        generateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String lowerLimitString = lowerLimitEntryBox.getText().toString();
                String upperLimitString = upperLimitEntryBox.getText().toString();

                System.out.println("Text extracted from lowerLimitEntryBox: \"" + lowerLimitString +"\"");
                System.out.println("Text extracted from upperLimitEntryBox: \"" + upperLimitString + "\"");
                if (lowerLimitString.isEmpty() || upperLimitString.isEmpty()) {
                    System.out.println("Error, one of these is empty");
                    return;
                }
                long firstNumber = (long)Integer.parseInt(lowerLimitString);
                long secondNumber = (long)Integer.parseInt(upperLimitString);
                System.out.println("Integer parsed from lowerLimitEntryBox: \"" + firstNumber +"\"");
                System.out.println("Integer parsed from upperLimitEntryBox: \"" + secondNumber + "\"");
                if ((firstNumber < (Integer.MAX_VALUE)) && (secondNumber < (Integer.MAX_VALUE))) {
                    int firstNumberInt = (int)firstNumber;
                    int secondNumberInt = (int)secondNumber;
                    new iRNGAsync().execute(firstNumberInt, secondNumberInt); //Generate random number by Async
                } else {
                    new lRNGAsync().execute(firstNumber, secondNumber);
                }

                //Intent randomNumberIntent = new Intent(MainActivity.this, RandomNumberGenerationProgress.class);
                //randomNumberIntent.putExtra("firstNumber", firstNumber);
                //randomNumberIntent.putExtra("secondNumber", secondNumber);

                //Maybe needed to bring new intent on top?
                //randomNumberIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //The second variable, an integer called "requestCode", is just silently
                //passed back to the "onActivityResult" below, to distinguish it from any
                //other result requests. The "Intent" is what gets physically passed to the
                //new activity, and contains packaged data that the new activity may unpack
                //try {
                //    startActivityForResult(randomNumberIntent, RANDOM_NUMBER_REQUEST_CODE);
                //} catch (ActivityNotFoundException e) {
                //    e.printStackTrace();
                //}
            }
        });
    }

    private void autoResizeDisplayText() {
        float textSize = randomNumber.getTextSize();
        String tempString =  _numberToDisplayString;
        float textSpacing = randomNumber.getLetterSpacing();
        System.out.println("The current text size is " + textSize);
        System.out.println("The current text spacing is " + textSpacing);
        System.out.println("The number to display has " + tempString.length() + " characters");
    }

    public class iRNGAsync extends AsyncTask<Integer, Integer, Integer> {

        //Begin data members
        private int _lowLimit;
        private int _highLimit;
        //End data members

        @Override
        protected void onPreExecute(){
            // Activity 1 GUI stuff
        }

        @Override
        protected Integer doInBackground(Integer... values) {
            if (values.length != 2) {
                throw new IllegalArgumentException("Incorrect amount of arguments (expected 2, received" + Integer.toString(values.length) + ")");
            }
            int firstNumber = values[0];
            int secondNumber = values[1];
            if (firstNumber > secondNumber) {
                _lowLimit = secondNumber;
                _highLimit = firstNumber;
            }
            else if (firstNumber == secondNumber) {
                return firstNumber;
            }
            else {
                _lowLimit = firstNumber;
                _highLimit = secondNumber;
            }
            return (RandomNumberGenerator.iRandomBetween(_lowLimit, _highLimit));

        }

        @Override
        protected void onPostExecute(Integer result) {
            System.out.println("Received number of " + result + " back in the MainActivityAsync method");
            _numberToDisplayInt = result;
            _numberToDisplayString = Integer.toString(_numberToDisplayInt);
            if (randomNumber == null) {
                randomNumber = (TextView) findViewById(R.id.randomNumber);
            }

            randomNumber.setText(_numberToDisplayString);

            autoResizeDisplayText();

        }
    }


    public class lRNGAsync extends AsyncTask<Long, Long, Long> {

        //Begin data members
        private long _lowLimit;
        private long _highLimit;
        //End data members

        @Override
        protected void onPreExecute(){
            // Activity 1 GUI stuff
        }

        @Override
        protected Long doInBackground(Long... values) {
            if (values.length != 2) {
                throw new IllegalArgumentException("Incorrect amount of arguments (expected 2, received" + Integer.toString(values.length) + ")");
            }
            long firstNumber = values[0];
            long secondNumber = values[1];
            if (firstNumber > secondNumber) {
                _lowLimit = secondNumber;
                _highLimit = firstNumber;
            }
            else if (firstNumber == secondNumber) {
                return firstNumber;
            }
            else {
                _lowLimit = firstNumber;
                _highLimit = secondNumber;
            }
            return (RandomNumberGenerator.lRandomBetween(_lowLimit, _highLimit));

        }

        @Override
        protected void onPostExecute(Long result) {
            System.out.println("Received number of " + result + " back in the MainActivityAsync method");
            _numberToDisplayLong = result;
            _numberToDisplayString = Long.toString(_numberToDisplayLong);
            if (randomNumber == null) {
                randomNumber = (TextView) findViewById(R.id.randomNumber);
            }
            randomNumber.setText(_numberToDisplayString);

        }
    }
}
