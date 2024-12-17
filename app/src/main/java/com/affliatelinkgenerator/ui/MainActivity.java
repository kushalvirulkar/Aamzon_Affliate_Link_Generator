package com.affliatelinkgenerator.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText urlInput, affiliateTagInput;
    private Button generateButton;
    private EditText resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI components
        urlInput = findViewById(R.id.urlInput);
        affiliateTagInput = findViewById(R.id.affiliateTagInput);
        generateButton = findViewById(R.id.generateButton);
        resultText = findViewById(R.id.resultText);

        // Button click listener
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String longUrl = urlInput.getText().toString().trim();
                //String affiliateTag = affiliateTagInput.getText().toString().trim();


                if (!longUrl.isEmpty() && !affiliateTag.isEmpty()) {
                    String affiliateLink = createAffiliateLink(longUrl, affiliateTag);
                    resultText.setText(affiliateLink);
                } else {
                    resultText.setText("Please enter a valid Amazon URL and Affiliate Tag.");
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
            return "https://www.amazon.in/dp/" + asin + "/" + "?tag=" + affiliateTag ;
        } else {
            // Return error if ASIN not found
            return "Invalid Amazon URL. ASIN not found.";
        }
    }
}
