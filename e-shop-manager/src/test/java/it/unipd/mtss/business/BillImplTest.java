package it.unipd.mtss.business;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;

public class BillImplTest {

    @Test
    public void testLessExpensiveEItem() {
        BillImpl bill = new BillImpl();
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
}
