package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
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
    public void testGetOrderPriceWith10FreeOrdersForUnder18WrongTime() throws BillException{
        BillImpl.numOrderGifted = 0;
        BillImpl.time = LocalTime.of(19,0);
        BillImpl bill = new BillImpl();
        List<EItem> items1 = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50)
            ));
        List<EItem> items2 = new ArrayList<>(List.of(
            new EItem(EItemType.Keyboard, "Tastiera1", 6.30),
            new EItem(EItemType.Keyboard, "Tastiera2", 12.50)
        ));
        User user1 = new User(1, "Lorenzo", "Gialli", "2014-09-16");
        User user2 = new User(1, "Valentino", "Bianchi", "2010-12-16");
        
        double priceOrder1 = bill.getOrderPrice(items1, user1);
        double priceOrder2 = bill.getOrderPrice(items2, user2);

        assertEquals(27, priceOrder1, 0);
        assertEquals(18.80, priceOrder2, 0);
    }

    @Test
    public void testGetOrderPriceWith10FreeOrdersForUnder18() throws BillException{
        BillImpl.numOrderGifted = 0;
        BillImpl.time = LocalTime.of(18,5);
        BillImpl bill = new BillImpl();
        List<EItem> items1 = new ArrayList<>(List.of(
                new EItem(EItemType.Processor, "Processore1", 14.50),
                new EItem(EItemType.Motherboard, "Scheda1", 12.50)
            ));
        List<EItem> items2 = new ArrayList<>(List.of(
            new EItem(EItemType.Keyboard, "Tastiera1", 6.30),
            new EItem(EItemType.Keyboard, "Tastiera2", 12.50)
        ));
        User user1 = new User(1, "Lorenzo", "Gialli", "2014-09-16");
        User user2 = new User(1, "Valentino", "Bianchi", "2010-12-16");
        
        double priceOrder1 = bill.getOrderPrice(items1, user1);
        double priceOrder2 = bill.getOrderPrice(items1, user2);
        double priceOrder3 = bill.getOrderPrice(items2, user1);
        double priceOrder4 = bill.getOrderPrice(items2, user1);
        double priceOrder5 = bill.getOrderPrice(items2, user1);
        double priceOrder6 = bill.getOrderPrice(items1, user2);
        double priceOrder7 = bill.getOrderPrice(items2, user2);
        double priceOrder8 = bill.getOrderPrice(items1, user2);
        double priceOrder9 = bill.getOrderPrice(items2, user1);
        double priceOrder10 = bill.getOrderPrice(items1, user1);
        double priceOrder11 = bill.getOrderPrice(items1, user2);

        assertEquals(0, priceOrder1, 0);
        assertEquals(0, priceOrder2, 0);
        assertEquals(0, priceOrder3, 0);
        assertEquals(0, priceOrder4, 0);
        assertEquals(0, priceOrder5, 0);
        assertEquals(0, priceOrder6, 0);
        assertEquals(0, priceOrder7, 0);
        assertEquals(0, priceOrder8, 0);
        assertEquals(0, priceOrder9, 0);
        assertEquals(0, priceOrder10, 0);
        assertEquals(27, priceOrder11, 0);
    }

    @Test
    public void testIsOrderFree(){
        BillImpl.numOrderGifted = 0;
        BillImpl.time = LocalTime.of(18,5);
        User user1 = new User(1, "Lorenzo", "Gialli", "2014-09-16");

        boolean orderFree = BillImpl.isOrderFree(user1);

        assertTrue(orderFree);
    }

    @Test
    public void testIsOrderFreeTimeWrong(){
        BillImpl.numOrderGifted = 0;
        BillImpl.time = LocalTime.of(19,5);
        User user1 = new User(1, "Lorenzo", "Gialli", "2014-09-16");

        boolean orderFree = BillImpl.isOrderFree(user1);

        assertFalse(orderFree);
    }

    @Test
    public void testIsOrderFreeUserNotUnder18(){
        BillImpl.numOrderGifted = 0;
        BillImpl.time = LocalTime.of(18,5);
        User user1 = new User(1, "Lorenzo", "Gialli", "1985-09-16");

        boolean orderFree = BillImpl.isOrderFree(user1);

        assertFalse(orderFree);
    }

    @Test
    public void testIsOrderFreeGiftedAlready10Orders(){
        BillImpl.numOrderGifted = 10;
        BillImpl.time = LocalTime.of(18,5);
        User user1 = new User(1, "Lorenzo", "Gialli", "2014-09-16");

        boolean orderFree = BillImpl.isOrderFree(user1);

        assertFalse(orderFree);
    }
}
