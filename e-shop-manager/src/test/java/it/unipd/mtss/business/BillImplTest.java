package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;

public class BillImplTest {

    @Test
    public void testGetOrderPrice() throws BillException{
        BillImpl bill = new BillImpl();

        List<EItem> items = new ArrayList<EItem>(List.of( 
            new EItem(EItemType.Processor, "Processore1", 14.50),
            new EItem(EItemType.Motherboard, "Scheda1", 12.50),
            new EItem(EItemType.Processor, "Processore2", 10.50),
            new EItem(EItemType.Processor, "Processore3", 6.50),
            new EItem(EItemType.Keyboard, "Tastiera1", 16.50),
            new EItem(EItemType.Mouse, "Mouse1", 3.50),
            new EItem(EItemType.Mouse, "Mouse2", 2.50)
        ));

        User user = new User(1, "Mario", "Rossi", "1990-05-01");

        double price = bill.getOrderPrice(items, user);

        assertEquals(66.5, price, 0);
    }

    @Test
    public void testLessExpensiveEItem(){
        EItem processor3;
        List<EItem> items = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                new EItem(EItemType.Processor, "Processore2", 10.50),
                processor3 = new EItem(EItemType.Processor, "Processore3", 6.50),
                new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
            ));

        Optional<EItem> cheaperEItem = BillImpl.lessExpensiveEItem(items, EItemType.Processor);     
        
        assertEquals(processor3, cheaperEItem.get());
    }

    @Test
    public void testLessExpensiveEItemNotFound(){
        List<EItem> items = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                new EItem(EItemType.Processor, "Processore2", 10.50),
                new EItem(EItemType.Processor, "Processore3", 6.50),
                new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
            ));

        Optional<EItem> cheaperEItem = BillImpl.lessExpensiveEItem(items, EItemType.Mouse);   
        
        assertFalse(cheaperEItem.isPresent());
    }

    @Test
    public void testLessExpensiveEItemNotFoundOnEmptyList(){
        List<EItem> items = new ArrayList<>();

        Optional<EItem> cheaperEItem = BillImpl.lessExpensiveEItem(items, EItemType.Mouse);   
        
        assertFalse(cheaperEItem.isPresent());  
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
    
    public void testNumberOfEItem(){
        List<EItem> items = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                new EItem(EItemType.Processor, "Processore2", 10.50),
                new EItem(EItemType.Processor, "Processore3", 6.50),
                new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
            ));

        int numberOfProcessors = BillImpl.numberOfEItem(items, EItemType.Processor);

        assertEquals(3, numberOfProcessors);
    }

    @Test
    public void testEmptyNumberOfEItem(){
        List<EItem> items = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                new EItem(EItemType.Processor, "Processore2", 10.50),
                new EItem(EItemType.Processor, "Processore3", 6.50),
                new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
            ));

        int numberOfMouses = BillImpl.numberOfEItem(items, EItemType.Mouse);

        assertEquals(0, numberOfMouses);
    }

    @Test
    public void testGetOrderPrice5Processors() throws BillException {
        BillImpl bill = new BillImpl();
        User user = new User(1, "Mario", "Rossi", "1985-05-16");
        List<EItem> items = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                new EItem(EItemType.Processor, "Processore2", 10.50),
                new EItem(EItemType.Processor, "Processore3", 6.50),
                new EItem(EItemType.Keyboard, "Tastiera1", 16.50),
                new EItem(EItemType.Processor, "Processore4", 9.50),
                new EItem(EItemType.Processor, "Processore5", 8.50),
                new EItem(EItemType.Processor, "Processore6", 8.50)
            ));

        double orderPrice = bill.getOrderPrice(items, user);

        assertEquals(83.75, orderPrice, 0);
    }

    @Test
    public void testApplyDiscount(){
        double price = 30;
        double discount = 0.5;

        double dicountedPrice = BillImpl.applyDiscount(price, discount);

        assertEquals(15, dicountedPrice, 0);
    }

    @Test
    public void testTotalGreaterThan1000() throws BillException{
        BillImpl bill = new BillImpl();

        List<EItem> items = new ArrayList<EItem>(List.of( 
            new EItem(EItemType.Processor, "Processore1", 140.50),
            new EItem(EItemType.Motherboard, "Scheda1", 120.50),
            new EItem(EItemType.Processor, "Processore2", 100.50),
            new EItem(EItemType.Processor, "Processore3", 600.50),
            new EItem(EItemType.Keyboard, "Tastiera1", 160.50),
            new EItem(EItemType.Mouse, "Mouse1", 30.50),
            new EItem(EItemType.Mouse, "Mouse2", 20.50)
        ));

        User user = new User(1, "Mario", "Rossi", "1990-05-01");

        double price = bill.getOrderPrice(items, user);

        assertEquals(1056.15, price, 0);
    }
    
    public void testGetOrderPriceMoreThan10Mouse() throws BillException{
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

        double price = bill.getOrderPrice(items, user);
        
        assertEquals(125.50, price, 0);
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
    
    @Test 
    public void testGetOrderPriceSameMousesAndKeyboards() throws BillException{
        BillImpl bill = new BillImpl();
        User user = new User(1, "Mario", "Rossi", "1985-05-16");
        List<EItem> items = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Keyboard, "Tastiera1", 12.50),
                new EItem(EItemType.Mouse, "Mouse1", 10.50),
                new EItem(EItemType.Keyboard, "Tastiera2", 6.50),
                new EItem(EItemType.Mouse, "Mouse2", 16.50)
            ));

        double orderPrice = bill.getOrderPrice(items, user);

        assertEquals(54, orderPrice, 0);
    }
}
