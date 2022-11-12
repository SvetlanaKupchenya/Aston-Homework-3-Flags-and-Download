package com.example.homework3flags;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {

    private static final String KEY_IMAGE = "IMAGE";
    private ImageView mImageView;
    private Uri mUri;
    private EditText mEditTextWebsite;
    private String mLink;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mEditTextWebsite = findViewById(R.id.editTextWebsite);
        mImageView = findViewById(R.id.imageView);

        mEditTextWebsite.setOnKeyListener (new View.OnKeyListener() {
                                              @Override
                                              public boolean onKey(View v, int keyCode, KeyEvent event) {
                                                  if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                                          (keyCode == KeyEvent.KEYCODE_ENTER) || (keyCode == KeyEvent.KEYCODE_SEARCH))
                                                  {
                                                      mImageView.setImageDrawable(null);
                                                      mLink = mEditTextWebsite.getText().toString();
                                                      if (mEditTextWebsite.length() > 0) {
                                                          mUri = Uri.parse(mLink);

                                                          Picasso.with(getApplicationContext())
                                                                  .load(mUri)
                                                                  .into(mImageView);
                                                          //"android.graphics.drawable.BitmapDrawable@3173a47"

                                                          if (mImageView.getDrawable() == null) {
                                                              Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_1, Toast.LENGTH_LONG);
                                                              toast.show();
                                                          }

                                                      } else {
                                                          mImageView.setImageURI(null);
                                                          Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_message_2, Toast.LENGTH_LONG);
                                                          toast.show();
                                                      }
                                                      return true;
                                                  }
                                                  return false;
                                              }
                                          }
        );

        if (savedInstanceState != null) {
            mLink = savedInstanceState.getString(KEY_IMAGE, "");
            mUri = Uri.parse(mLink);
            Picasso.with(this)
                    .load(mUri)
                    .into(mImageView);
        }
    }

    public void onToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onToThirdActivity(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IMAGE, mLink);
    }
}