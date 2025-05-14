package model;

import java.io.Serializable;
import java.util.Date;

public class AccessLogs implements Serializable {

    int logId;
    int userID;
    Date loginDate;
    Date logoutDate;

    public AccessLogs() {
    }

}