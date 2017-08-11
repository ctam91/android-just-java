package com.example.tammy.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolateCheckBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String userName = nameField.getText().toString();

        int newPrice = calculatePrice(hasWhippedCream, hasChocolate);
        String emailMessage = createOrderSummary(newPrice, hasWhippedCream, hasChocolate, userName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:tammyscoffeeshop@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee Order");
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;

        if(hasWhippedCream) {
            basePrice += 1;
        }
        if(hasChocolate){
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    /**
     * Creates order summary
     * @return message with price
     */
    private String createOrderSummary(int newPrice, boolean hasWhippedCream, boolean hasChocolate, String userName){
        String priceMessage = getString(R.string.name) + ": "+ getString(R.string.order_summary_name, userName);
        priceMessage += "\n" + getString(R.string.addwhippedcream) + hasWhippedCream;
        priceMessage += "\n" + getString(R.string.addchocolate) + hasChocolate;
        priceMessage += "\n" + getString(R.string.quantity) + quantity;
        priceMessage += "\n" + getString(R.string.total) + newPrice;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity < 100) {
            quantity += 1;
            displayQuantity(quantity);
        }else {
            Toast.makeText(this, "You cannot order more than 100 coffees at a time", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity > 1) {
            quantity -= 1;
            displayQuantity(quantity);
        } else{
            Toast.makeText(this, "Quantity cannot be negative", Toast.LENGTH_SHORT).show();
        }
    }

}