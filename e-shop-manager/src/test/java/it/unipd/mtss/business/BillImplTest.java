package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import it.unipd.mtss.business.exeption.BillException;
import it.unipd.mtss.model.User;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;

public class BillImplTest {

    @Test
    public void testLessExpensiveEItem(){
        EItem processor1, processor2, processor3, motherboard1, keyboard1;
        List<EItem> items = new ArrayList<>(List.of(
                processor1 = new EItem(EItemType.Processor, "Processore1", 14.50),
                motherboard1 = new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                processor2 = new EItem(EItemType.Processor, "Processore2", 10.50),
                processor3 = new EItem(EItemType.Processor, "Processore3", 6.50),
                keyboard1 = new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
            ));

        Optional<EItem> cheaperEItem = BillImpl.lessExpensiveEItem(items, EItemType.Processor);     
        
        assertEquals(processor3, cheaperEItem.get());
    }

    @Test
    public void testLessExpensiveEItemNotFound(){
        EItem processor1, processor2, processor3, motherboard1, keyboard1;
        List<EItem> items = new ArrayList<>(List.of(
                processor1 = new EItem(EItemType.Processor, "Processore1", 14.50),
                motherboard1 = new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                processor2 = new EItem(EItemType.Processor, "Processore2", 10.50),
                processor3 = new EItem(EItemType.Processor, "Processore3", 6.50),
                keyboard1 = new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
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
    public void testNumberOfEItem(){
        EItem processor1, processor2, processor3, motherboard1, keyboard1;
        List<EItem> items = new ArrayList<>(List.of(
                processor1 = new EItem(EItemType.Processor, "Processore1", 14.50),
                motherboard1 = new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                processor2 = new EItem(EItemType.Processor, "Processore2", 10.50),
                processor3 = new EItem(EItemType.Processor, "Processore3", 6.50),
                keyboard1 = new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
            ));

        int numberOfProcessors = BillImpl.numberOfEItem(items, EItemType.Processor);

        assertEquals(3, numberOfProcessors);
    }

    @Test
    public void testEmptyNumberOfEItem(){
        EItem processor1, processor2, processor3, motherboard1, keyboard1;
        List<EItem> items = new ArrayList<>(List.of(
                processor1 = new EItem(EItemType.Processor, "Processore1", 14.50),
                motherboard1 = new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                processor2 = new EItem(EItemType.Processor, "Processore2", 10.50),
                processor3 = new EItem(EItemType.Processor, "Processore3", 6.50),
                keyboard1 = new EItem(EItemType.Keyboard, "Tastiera1", 16.50)
            ));

        int numberOfMouses = BillImpl.numberOfEItem(items, EItemType.Mouse);

        assertEquals(0, numberOfMouses);
    }

    @Test
    public void testGetOrderPrice5Processors() throws BillException {
        BillImpl bill = new BillImpl();
        User user = new User(1, "Mario", "Rossi", "1985-05-16");
        EItem processor1, processor2, processor3, motherboard1, keyboard1,
              processor4, processor5, processor6;
        List<EItem> items = new ArrayList<>(List.of(
                processor1 = new EItem(EItemType.Processor, "Processore1", 14.50),
                motherboard1 = new EItem(EItemType.Motherboard, "Scheda1", 12.50),
                processor2 = new EItem(EItemType.Processor, "Processore2", 10.50),
                processor3 = new EItem(EItemType.Processor, "Processore3", 6.50),
                keyboard1 = new EItem(EItemType.Keyboard, "Tastiera1", 16.50),
                processor4 = new EItem(EItemType.Processor, "Processore4", 9.50),
                processor5 = new EItem(EItemType.Processor, "Processore5", 8.50),
                processor6 = new EItem(EItemType.Processor, "Processore6", 8.50)
            ));

        double orderPrice = bill.getOrderPrice(items, user);

        assertEquals(83.75, orderPrice, 0);
    }

    @Test
    public void testApplyDiscount()
        throws DiscountValueException{
        double price = 30;
        double discount = 0.5;

        double dicountedPrice = BillImpl.applyDiscount(price, discount);

        assertEquals(15, dicountedPrice, 0);
    }
}
