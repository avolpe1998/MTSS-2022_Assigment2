////////////////////////////////////////////////////////////////////
// Andrea Volpe 2021904
// Riccardo Contin 1225416
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EItemTest {

    @Test
    public void testGetItemType(){
        EItem item = new EItem(EItemType.Processor, "Processore1", 150.50);

        EItemType itemType = item.getItemType();

        assertEquals(EItemType.Processor, itemType);
    }

    @Test
    public void testGetName(){
        EItem item = new EItem(EItemType.Processor, "Processore1", 150.50);

        String name = item.getName();

        assertEquals("Processore1", name);
    }

    @Test
    public void testGetPrice(){
        EItem item = new EItem(EItemType.Processor, "Processore1", 150.50);

        double price = item.getPrice();

        assertEquals(150.50, price, 0);
    }
}
