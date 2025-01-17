package com.affliatelinkgenerator.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText urlInput;
    private Button generateButton;
    private EditText resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI components
        urlInput = findViewById(R.id.urlInput);
        generateButton = findViewById(R.id.generateButton);
        resultText = findViewById(R.id.resultText);

        String affiliateTag = "giftcorner01-21";

        // Allow network operations on main thread (for simplicity)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Button click listener
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUrl = urlInput.getText().toString().trim();

                if (!inputUrl.isEmpty()) {
                    try {
                        String expandedUrl = expandShortUrl(inputUrl);
                        String affiliateLink = createAffiliateLink(expandedUrl, affiliateTag);
                        resultText.setText(affiliateLink);
                    } catch (Exception e) {
                        resultText.setText("Error: " + e.getMessage());
                    }
                } else {
                    resultText.setText("Please enter a valid Amazon URL.");
                }
            }
        });
    }

    // Method to create an affiliate link
    private String createAffiliateLink(String longUrl, String affiliateTag) {
        // Regular expression to extract ASIN
        String asinRegex = "/([A-Z0-9]{10})(?:[/?]|$)";
        Pattern pattern = Pattern.compile(asinRegex);
        Matcher matcher = pattern.matcher(longUrl);

        if (matcher.find()) {
            // Extract ASIN
            String asin = matcher.group(1);
            // Create affiliate link
            return "https://www.amazon.in/dp/" + asin + "/?tag=" + affiliateTag;
        } else {
            // Return error if ASIN not found
            return "Invalid Amazon URL. ASIN not found.";
        }
    }

    // Method to expand short Amazon URL
    private String expandShortUrl(String shortUrl) throws IOException {
        // Check if the URL is short
        if (shortUrl.contains("amzn.in")) {
            URL url = new URL(shortUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false); // To handle redirects manually
            String expandedUrl = connection.getHeaderField("Location");
            connection.disconnect();

            Log.i("tag","amzonUrl: "+expandedUrl);

            return expandedUrl != null ? expandedUrl : shortUrl; // Return the expanded URL or original
        }
        return shortUrl; // If not short, return as is
    }
}
