package com.buildria.eai.component.xlsbeans;

import java.util.List;
import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.LabelledCell;
import net.java.amateras.xlsbeans.annotation.LabelledCellType;
import net.java.amateras.xlsbeans.annotation.Sheet;

@Sheet(name = "UserList")
public class UserList {
    
    @LabelledCell(label = "title", type = LabelledCellType.Right)
    public String title;
    
    @HorizontalRecords(tableLabel = "User List", recordClass = User.class)
    public List<User> users;
    
    public User getUser(int id) {
        for (User user : users) {
            if (user.id == id) {
                return user;
            }
        }
        return null;
    }
}
