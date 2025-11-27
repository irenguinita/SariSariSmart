package Database.Users;

public class User {
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toCSVString(){
        return username + "," + password;
    }

    public static User fromCSVString(String csvLine){
        if (csvLine == null || csvLine.trim().isEmpty()){
            throw new IllegalArgumentException("CSV Line is either null or empty.");
        }

        String[] fields = csvLine.split(",");

        if (fields.length != 2){
            throw new IllegalArgumentException("Invalid CSV Format: Expected 3 fields");
        }

        try {
            String user = fields[0].trim();
            String pass = fields[1].trim();

            return new User(user, pass);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Parsing error", e);
        }
    }

}
