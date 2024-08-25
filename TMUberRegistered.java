
// Marten Zaky 501224012
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TMUberRegistered {
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountId = 9000;
    private static int firstDriverId = 7000;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current) {
        int nextId = firstUserAccountId + current.size();
        return "" + nextId;
    }

    public static String generateUserAccountIdMaps(Map<String, User> current) {
        int nextId = firstUserAccountId + current.size();
        return "" + nextId;
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current) {
        int nextId = firstDriverId + current.size();
        return "" + nextId;
    }

    // Database of Preregistered users
    // Read in Users database through input file and store in ArrayList
    public static ArrayList<User> loadPreregisteredUsers(String usersFile) throws FileNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        Scanner in = new Scanner(new File(usersFile));
        User record;
        while (in.hasNextLine()) {
            String name = in.nextLine();
            String address = in.nextLine();
            double wallet = in.nextDouble();
            if (in.hasNextLine()) {
                in.nextLine();
            }
            record = new User(generateUserAccountId(users), name, address, wallet);
            users.add(record);
        }
        in.close();
        return users;
    }

    // Database of Preregistered drivers
    // Read in Drivers database through input file and store in ArrayList
    public static ArrayList<Driver> loadPreregisteredDrivers(String driversFile) throws FileNotFoundException {
        ArrayList<Driver> drivers = new ArrayList<>();
        Scanner in = new Scanner(new File(driversFile));
        Driver record;
        while (in.hasNextLine()) {
            String name = in.nextLine();
            String model = in.nextLine();
            String plate = in.nextLine();
            String address = in.nextLine();
            record = new Driver(generateDriverId(drivers), name, model, plate, address);
            drivers.add(record);
        }
        in.close();
        return drivers;
    }
}
