
// Marten Zaky 501224012
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.TreeMap;

//import Driver.Status;

/*
 * 
 * This class contains the main logic of the system.
 *   
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager {
  private Map<String, User> usersMap;
  private ArrayList<User> usersArray;
  private ArrayList<Driver> drivers;

  public double totalRevenue; // Total revenues accumulated via rides and deliveries

  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to a Regular driver
  private static final double PAYRATE = 0.1;

  private Queue<TMUberService> zone1;
  private Queue<TMUberService> zone2;
  private Queue<TMUberService> zone3;
  private Queue<TMUberService> zone4;
  private Queue<TMUberService>[] serviceRequests;

  // These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager() {
    usersMap = new TreeMap<String, User>();
    usersArray = new ArrayList<User>();
    drivers = new ArrayList<Driver>();

    zone1 = new LinkedList<TMUberService>();
    zone2 = new LinkedList<TMUberService>();
    zone3 = new LinkedList<TMUberService>();
    zone4 = new LinkedList<TMUberService>();
    serviceRequests = new Queue[] { zone1, zone2, zone3, zone4 };

    totalRevenue = 0;
  }

  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId) {
    return usersMap.get(accountId);
  }

  // Given a driver id, find driver in list of drivers
  // Return null if not found
  public Driver getDriver(String driverId) {
    for (int i = 0; i < drivers.size(); i++) {
      if (drivers.get(i).getId().equals(driverId)) {
        return drivers.get(i);
      }
    }
    return null;
  }

  // Check for duplicate user
  private boolean userExists(User user) {
    usersArray = new ArrayList<User>(usersMap.values());
    for (int i = 0; i < usersArray.size(); i++) {
      if (usersArray.get(i).equals(user)) {
        return true;
      }
    }
    return false;
  }

  // Check for duplicate driver
  private boolean driverExists(Driver driver) {
    for (int i = 0; i < drivers.size(); i++) {
      if (drivers.get(i).equals(driver)) {
        return true;
      }
    }
    return false;
  }

  // Given a user, check if user ride/delivery request already exists in service
  // requests
  private boolean existingRequest(TMUberService req) {
    // Checks if request is a DELIVERY, if so then compares it with other DELIVERY
    // requests only
    if (req.getServiceType().equals("DELIVERY")) {
      for (int i = 0; i < serviceRequests.length; i++) {
        Iterator<TMUberService> reqs = serviceRequests[i].iterator();
        while (reqs.hasNext()) {
          TMUberService nextreq = reqs.next();
          if (nextreq.getServiceType().equals("DELIVERY") && nextreq.equals(req)) {
            return true;
          } else {
            continue;
          }
        }
      }
    } // ELse if request is RIDE then compare to only RIDE requests
    else if (req.getServiceType().equals("RIDE")) {
      for (int i = 0; i < serviceRequests.length; i++) {
        Iterator<TMUberService> reqs = serviceRequests[i].iterator();
        while (reqs.hasNext()) {
          TMUberService nextreq = reqs.next();
          if (nextreq.getServiceType().equals("RIDE") && nextreq.equals(req)) {
            return true;
          } else {
            continue;
          }
        }
      }
    }
    return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance
  private double getDeliveryCost(int distance) {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance) {
    return distance * RIDERATE;
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers() {
    System.out.println();
    int count = 0;
    for (String keys : usersMap.keySet()) {
      count += 1;
      System.out.printf("%-2s. ", count);
      User user = usersMap.get(keys);
      user.printInfo();
      System.out.println();
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers() {
    System.out.println();
    for (int i = 0; i < drivers.size(); i++) {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println();
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests() {
    System.out.println();

    for (int i = 0; i < serviceRequests.length; i++) {
      int index = 1;
      Iterator<TMUberService> reqs = serviceRequests[i].iterator();
      System.out.println("ZONE " + i + "\n" + "======");
      while (reqs.hasNext()) {
        TMUberService nextreq = reqs.next();
        System.out.printf("%-2s. ", index);
        nextreq.printInfo();
        System.out.println();
        index += 1;
      }
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet) {
    if (name == null || name == "") {
      throw new InvalidUserDataException("Invalid User Name");
    }
    if (!CityMap.validAddress(address) || address == "") {
      throw new InvalidUserDataException("Invalid User Address");
    }
    if (wallet < 0) {
      throw new InvalidUserDataException("Invalid Money in Wallet");
    }
    // Checking if this user already exists!
    usersArray = new ArrayList<User>(usersMap.values());
    User newUser = new User(TMUberRegistered.generateUserAccountId(usersArray), name, address, wallet);
    if (userExists(newUser)) {
      throw new ExistingException("User Already Exists in System");
    }
    usersMap.put(newUser.getAccountId(), newUser);
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address) {
    // Fill in the code - see the assignment document for error conditions
    // that might apply. See comments above in registerNewUser
    if (name == null || name == "") {
      throw new InvalidDriverDataException("Invalid Driver Name");
    }
    if (carModel == null || carModel == "") {
      throw new InvalidDriverDataException("Invalid Car Model");
    }
    if (carLicencePlate == null || carLicencePlate == "") {
      throw new InvalidDriverDataException("Invalid Car Licence Plate");
    }
    if (!CityMap.validAddress(address)) {
      throw new InvalidDriverDataException("Invalid Driver Address");
    }
    Driver newDriver = new Driver(TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate, address);
    if (driverExists(newDriver)) {
      throw new ExistingException("Driver Already Exists in System");
    }
    drivers.add(newDriver);
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to) {
    // Check for valid parameters
    // Use the account id to find the user object in the list of users
    User user = getUser(accountId);
    if (user == null) {
      throw new UserNotFoundException("User Account Not Found");
    }

    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) {
      throw new InvalidAddressException("Invalid Address");
    }
    // Get the distance for this ride
    int distance = CityMap.getDistance(from, to);
    // Note: distance must be > 1 city block!
    if (distance <= 1) {
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }
    double cost = getRideCost(distance);
    if (cost > user.getWallet()) {
      throw new InsufficientFundsException("Insufficient Funds");
    }
    // Create the TMUberRide object
    TMUberRide trip = new TMUberRide(from, to, user, distance, cost);
    // Check if existing ride request for this user - only one ride request per user
    // at a time!
    if (existingRequest(trip)) {
      throw new ExistingException("User Already Has Ride Request");
    }
    // Add the ride request to the list of requests
    int i = CityMap.getCityZone(from);
    serviceRequests[i].add(trip);
    // Increment the number of rides for this user
    user.addRide();
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId) {
    // See the comments above and use them as a guide
    User user = getUser(accountId);
    if (user == null) {
      throw new UserNotFoundException("User Account Not Found");
    }

    if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) {
      throw new InvalidAddressException("Invalid Address");
    }

    int distance = CityMap.getDistance(from, to);
    if (distance <= 1) {
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }

    double cost = getDeliveryCost(distance);
    if (cost > user.getWallet()) {
      throw new InsufficientFundsException("Insufficient Funds");
    }

    TMUberDelivery delivery = new TMUberDelivery(from, to, user, distance, cost, restaurant, foodOrderId);
    // For deliveries, an existing delivery has the same user, restaurant and food
    // order id
    // ONLY COMPARE WITH DELIVERY REQUESTS
    if (existingRequest(delivery)) {
      throw new ExistingException("User Already Has Delivery Request at Restaurant with this Food Order");
    }

    int i = CityMap.getCityZone(from);
    serviceRequests[i].add(delivery);
    // Increment the number of deliveries the user has had
    user.addDelivery();
  }

  // Cancel an existing service request.
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int request, int zoneNumber) {
    // Check if valid request #
    boolean valid = false;
    int removeindex = request - 1;
    if (zoneNumber >= 0 && zoneNumber <= 3 && removeindex < serviceRequests[zoneNumber].size() && removeindex >= 0) {
      valid = true;
    }
    if (valid == false) {
      throw new RequestNotFoundException("The Request number is incorrect or Zone number is invalid");
    }
    // Remove request from list
    // Iterator<TMUberService> iter = serviceRequests[zoneNumber].iterator();
    ArrayList<TMUberService> reqs = new ArrayList<>(serviceRequests[zoneNumber]);
    serviceRequests[zoneNumber].clear();
    /*
     * while (iter.hasNext()) {
     * iter.next();
     * reqs.add(serviceRequests[zoneNumber].remove());
     * }
     */
    for (int i = 0; i < reqs.size(); i++) {
      if (removeindex == i) {
        continue;
      }
      serviceRequests[zoneNumber].add(reqs.get(i));
    }
    // Decrement number of rides/deliveries for this user since it wasn't completed
    User user = reqs.get(removeindex).getUser();
    user.removeRide();
  }

  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public void dropOff(String driverId) {
    // Get the cost for the service and add to total revenues
    Driver driver = getDriver(driverId);
    if (driver == null) {
      throw new DriverNotFoundException("There is no Driver with this DriverId");
    }
    if (driver.getStatus() == Driver.Status.AVAILABLE) {
      throw new AvailabilityException("This Driver is not currently Driving/not completeing a Trip");
    } else if (driver.getStatus() == Driver.Status.DRIVING) {
      TMUberService trip = driver.getService();
      double cost = trip.getCost();
      totalRevenue += cost;
      // Pay the driver
      double driverFee = PAYRATE * cost;
      driver.pay(driverFee);
      // Change driver status
      driver.setStatus(Driver.Status.AVAILABLE);
      // Deduct driver fee from total revenues
      totalRevenue -= driverFee;
      // Deduct cost of service from user
      User user = trip.getUser();
      user.payForService(cost);
      // Remove the request from the queue of requests
      // int i = driver.getZone();
      // serviceRequests[i].remove();
      driver.setService(null);
      String address = trip.getTo();
      driver.setAddress(address);
      driver.setZone(CityMap.getCityZone(address));
    }
  }

  // Sort users by name
  // Then list all users
  public void sortByUserName() {
    usersArray = new ArrayList<User>(usersMap.values());
    Collections.sort(usersArray, new NameComparator());
    System.out.println();
    for (int i = 0; i < usersArray.size(); i++) {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      usersArray.get(i).printInfo();
      System.out.println();
    }
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User> {
    public int compare(User a, User b) {
      return a.getName().compareTo(b.getName());
    }
  }

  // Sort users by number amount in wallet
  // Then ist all users
  public void sortByWallet() {
    usersArray = new ArrayList<User>(usersMap.values());
    Collections.sort(usersArray, new UserWalletComparator());
    System.out.println();
    for (int i = 0; i < usersArray.size(); i++) {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      usersArray.get(i).printInfo();
      System.out.println();
    }
  }

  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User> {
    public int compare(User a, User b) {
      if (a.getWallet() < b.getWallet()) {
        return -1;
      }
      if (a.getWallet() > b.getWallet()) {
        return 1;
      }
      return 0;
    }
  }

  // Pick up method
  public void pickup(String driverId) {
    // Getting driver object from driverId and then finding the zone they are
    // currently in
    Driver driver = getDriver(driverId);
    if (driver == null) {
      throw new DriverNotFoundException("There is No Driver with this DriverId");
    }
    int currentZone = driver.getZone();
    // Remove the TMUberService object from the front of the queue for this zone
    if (serviceRequests[currentZone].isEmpty()) {
      throw new NoSuchElementException("There are currently no requests in this Driver's zone");
    }
    TMUberService trip = serviceRequests[currentZone].remove();
    // Set the new service variable in the Driver object to refer to this
    // TMUberService object
    driver.setService(trip);
    driver.setStatus(Driver.Status.DRIVING);
    // Set the driver address to the From address for this service request
    TMUberService req = driver.getService();
    String address = req.getFrom();
    driver.setAddress(address);
  }

  // Drive to method
  public void driveTo(String driverId, String address) {
    Driver driver = getDriver(driverId);
    if (driver == null) {
      throw new DriverNotFoundException("There is No Driver with this DriverId");
    }
    // Checking if driver is available and the drive to address is valid
    if (driver.getStatus() == Driver.Status.AVAILABLE && CityMap.validAddress(address)) {
      driver.setAddress(address);
      driver.setZone(CityMap.getCityZone(address));
    } else if (!CityMap.validAddress(address)) {
      throw new InvalidAddressException("Invalid Address");
    } else if (driver.getStatus() == Driver.Status.DRIVING) {
      throw new AvailabilityException("This driver is currently Driving - Complete trip first");
    }
  }

  // Set users method
  public void setUsers(ArrayList<User> userList) {
    for (User userRecord : userList) {
      usersMap.put(userRecord.getAccountId(), userRecord);
    }
  }

  // Set drivers method
  public void setDrivers(ArrayList<Driver> driverList) {
    for (Driver driverRecord : driverList) {
      drivers.add(driverRecord);
    }
  }
}

class DriverNotFoundException extends RuntimeException {
  public DriverNotFoundException() {
  }

  public DriverNotFoundException(String message) {
    super(message);
  }
}

class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}

class RequestNotFoundException extends RuntimeException {
  public RequestNotFoundException() {

  }

  public RequestNotFoundException(String message) {
    super(message);
  }
}

class InvalidAddressException extends RuntimeException {
  public InvalidAddressException() {
  }

  public InvalidAddressException(String message) {
    super(message);
  }
}

class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException() {
  }

  public InsufficientFundsException(String message) {
    super(message);
  }
}

class InsufficientDistanceException extends RuntimeException {
  public InsufficientDistanceException() {
  }

  public InsufficientDistanceException(String message) {
    super(message);
  }
}

class InvalidUserDataException extends RuntimeException {
  public InvalidUserDataException() {

  }

  public InvalidUserDataException(String message) {
    super(message);
  }
}

class InvalidDriverDataException extends RuntimeException {
  public InvalidDriverDataException() {

  }

  public InvalidDriverDataException(String message) {
    super(message);
  }
}

class ExistingException extends RuntimeException {
  public ExistingException() {

  }

  public ExistingException(String message) {
    super(message);
  }
}

class AvailabilityException extends RuntimeException {
  public AvailabilityException() {

  }

  public AvailabilityException(String message) {
    super(message);
  }
}