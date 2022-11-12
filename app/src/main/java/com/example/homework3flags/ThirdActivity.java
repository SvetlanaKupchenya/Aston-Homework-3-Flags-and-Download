package com.example.homework3flags;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ThirdActivity extends AppCompatActivity {

    private static final String KEY_IMAGE_THIRD = "IMAGE_THIRD";
    private ImageView mImageViewThird;
    private URL mUriThird;
    private EditText mEditTextWebsiteThird;
    private String mLinkThird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mEditTextWebsiteThird = findViewById(R.id.editTextWebsiteThird);
        mImageViewThird = findViewById(R.id.imageViewThird);

        mEditTextWebsiteThird.setOnKeyListener (new View.OnKeyListener() {
                                               @Override
                                               public boolean onKey(View v, int keyCode, KeyEvent event) {
                                                   if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                                           (keyCode == KeyEvent.KEYCODE_ENTER || (keyCode == KeyEvent.KEYCODE_SEARCH)))
                                                   {
                                                       mImageViewThird.setImageDrawable(null);
                                                       mLinkThird = mEditTextWebsiteThird.getText().toString();
                                                       if (mEditTextWebsiteThird.length() == 0) {mLinkThird = "https://i.picsum.photos";}
                                                       try {
                                                           mUriThird = new URL(mLinkThird);
                                                       } catch (MalformedURLException e) {
                                                           e.printStackTrace();
                                                       }
                                                       new DownloadImageTask(mImageViewThird).execute(String.valueOf(mUriThird));
                                                       return true;
                                                   }
                                                   return false;
                                               }
                                           }
        );


        if (savedInstanceState != null) {
            mLinkThird = savedInstanceState.getString(KEY_IMAGE_THIRD, "https://i.picsum.photos");
            //if (mEditTextWebsiteThird.length() == 0) {mLinkThird = "https://i.picsum.photos";}
            try {
                mUriThird = new URL(mLinkThird);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            new DownloadImageTask(mImageViewThird).execute(String.valueOf(mUriThird));
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Ошибка загрузки", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            if (result == null) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_2, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public void onFromThirdToSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IMAGE_THIRD, mLinkThird);
    }
}