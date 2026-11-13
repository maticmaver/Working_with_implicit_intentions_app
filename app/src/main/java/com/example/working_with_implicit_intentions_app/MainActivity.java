package com.example.working_with_implicit_intentions_app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText mWebsiteEditText;
    private EditText mLocationEditText;
    private EditText mShareTextEditText;
    private EditText mPhoneEditText;

    private Button mWebsiteButton;
    private Button mLocationButton;
    private Button mShareTextButton;
    private Button mPhoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Povezava gradnikov v metodi onCreate z uporabnim findViewById()
        mWebsiteEditText = findViewById(R.id.editTextText);
        mLocationEditText = findViewById(R.id.editTextText2);
        mShareTextEditText = findViewById(R.id.editTextText3);
        mPhoneEditText = findViewById(R.id.editTextText4);

        mWebsiteButton = findViewById(R.id.button);
        mLocationButton = findViewById(R.id.button2);
        mShareTextButton = findViewById(R.id.button3);
        mPhoneButton = findViewById(R.id.button4);

        if (mWebsiteButton != null) {
            mWebsiteButton.setOnClickListener(this::openWebsite);
        }
        if (mLocationButton != null) {
            mLocationButton.setOnClickListener(this::openLocation);
        }
        if (mShareTextButton != null) {
            mShareTextButton.setOnClickListener(this::shareText);
        }
        if (mPhoneButton != null) {
            mPhoneButton.setOnClickListener(this::callUs);
        }
    }

    public void openWebsite(View view) {
        String url = mWebsiteEditText.getText().toString().trim();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e("ImplicitIntents", "Cannot handle website intent", e);
            }
        }
    }

    public void openLocation(View view) {
        String loc = mLocationEditText.getText().toString().trim();
        Uri addressUri;
        if (loc.startsWith("http://") || loc.startsWith("https://") || loc.startsWith("geo:")) {
            addressUri = Uri.parse(loc);
        } else {
            addressUri = Uri.parse("geo:0,0?q=" + Uri.encode(loc));
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e("ImplicitIntents", "Cannot handle location intent", e);
            }
        }
    }

    public void shareText(View view) {
        String txt = mShareTextEditText.getText().toString();
        new ShareCompat.IntentBuilder(this)
                .setType("text/plain")
                .setChooserTitle("Share text via")
                .setText(txt)
                .startChooser();
    }

    public void callUs(View view) {
        String phone = mPhoneEditText.getText().toString().trim();
        if (phone.isEmpty()) {
            phone = "+386 5 62 05 700";
        }
        Uri phoneUri = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, phoneUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e("ImplicitIntents", "Cannot handle dial intent", e);
            }
        }
    }
}