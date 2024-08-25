
// Marten Zaky 501224012
import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap {
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s) {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address) {
    String parts[] = new String[3];

    if (address == null || address.length() == 0) {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext()) {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length + 1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address) {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for
    // e.g. number of parts != 3
    if (getParts(address).length != 3) {
      return false;
    }
    if (!allDigits(getParts(address)[0]) || getParts(address)[0].length() != 2 ||
        getParts(address)[1].length() != 3) {
      return false;
    }
    if (!getParts(address)[2].equalsIgnoreCase("Avenue") &&
        !getParts(address)[2].equalsIgnoreCase("Street")) {
      return false;
    }
    if (((int) (getParts(address)[1].charAt(0)) - 48) <= 0 || (((int) getParts(address)[0].charAt(0)) - 48) <= 0) {
      return false;
    }
    if (getParts(address)[1].charAt(0) == '1') {
      if (!getParts(address)[1].substring(1, 3).equals("st")) {
        return false;
      }
    } else if (getParts(address)[1].charAt(0) == '2') {
      if (!getParts(address)[1].substring(1, 3).equals("nd")) {
        return false;
      }
    } else if (getParts(address)[1].charAt(0) == '3') {
      if (!getParts(address)[1].substring(1, 3).equals("rd")) {
        return false;
      }
    } else {
      if (!getParts(address)[1].substring(1, 3).equals("th")) {
        return false;
      }
    }
    return true;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4]
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address) {
    int[] block = { 0, 0 };
    // Fill in the code
    int avenue = 0;
    int street = 0;
    if (validAddress(address)) {
      if (getParts(address)[2].equalsIgnoreCase("Street")) {
        avenue = getParts(address)[0].charAt(0);
        street = getParts(address)[1].charAt(0);
      } else {
        avenue = getParts(address)[1].charAt(0);
        street = getParts(address)[0].charAt(0);
      }
    }
    block[0] = avenue - 48;
    block[1] = street - 48;
    return block;
  }

  // Calculates the distance in city blocks between the 'from' address and 'to'
  // address
  // Hint: be careful not to generate negative distances

  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to) {
    // Fill in the code or use this default code below. If you use
    // the default code then you are not eligible for any marks for this part
    int avenueFrom = getCityBlock(from)[0];
    int avenueTo = getCityBlock(to)[0];
    int streetFrom = getCityBlock(from)[1];
    int streetTo = getCityBlock(to)[1];
    int distance = Math.abs(avenueTo - avenueFrom) + Math.abs(streetTo - streetFrom);
    return (Math.abs(distance));

    // Math.random() generates random number from 0.0 to 0.999
    // Hence, Math.random()*17 will be from 0.0 to 16.999
    // double doubleRandomNumber = Math.random() * 17;
    // cast the double to whole number
    // int randomNumber = (int) doubleRandomNumber;
    // return (randomNumber);
  }

  public static int getCityZone(String address) {
    int zone = -1;
    if (validAddress(address)) {
      if (getCityBlock(address)[0] >= 1 && getCityBlock(address)[0] <= 5 && getCityBlock(address)[1] >= 6
          && getCityBlock(address)[1] <= 9) {
        zone = 0;
      } else if (getCityBlock(address)[0] >= 6 && getCityBlock(address)[0] <= 9 && getCityBlock(address)[1] >= 6
          && getCityBlock(address)[1] <= 9) {
        zone = 1;
      } else if (getCityBlock(address)[0] >= 6 && getCityBlock(address)[0] <= 9 && getCityBlock(address)[1] >= 1
          && getCityBlock(address)[1] <= 5) {
        zone = 2;
      } else if (getCityBlock(address)[0] >= 1 && getCityBlock(address)[0] <= 5 && getCityBlock(address)[1] >= 1
          && getCityBlock(address)[1] <= 5) {
        zone = 3;
      }
    }
    return zone;
  }
}
