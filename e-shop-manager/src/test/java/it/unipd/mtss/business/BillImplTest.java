package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.business.exception.EItemNotFoundException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;

public class BillImplTest {

    @Test
    public void testGetOrderPrice(){
        BillImpl bill = new BillImpl();

        List<EItem> items = new ArrayList<EItem>(List.of( 
            new EItem(EItemType.Processor, "Processore1", 14.50),
            new EItem(EItemType.Motherboard, "Scheda1", 12.50),
            new EItem(EItemType.Processor, "Processore2", 10.50),
            new EItem(EItemType.Processor, "Processore3", 6.50),
            new EItem(EItemType.Keyboard, "Tastiera1", 16.50),
            new EItem(EItemType.Mouse, "Mouse1", 3.50)
        ));

        User user = new User(1, "Mario", "Rossi", "1990-05-01");

        try {
            double price = bill.getOrderPrice(items, user);

            assertEquals(64.0, price, 0);
        } catch (BillException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLessExpensiveEItem() throws EItemNotFoundException {
        EItem processor1, processor2, processor3, motherboard1, keyboard1;
        List<EItem> items = new ArrayList<>(List.of(
                processor1 = new EItem(EItemType.Processor, "Processore1", 14.50),
                motherboard1 = new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                processor2 = new EItem(EItemType.Processor, "Processore2", 10.50),
                processor3 = new EItem(EItemType.Processor, "Processore3", 6.50),
                keyboard1 = new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
            ));

        EItem cheaperEItem = BillImpl.lessExpensiveEItem(items, EItemType.Processor);     
        
        assertEquals(processor3, cheaperEItem);
    }

    @Test(expected = EItemNotFoundException.class)
    public void testThrowsEItemNotFoundException() throws EItemNotFoundException {
        EItem processor1, processor2, processor3, motherboard1, keyboard1;
        List<EItem> items = new ArrayList<>(List.of(
                processor1 = new EItem(EItemType.Processor, "Processore1", 14.50),
                motherboard1 = new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                processor2 = new EItem(EItemType.Processor, "Processore2", 10.50),
                processor3 = new EItem(EItemType.Processor, "Processore3", 6.50),
                keyboard1 = new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
            ));

        EItem cheaperEItem = BillImpl.lessExpensiveEItem(items, EItemType.Mouse);     
    }

    @Test(expected = EItemNotFoundException.class)
    public void testThrowsEItemNotFoundExceptionOnEmptyList() throws EItemNotFoundException {
        List<EItem> items = new ArrayList<>();

        EItem cheaperEItem = BillImpl.lessExpensiveEItem(items, EItemType.Mouse);     
    }

    @Test
    public void testGetTotal(){
        List<EItem> items = new ArrayList<EItem>(List.of( 
            new EItem(EItemType.Processor, "Processore1", 14.50),
            new EItem(EItemType.Motherboard, "Scheda1", 12.50),
            new EItem(EItemType.Processor, "Processore2", 10.50),
            new EItem(EItemType.Processor, "Processore3", 6.50),
            new EItem(EItemType.Keyboard, "Tastiera1", 16.50),
            new EItem(EItemType.Mouse, "Mouse1", 3.50)
        ));

        double total = BillImpl.getTotal(items);

        assertEquals(64.0, total, 0);
    }

    @Test
    public void testGetTotalEmptyList(){
        List<EItem> items = new ArrayList<EItem>();

        double total = BillImpl.getTotal(items);

        assertEquals(0, total, 0);
    }
}
