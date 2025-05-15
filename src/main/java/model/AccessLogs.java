package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AccessLogs implements Serializable {

    int logId;
    int userID;
    String action;
    LocalDateTime accessDate;

    public AccessLogs() {
    }

    public AccessLogs(int userID, String action, LocalDateTime accessDate) {
        this.userID = userID;
        this.action = action;
        this.accessDate = accessDate;
    }

    public int getLogId() {
        return this.logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getAccessDate() {
        return this.accessDate;
    }

    public void setAccessDate(LocalDateTime accessDate) {
        this.accessDate = accessDate;
    }

}