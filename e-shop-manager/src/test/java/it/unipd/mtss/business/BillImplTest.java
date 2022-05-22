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

    @Test(expected = BillException.class)
    public void testGetOrderPriceMoreThan30EItems() throws BillException{
        BillImpl bill = new BillImpl();
        User user = new User(1, "Mario", "Rossi", "1985-05-16");
        List<EItem> items = new ArrayList<>();
        for(int i = 0; i <= 30; i++){
            items.add(new EItem(EItemType.Processor, "Processore"+(i+1), i + 5));
        }

        bill.getOrderPrice(items, user);
    }
}
