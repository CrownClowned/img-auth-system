package exception;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s
 */
public class SteganographyException extends Exception{
    private static final long serialVersionUID = 9031373046571531684L;

    public SteganographyException() {
        super();
    }

    public SteganographyException(String message) {
        super(message);
    }

    public SteganographyException(Throwable throwable) {
        super(throwable);
    }

    public SteganographyException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
