package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.unipd.mtss.business.exeption.BillException;
import it.unipd.mtss.business.exeption.EItemNotFoundException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;

public class BillImplTest {

    @Test
    public void testGetOrderPrice() {
        BillImpl bill = new BillImpl();
        User user = new User(1, "Mario", "Rossi", "1990-05-01");

        List<EItem> items = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                new EItem(EItemType.Processor, "Processore2", 10.50),
                new EItem(EItemType.Processor, "Processore3", 6.50),
                new EItem(EItemType.Keyboard, "Tastiera1", 16.50),
                new EItem(EItemType.Mouse, "Mouse1", 3.50),
                new EItem(EItemType.Mouse, "Mouse2", 5.50),
                new EItem(EItemType.Mouse, "Mouse3", 8.50),
                new EItem(EItemType.Mouse, "Mouse4", 4.50),
                new EItem(EItemType.Mouse, "Mouse5", 2.50),
                new EItem(EItemType.Mouse, "mouse6", 6.50),
                new EItem(EItemType.Mouse, "Mouse7", 7.50),
                new EItem(EItemType.Mouse, "Mouse8", 9.50),
                new EItem(EItemType.Mouse, "Mouse9", 5.50),
                new EItem(EItemType.Mouse, "Mouse10", 6.50),
                new EItem(EItemType.Mouse, "Mouse11", 7.50)
        ));

        try {
            double price = bill.getOrderPrice(items, user);

            assertEquals(125.50, price, 0);
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
    public void testMoreThan10Mouse(){
        BillImpl bill = new BillImpl();

        List<EItem> items = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                new EItem(EItemType.Processor, "Processore2", 10.50),
                new EItem(EItemType.Processor, "Processore3", 6.50),
                new EItem(EItemType.Keyboard, "Tastiera1", 16.50),
                new EItem(EItemType.Mouse, "Mouse1", 3.50),
                new EItem(EItemType.Mouse, "Mouse2", 5.50),
                new EItem(EItemType.Mouse, "Mouse3", 8.50),
                new EItem(EItemType.Mouse, "Mouse4", 4.50),
                new EItem(EItemType.Mouse, "Mouse5", 2.50),
                new EItem(EItemType.Mouse, "mouse6", 6.50),
                new EItem(EItemType.Mouse, "Mouse7", 7.50),
                new EItem(EItemType.Mouse, "Mouse8", 9.50),
                new EItem(EItemType.Mouse, "Mouse9", 5.50),
                new EItem(EItemType.Mouse, "Mouse10", 6.50),
                new EItem(EItemType.Mouse, "Mouse11", 7.50)
        ));

        double lowerPrice = bill.moreThan10Mouse(items);

        assertEquals(2.50, lowerPrice, 0);
    }

    @Test
    public void testNoMoreThan10Mouse(){
        BillImpl bill = new BillImpl();

        List<EItem> items = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                new EItem(EItemType.Processor, "Processore2", 10.50),
                new EItem(EItemType.Processor, "Processore3", 6.50),
                new EItem(EItemType.Keyboard, "Tastiera1", 16.50),
                new EItem(EItemType.Mouse, "Mouse1", 3.50),
                new EItem(EItemType.Mouse, "Mouse2", 5.50),
                new EItem(EItemType.Mouse, "Mouse3", 8.50),
                new EItem(EItemType.Mouse, "Mouse4", 4.50),
                new EItem(EItemType.Mouse, "Mouse5", 2.50),
                new EItem(EItemType.Mouse, "mouse6", 6.50),
                new EItem(EItemType.Mouse, "Mouse7", 7.50),
                new EItem(EItemType.Mouse, "Mouse8", 9.50),
                new EItem(EItemType.Mouse, "Mouse9", 5.50),
                new EItem(EItemType.Mouse, "Mouse10", 6.50)
        ));

        double lowerPrice = bill.moreThan10Mouse(items);

        assertEquals(0, lowerPrice, 0);
    }
}
