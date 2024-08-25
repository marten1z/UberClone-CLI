
// Marten Zaky 501224012
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI {
  public static void main(String[] args) throws FileNotFoundException {
    // Create the System Manager - the main system code is in here

    TMUberSystemManager tmuber = new TMUberSystemManager();
    // TMUberRegistered tmuregistered = new TMUberRegistered();

    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine()) {
      try {
        String action = scanner.nextLine();

        if (action == null || action.equals("")) {
          System.out.print("\n>");
          continue;
        }
        // Quit the App
        else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
          return;
        // Print all the registered drivers
        else if (action.equalsIgnoreCase("DRIVERS")) // List all drivers
        {
          tmuber.listAllDrivers();
        }
        // Print all the registered users
        else if (action.equalsIgnoreCase("USERS")) // List all users
        {
          tmuber.listAllUsers();
        }
        // Print all current ride requests or delivery requests
        else if (action.equalsIgnoreCase("REQUESTS")) // List all requests
        {
          tmuber.listAllServiceRequests();
        }
        // Register a new driver
        else if (action.equalsIgnoreCase("REGDRIVER")) {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine()) {
            name = scanner.nextLine();
          }
          String carModel = "";
          System.out.print("Car Model: ");
          if (scanner.hasNextLine()) {
            carModel = scanner.nextLine();
          }
          String license = "";
          System.out.print("Car License: ");
          if (scanner.hasNextLine()) {
            license = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine()) {
            address = scanner.nextLine();
          }
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-10s", name, carModel,
              license, address);
        }

        // Register a new user
        else if (action.equalsIgnoreCase("REGUSER")) {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine()) {
            name = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine()) {
            address = scanner.nextLine();
          }
          double wallet = 0.0;
          System.out.print("Wallet: ");
          if (scanner.hasNextDouble()) {
            wallet = scanner.nextDouble();
            scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
          }
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        }
        // Request a ride
        else if (action.equalsIgnoreCase("REQRIDE")) {
          String accountId = "";
          System.out.print("User Account Id: ");
          if (scanner.hasNextLine()) {
            accountId = scanner.nextLine();
          }
          String from = "";
          System.out.print("From Address: ");
          if (scanner.hasNextLine()) {
            from = scanner.nextLine();
          }
          String to = "";
          System.out.print("To Address: ");
          if (scanner.hasNextLine()) {
            to = scanner.nextLine();
          }
          tmuber.requestRide(accountId, from, to);
          System.out.printf("RIDE for: %-10s From: %-15s To: %-15s", accountId, from, to);
        }

        // Request a food delivery
        else if (action.equalsIgnoreCase("REQDLVY")) {
          String accountId = "";
          System.out.print("User Account Id: ");
          if (scanner.hasNextLine()) {
            accountId = scanner.nextLine();
          }
          String from = "";
          System.out.print("From Address: ");
          if (scanner.hasNextLine()) {
            from = scanner.nextLine();
          }
          String to = "";
          System.out.print("To Address: ");
          if (scanner.hasNextLine()) {
            to = scanner.nextLine();
          }
          String restaurant = "";
          System.out.print("Restaurant: ");
          if (scanner.hasNextLine()) {
            restaurant = scanner.nextLine();
          }
          String orderId = "";
          System.out.print("Food Order #: ");
          if (scanner.hasNextLine()) {
            orderId = scanner.nextLine();
          }
          tmuber.requestDelivery(accountId, from, to, restaurant, orderId);
          System.out.printf("DELIVERY for: %-15s From: %-15s To: %-15s Restaurant: %-15s OrderID: %-15s", accountId,
              from, to, restaurant, orderId);
        }
        // Sort users by name
        else if (action.equalsIgnoreCase("SORTBYNAME")) {
          tmuber.sortByUserName();
        }
        // Sort users by number of ride they have had
        else if (action.equalsIgnoreCase("SORTBYWALLET")) {
          tmuber.sortByWallet();
        }
        // Cancel a current service (ride or delivery) request
        else if (action.equalsIgnoreCase("CANCELREQ")) {
          int zoneNumber = -1;
          int reqNumber = -1;
          System.out.print("Zone #: ");
          if (scanner.hasNextInt()) {
            zoneNumber = scanner.nextInt();
            scanner.nextLine();
          }
          System.out.print("Request #: ");
          if (scanner.hasNextInt()) {
            reqNumber = scanner.nextInt();
            scanner.nextLine();
          }
          tmuber.cancelServiceRequest(reqNumber, zoneNumber);
          System.out.println("Service request #" + reqNumber + " cancelled");
        }
        // Drop-off the user or the food delivery to the destination address
        else if (action.equalsIgnoreCase("DROPOFF")) {
          String driverId = "";
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine()) {
            driverId = scanner.nextLine();
          }
          tmuber.dropOff(driverId);
          System.out.println("Successful Drop Off - Driver " + driverId + " Dropping Off");
        }
        // Get the Current Total Revenues
        else if (action.equalsIgnoreCase("REVENUES")) {
          System.out.println("Total Revenue: " + tmuber.totalRevenue);
        }
        // Unit Test of Valid City Address
        else if (action.equalsIgnoreCase("ADDR")) {
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine()) {
            address = scanner.nextLine();
          }
          System.out.print(address);
          if (CityMap.validAddress(address))
            System.out.println("\nValid Address");
          else
            System.out.println("\nBad Address");
        }
        // Unit Test of CityMap Distance Method
        else if (action.equalsIgnoreCase("DIST")) {
          String from = "";
          System.out.print("From: ");
          if (scanner.hasNextLine()) {
            from = scanner.nextLine();
          }
          String to = "";
          System.out.print("To: ");
          if (scanner.hasNextLine()) {
            to = scanner.nextLine();
          }
          System.out.print("\nFrom: " + from + " To: " + to);
          System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
        } else if (action.equalsIgnoreCase("PICKUP")) {
          String driverId = "";
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine()) {
            driverId = scanner.nextLine();
          }
          tmuber.pickup(driverId);
          String address = tmuber.getDriver(driverId).getAddress();
          System.out.println("Driver " + driverId + " Picking Up in Zone " + CityMap.getCityZone(address));
        } else if (action.equalsIgnoreCase("LOADUSERS")) {
          String usersFile = "";
          System.out.print("Users File: ");
          if (scanner.hasNextLine()) {
            usersFile = scanner.nextLine();
          }
          try {
            tmuber.setUsers(TMUberRegistered.loadPreregisteredUsers(usersFile));
            System.out.println("Users Loaded");
          } catch (FileNotFoundException e) {
            System.out.print("Users File: " + usersFile + " Not Found");
          }
        } else if (action.equalsIgnoreCase("LOADDRIVERS")) {
          String driversFile = "";
          System.out.print("Drivers File: ");
          if (scanner.hasNextLine()) {
            driversFile = scanner.nextLine();
          }
          try {
            tmuber.setDrivers(TMUberRegistered.loadPreregisteredDrivers(driversFile));
            System.out.println("Drivers Loaded");
          } catch (FileNotFoundException e) {
            System.out.print("Drivers File: " + driversFile + " Not Found");
          }
        } else if (action.equalsIgnoreCase("DRIVETO")) {
          String driverId = "";
          String address = "";
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine()) {
            driverId = scanner.nextLine();
          }
          System.out.print("Address: ");
          if (scanner.hasNextLine()) {
            address = scanner.nextLine();
          }
          tmuber.driveTo(driverId, address);
          System.out.println("Driver " + driverId + " is now in Zone " + CityMap.getCityZone(address));
        }
        System.out.print("\n>");
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
        System.out.print("\n>");
      }
    }
  }
}
