package ca.refactored.BusinessLogic;

import ca.refactored.EntityClasses.User;

public class Add_Remove_Friend_Logic {
    private User user_viewed;
    private boolean delete;

    public Add_Remove_Friend_Logic(User user_viewed, boolean delete){
        this.user_viewed = user_viewed;
        this.delete = delete;
    }

}
