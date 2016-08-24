package com.example.administrator.voicerecognition;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView txtOutput;
    private Button start_listening;
    private SpeechRecognizer sr;
    private final static String TAG = "VoiceRecognition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtOutput = (TextView) findViewById(R.id.textView);
        start_listening = (Button) findViewById(R.id.start_listening);
        start_listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sr != null)
                {
                    sr.destroy();

                }
                sr = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
                sr.setRecognitionListener(new listener());
                startSpeechToText();
            }
        });


    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            txtOutput.setText("error " + error);
        }
        public void onResults(Bundle results)
        {
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            txtOutput.setText(data.get(0));
            sr.destroy();
            sr = SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
            sr.setRecognitionListener(new listener());
            startSpeechToText();
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }

    public void startSpeechToText()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "...");
        sr.startListening(intent);

    }


}
