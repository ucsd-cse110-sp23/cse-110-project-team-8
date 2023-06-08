package cse110.middleware;


import org.bson.Document;
import org.bson.types.ObjectId;

import com.google.gson.JsonObject;

/**
 * Class that stores Email Setup info.
 * Includes methods for conversion from and to Document and Json.
 */
public class EmailInfo {
    private String userId;
    private String firstName;
    private String lastName;
    private String displayName;
    private String emailAddress;
    private String smtp;
    private String tls;
    private String password;

    public static final String userIdKey = "username";
    public static final String firstNameKey = "firstName";
    public static final String lastNameKey= "lastName";
    public static final String displayNameKey = "displayName";
    public static final String emailAddressKey = "emailAddress";
    public static final String smtpKey = "SMTP";
    public static final String tlsKey = "TLS";
    public static final String passwordKey = "password";

    public EmailInfo() {
        userId = "";
        firstName = "";
        lastName = "";
        displayName = "";
        emailAddress = "";
        smtp = "";
        tls = "";
        password = "";
    }

    public EmailInfo setUserId(String userId) {this.userId = userId; return this;}
    public EmailInfo setFirstName(String firstName) {this.firstName= firstName; return this;}
    public EmailInfo setLastName(String lastName) {this.lastName= lastName; return this;}
    public EmailInfo setdisplayName(String displayname) {this.displayName= displayname; return this;}
    public EmailInfo setEmailAddress(String emailAddress) {this.emailAddress= emailAddress; return this;}
    public EmailInfo setSmtp(String smtp) {this.smtp= smtp; return this;}
    public EmailInfo setTls(String tls) {this.tls= tls; return this;}
    public EmailInfo setPassword(String password) {this.password= password; return this;}

    public String getUserId() {return this.userId;}
    public String getFirstName() {return this.firstName;}
    public String getLastName() {return this.lastName;}
    public String getDisplayName() {return this.displayName;}
    public String getEmailAddress() {return this.emailAddress;}
    public String getSmtp() {return this.smtp;}
    public String getTls() {return this.tls;}
    public String getPassword() {return this.password;}

    public JsonObject toJson() {
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

    public Document toDocument() {
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
