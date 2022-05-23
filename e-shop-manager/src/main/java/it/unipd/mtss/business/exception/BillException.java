////////////////////////////////////////////////////////////////////
// Andrea Volpe 2021904
// Riccardo Contin 1225416
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business.exception;

public class BillException extends Exception {
    private String message;

    public BillException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
