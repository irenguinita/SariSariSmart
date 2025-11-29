package Database.Users;

//import Database.*;
//import Database.Inventory.*;
import Database.CustomException.*;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final String DATA_FILE_CSV = "data/User/users.csv";
    private static final String LOG_FILE_TXT = "data/User/log.txt";
    private final List<User> userList;

    public UserManager(){
        this.userList = new ArrayList<>();

        try {
            loadUserFromCSV();
        } catch (FileNotFoundException e){
            logAction("User data file not found.");
        } catch (IOException e){
            System.err.println("ERROR: Could not load user data. " + e.getMessage());
        }
    }

    public void userFolder(){
        File dataDirectory = new File("data/User");
        if (!dataDirectory.exists()){
            boolean created = dataDirectory.mkdirs();
            if (created){
                System.out.println("Created data directory: " + dataDirectory.getAbsolutePath());
            } else {
                System.err.println("ERROR: Could not create directory");
            }
        } else {
            System.err.println("Folder already existed.");
        }
    }

    private void loadUserFromCSV() throws IOException {
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE_CSV))){
            String line;
            br.readLine(); // Skip the header line

            while((line = br.readLine()) != null){
                lineNumber++;

                if (line.trim().isEmpty()){
                    continue;
                }

                try {
                    User user = User.fromCSVString(line);
                    userList.add(user);
                } catch (NumberFormatException e){
                    System.err.println("Parsing error." + e.getMessage());
                    throw new NumberFormatException("Invalid format on line " + lineNumber + " in file " + DATA_FILE_CSV);
                }
            }
        } catch (IOException e){
            throw new FileNotFoundException("File not exist." + e.getMessage());
        }
    }

    public void saveUserToCSV() throws IOException {
        String[] header = {"Username", "Password"};

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE_CSV))){
            bw.write(String.join(",", header));
            bw.newLine();

            for (User user : userList){
                String userLine = user.toCSVString();

                bw.write(userLine);
                bw.newLine();
            }
        } catch (IOException e){
            System.err.println("Error while saving CSV File" + e.getMessage());
            throw e;
        }
    }

    private void logAction(String message) {
        LocalDateTime logTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeStamp = logTime.format(formatter);
        String logEntry = String.format("[%s] - %s", timeStamp, message);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE_TXT, true))){

            bw.write(logEntry);
            bw.newLine();
        } catch (IOException e){
            System.err.println("ERROR: Could not write to log file: " + LOG_FILE_TXT);
            System.err.println("Details: " + e.getMessage());
        }
    }

    public User login(String username, String password) throws LoginFailedException {
        User authenticatedUser = null;

        for (User user : userList){
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)){
                authenticatedUser = user;
                break;
            }
        }

        if (authenticatedUser != null){
            logAction("Successfully login: " + username);
            return authenticatedUser;
        } else {
            logAction("Failed to login: " + username);
            return null;
        }
    }

    public void registerUser(String username, String password) throws DuplicateUserException, IOException{
        for (User existingUser : userList){
            if (existingUser.getUsername().equalsIgnoreCase(username)){
                throw new DuplicateUserException("User already exist");
            }
        }

        User user = new User(username, password);
        userList.add(user);

        try {
            saveUserToCSV();
            logAction("User registered: " + username);
        } catch (IOException e){
            userList.remove(user);
            throw new IOException("Failed to save user", e);
        }
    }

    public void resetPassword(String username, String newPassword) throws UserNotFound {
        boolean userFound = false;
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                user.setPassword(newPassword);
                userFound = true;
                logAction("Password reset for: " + username);
                break;
            }
        }

        if (userFound){
            try {
                saveUserToCSV();
            } catch (IOException e) {
                logAction("Failed to save new password for " + username);
                System.err.println("ERROR: " + e.getMessage());
            }
        } else {
            throw new UserNotFound("ERROR: Could not find User or Email.");
        }
    }
}
