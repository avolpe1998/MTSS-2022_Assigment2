package it.unipd.mtss.business.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BillExceptionTest {
    @Test(expected = BillException.class)
    public void testThrowBillException() throws BillException {
        throwBillException();
    }

    @Test
    public void billExceptionGetMessage(){
        try {
            throwBillException();
        } catch (BillException e) {
            assertEquals("Eccezione lanciata", e.getMessage());
        }
    }

    private void throwBillException() throws BillException {
        throw new BillException("Eccezione lanciata");
    }
}
