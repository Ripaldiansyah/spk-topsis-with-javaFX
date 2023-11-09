package ac.id.unindra.spk.topsis.djingga.models;

import javafx.beans.property.SimpleStringProperty;


public class userTableModel {
    private SimpleStringProperty id,fullName,username,role,accountStatus;

    public userTableModel(String id, String fullName, String username, String role, String accountStatus ) {
        this.id = new SimpleStringProperty(id);
        this.fullName =  new SimpleStringProperty(fullName);
        this.username =  new SimpleStringProperty(username);
        this.role =  new SimpleStringProperty(role);
        this.accountStatus =  new SimpleStringProperty(accountStatus);
       
    }
    public String getId() {
        return id.get();
    }
    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }
    public String getFullName() {
        return fullName.get();
    }
    public void setFullName(String fullName) {
        this.fullName = new SimpleStringProperty(fullName);
    }
    public String getUsername() {
        return username.get();
    }
    public void setUsername(String username) {
        this.username =  new SimpleStringProperty(username);
    }
    public String getRole() {
        return role.get();
    }
    public void setRole(String role) {
        this.role = new SimpleStringProperty(role);
    }
    public String getAccountStatus() {
        return accountStatus.get();
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus =  new SimpleStringProperty(accountStatus);
    }
   
    

    
}
