// Marten Zaky 501224012
/*
 * 
 * General class that simulates a ride or a delivery in a simple Uber app
 * 
 * This class is made abstract since we never create an object. We only create subclass objects. 
 * 
 * Implement the Comparable interface and compare two service requests based on the distance
 */
abstract public class TMUberService {
  private String from;
  private String to;
  private User user;
  private String type; // Currently Ride or Delivery but other services could be added
  private int distance; // Units are City Blocks
  private double cost; // Cost of the service

  public TMUberService(String from, String to, User user, int distance, double cost, String type) {
    this.from = from;
    this.to = to;
    this.user = user;
    this.distance = distance;
    this.cost = cost;
    this.type = type;
  }

  // Subclasses define their type (e.g. "RIDE" OR "DELIVERY")
  abstract public String getServiceType();

  // Getters and Setters
  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  // Compare 2 service requests based on distance
  // Add the appropriate method
  public boolean equalDistance(int other) {
    if (this.distance == other) {
      return true;
    }
    return false;
  }

  // Check if 2 service requests are equal (this and other)
  // They are equal if its the same type and the same user
  // Make sure to check the type first
  public boolean equals(Object other) {
    // Fill in the code
    TMUberService service = (TMUberService) other;
    return type.equals(service.type) && user.equals(service.user);
  }

  // Print Information
  public void printInfo() {
    System.out.printf("\nType: %-9s From: %-15s To: %-15s", type, from, to);
    System.out.print("\nUser: ");
    user.printInfo();
    // System.out.print("\nDriver: ");
    // driver.printInfo();
  }
}
