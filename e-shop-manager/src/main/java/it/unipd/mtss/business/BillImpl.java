////////////////////////////////////////////////////////////////////
// Andrea Volpe 2021904
// Riccardo Contin 1225416
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;

public class BillImpl implements Bill{
    public double getOrderPrice(List<EItem> itemsOrdered, User user) 
        throws BillException{
        
        double total = getTotal(itemsOrdered);
        double discount = 0;
        
        if(itemsOrdered.size() > 30){
            throw new BillException("Non puoi ordinare piu' di 30 articoli");
        }

        // > 5 processors (the cheaper one is discounted by 50%) 
        if(numberOfEItem(itemsOrdered, EItemType.Processor) > 5){
            double cheaperProcessorPrice = 
                lessExpensiveEItem(itemsOrdered, EItemType.Processor).get()
                .getPrice();
            discount += applyDiscount(cheaperProcessorPrice, 0.5);
        }
        // #mouses == #keyboards (the cheaper keyboard is free)
        if(numberOfEItem(itemsOrdered, EItemType.Mouse) != 0 &&
           (numberOfEItem(itemsOrdered,EItemType.Mouse) ==
            numberOfEItem(itemsOrdered, EItemType.Keyboard))){
                double cheaperKeyboardPrice = 
                    lessExpensiveEItem(itemsOrdered, EItemType.Keyboard).get() 
                    .getPrice();
                discount += cheaperKeyboardPrice;
        }

        // > 10 mouses (the cheaper one is free)
        total = total - discount - moreThan10Mouse(itemsOrdered);

        // total price > 1000 (10% off)
        if (total > 1000) {
            discount = applyDiscount(total, 0.1);
            total -= discount;
        }
        
        return total;
    }

    public static Optional<EItem> lessExpensiveEItem(List<EItem> items, 
        EItemType eItemType){
        
        Optional<EItem> cheaperItem = items.stream()
            .filter(item -> item.getItemType() == eItemType)
            .min(Comparator.comparing(EItem::getPrice));

        return cheaperItem;
    }

    public static double getTotal(List<EItem> items) {
        double total = 0;

        for (EItem item : items) {
            total += item.getPrice();
        }
        
        return total;
    }
    
    public static int numberOfEItem(List<EItem> items, 
        EItemType eItemType){
            return (int)items.stream()
                .filter(item -> item.getItemType() == eItemType)
                .count();
    }

    public static double applyDiscount(double price, double discount){
        
        return price * discount;
    }

    // > 10 mouse (the cheaper one is a gift)
    public double moreThan10Mouse(List<EItem> items) {
        int count = numberOfEItem(items, EItemType.Mouse);

        if (count > 10){
            return lessExpensiveEItem(items, EItemType.Mouse).get().getPrice();
        }
        return 0;
    }
}
