package cse110;


import org.bson.Document;
import org.bson.types.ObjectId;

import com.google.gson.JsonObject;

public class EmailInfo {
    private String userId;
    private String firstName;
    private String lastName;
    private String displayName;
    private String emailAddress;
    private String smtp;
    private String tls;
    private String password;

    static final String userIdKey = "userId";
    static final String firstNameKey = "firstName";
    static final String lastNameKey= "lastName";
    static final String displayNameKey = "displayName";
    static final String emailAddressKey = "emailAddress";
    static final String smtpKey = "SMTP";
    static final String tlsKey = "TLS";
    static final String passwordKey = "password";

    EmailInfo() {
        userId = "";
        firstName = "";
        lastName = "";
        displayName = "";
        emailAddress = "";
        smtp = "";
        tls = "";
        password = "";
    }

    EmailInfo setUserId(String userId) {this.userId = userId; return this;}
    EmailInfo setFirstName(String firstName) {this.firstName= firstName; return this;}
    EmailInfo setLastName(String lastName) {this.lastName= lastName; return this;}
    EmailInfo setdisplayName(String displayname) {this.displayName= displayname; return this;}
    EmailInfo setEmailAddress(String emailAddress) {this.emailAddress= emailAddress; return this;}
    EmailInfo setSmtp(String smtp) {this.smtp= smtp; return this;}
    EmailInfo setTls(String tls) {this.tls= tls; return this;}
    EmailInfo setPassword(String password) {this.password= password; return this;}

    String getUserId() {return this.userId;}
    String getFirstName() {return this.firstName;}
    String getLastName() {return this.lastName;}
    String getDisplayName() {return this.displayName;}
    String getEmailAddress() {return this.emailAddress;}
    String getSmtp() {return this.smtp;}
    String getTls() {return this.tls;}
    String getPassword() {return this.password;}

    JsonObject toJson() {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty(userIdKey, userId);
        jsonObj.addProperty(firstNameKey, firstName);
        jsonObj.addProperty(lastNameKey, lastName);
        jsonObj.addProperty(displayNameKey, displayName);
        jsonObj.addProperty(emailAddressKey, emailAddress);
        jsonObj.addProperty(smtpKey, smtp);
        jsonObj.addProperty(tlsKey, tls);
        jsonObj.addProperty(passwordKey, password);
        return jsonObj;
    }

    Document toDocument() {
        return new Document("_id", new ObjectId())
            .append(userIdKey, userId)
            .append(firstNameKey, firstName)
            .append(lastNameKey, lastName)
            .append(displayNameKey, displayName)
            .append(emailAddressKey, emailAddress)
            .append(smtpKey, smtp)
            .append(tlsKey, tls)
            .append(passwordKey, password);
    }

    public static EmailInfo fromJson(JsonObject jsonObj) {
        return new EmailInfo()
            .setUserId(jsonObj.get(userIdKey).getAsString())
            .setFirstName(jsonObj.get(firstNameKey).getAsString())
            .setLastName(jsonObj.get(lastNameKey).getAsString())
            .setdisplayName(jsonObj.get(displayNameKey).getAsString())
            .setEmailAddress(jsonObj.get(emailAddressKey).getAsString())
            .setSmtp(jsonObj.get(smtpKey).getAsString())
            .setTls(jsonObj.get(tlsKey).getAsString())
            .setPassword(jsonObj.get(passwordKey).getAsString());
    }

    public static EmailInfo fromDocument(Document doc) {
        return new EmailInfo()
            .setUserId((String) doc.get(userIdKey))
            .setFirstName((String) doc.get(firstNameKey))
            .setLastName((String) doc.get(lastNameKey))
            .setdisplayName((String) doc.get(displayNameKey))
            .setEmailAddress((String) doc.get(emailAddressKey))
            .setSmtp((String) doc.get(smtpKey))
            .setTls((String) doc.get(tlsKey))
            .setPassword((String) doc.get(passwordKey));
    }
}
