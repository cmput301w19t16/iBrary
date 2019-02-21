package ca.rededaniskal;

import java.awt.image.*;

public class BookInstance extends Book {

    private String Owner;
    private String Borrowed;
    private BufferedImage bookImage;
    private String Status;

    public void setStatus(String status) {
        Status = status;
    }
}
