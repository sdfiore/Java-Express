/*
  IMPORTANT: Make sure you are using the correct package name.
  This example uses the package name:
  package com.example.javaexpress
  If you get an error when copying this code into Android studio, update it to match the package name found
  in the project's AndroidManifest.xml file.
 */
package com.example.javaexpress;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayTotal(String message) {
        TextView orderSummaryTextView = findViewById(R.id.total_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method increments the given quantity on the screen.
     */
    public void incrementOrder(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
        updateTotal(findViewById(R.id.total_text_view));
    }

    /**
     * This method decrements the given quantity on the screen.
     * The if statement prevents the quantity from becoming a negative value.
     */
    public void decrementOrder(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
        updateTotal(findViewById(R.id.total_text_view));
    }

    /**
     * This method updates the total based on the quantity, whippedCreamCheckbox, and chocolateCheckBox.
     */
    public void updateTotal(View view) {
        int price = quantity * 3;
        // Determines if the user wants whipped cream topping.
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Determines if the user wants chocolate topping.
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Add $3 if user wants whipped cream and chocolate.
        if (hasWhippedCream && hasChocolate) {
            price += (quantity * 3);
            String s_price = "Total: $" + price;
            displayTotal(s_price);
        }
        // Add $1 if user wants whipped cream.
        else if (hasWhippedCream) {
            price += (quantity);
            String s_price = "Total: $" + price;
            displayTotal(s_price);
        }
        // Add $2 if user wants chocolate.
        else if (hasChocolate) {
            price += (quantity * 2);
            String s_price = "Total: $" + price;
            displayTotal(s_price);
        }
        // User does not want whipped cream or chocolate.
        else {
            String s_price = "Total: $" + price;
            displayTotal(s_price);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Gets name that the user input.
        EditText nameField = findViewById(R.id.name_edit_text);
        String name = nameField.getText().toString();

        // Determines if the user wants whipped cream topping.
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Determines if the user wants chocolate topping.
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculates the price.
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Stores order values.
        String orderMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        if (quantity >= 1) {
            // The below code is used to send the order summary to and email application.
            // Intent intent = new Intent(Intent.ACTION_SENDTO);
            // intent.setData(Uri.parse("mailto:")); // Only email apps should handle this
            // intent.putExtra(Intent.EXTRA_SUBJECT, "Java Express order for " + name);
            // intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
            // If no application can handle this code, intent is skipped
            // if (intent.resolveActivity(getPackageManager()) != null) {
            // startActivity(intent);
            // }
            displayTotal(createOrderSummary(name, price, hasWhippedCream, hasChocolate));
        } else {
            String priceMessage = "Total: $" + price;
            displayTotal(priceMessage);
        }
    }

    /**
     * Calculates the price of the order.
     * @param addWhippedCream determines if whipped cream should be added to the coffee
     * @param addChocolate determines if chocolate should be added to the coffee
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Add $3 if user wants whipped cream and chocolate.
        if (addWhippedCream && addChocolate) {
            return (quantity * 3) + (quantity * 3);
        }
        // Add $1 if user wants whipped cream.
        else if (addWhippedCream) {
            return (quantity * 3) + (quantity);
        }
        // Add $2 if user wants chocolate.
        else if (addChocolate) {
            return (quantity * 3) + (quantity * 2);
        }
        // User does not want whipped cream or chocolate.
        else {
            return quantity * 3;
        }
    }

    /**
     * Creates a summary of the order.
     * @param name of the customer
     * @param price of the order
     * @param addWhippedCream determines if whipped cream should be added to the coffee
     * @param addChocolate determines if chocolate should be added to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nWhipped cream: " + addWhippedCream;
        priceMessage += "\nChocolate: " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }
}
