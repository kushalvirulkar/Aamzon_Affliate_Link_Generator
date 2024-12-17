package com.affliatelinkgenerator.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AffiliateLinkGenerator extends AppCompatActivity {

    private EditText inputUrl;
    private TextView generatedLink;
    private Button generateButton;

    // Replace "your-affiliate-tag" with your actual Amazon affiliate tag
    private static final String AFFILIATE_TAG = "giftcorner01-22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiliate_link_generator);

        inputUrl = findViewById(R.id.inputUrl);
        generatedLink = findViewById(R.id.generatedLink);
        generateButton = findViewById(R.id.generateButton);

        generateButton.setOnClickListener(v -> {
            String originalUrl = inputUrl.getText().toString().trim();
            if (!originalUrl.isEmpty()) {
                String affiliateLink = generateAffiliateLink(originalUrl);
                generatedLink.setText(affiliateLink);
            } else {
                generatedLink.setText("Please enter a valid Amazon URL.");
            }
        });
    }

    private String generateAffiliateLink(String originalUrl) {
        try {
            // Check if the URL already has a query string
            if (originalUrl.contains("?")) {
                // Append affiliate tag to existing query string
                return originalUrl + "&tag=" + AFFILIATE_TAG;
            } else {
                // Add affiliate tag as the first query parameter
                return originalUrl + "?tag=" + AFFILIATE_TAG;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating affiliate link.";
        }
    }
}
