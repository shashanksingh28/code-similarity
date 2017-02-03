// This example is from _Java Examples in a Nutshell_. (http://www.oreilly.com)
// Copyright (c) 1997 by David Flanagan
// This example is provided WITHOUT ANY WARRANTY either expressed or implied.
// You may study, use, modify, and distribute it for non-commercial purposes.
// For any commercial use, see http://www.davidflanagan.com/javaexamples

import java.text.*;
import java.util.Date;

/**
 * A partial implementation of a hypothetical stock portfolio class.
 * We use it only to demonstrate number and date internationalization.
 */
public class Portfolio {
  EquityPosition[] positions;
  Date lastQuoteTime = new Date();

  public void print() {
    // Obtain NumberFormat and DateFormat objects to format our data.
    NumberFormat number = NumberFormat.getInstance();
    NumberFormat price = NumberFormat.getCurrencyInstance();
    NumberFormat percent = NumberFormat.getPercentInstance();
    DateFormat shortdate = DateFormat.getDateInstance(DateFormat.SHORT);
    DateFormat fulldate = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                                         DateFormat.LONG);

    // Print some introductory data.
    System.out.println("Portfolio value at " +
                       fulldate.format(lastQuoteTime) + ":");
    System.out.println("Symbol\tShares\tBought On\tAt\t" +
                       "Quote\tChange");

    // Then display the table using the format() methods of the Format objects.
    for(int i = 0; i < positions.length; i++) {
      System.out.print(positions[i].name + "\t");
      System.out.print(number.format(positions[i].shares) + "\t");
      System.out.print(shortdate.format(positions[i].purchased) + "\t");
      System.out.print(price.format(positions[i].bought) + "\t");
      System.out.print(price.format(positions[i].current) + "\t");
      double change =
        (positions[i].current - positions[i].bought)/positions[i].bought;
      System.out.println(percent.format(change));
    }
  }

  static class EquityPosition {
    String name;             // Name of the stock.
    int shares;              // Number of shares held.
    Date purchased;          // When purchased.
    double bought, current;  // Purchase price and current price (per share).
    EquityPosition(String n, int s, Date when, double then, double now) {
      name = n; shares = s; purchased = when; bought = then; current = now;
    }
  }
}
