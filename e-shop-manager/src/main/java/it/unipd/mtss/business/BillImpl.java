////////////////////////////////////////////////////////////////////
// Andrea Volpe 2021904
// Riccardo Contin 1225416
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.business.exception.EItemNotFoundException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;

public class BillImpl implements Bill{
    public double getOrderPrice(List<EItem> itemsOrdered, User user) 
        throws BillException{

        double total = getTotal(itemsOrdered);

        return total;
    }

    public static EItem lessExpensiveEItem(List<EItem> items, 
        EItemType eItemType)
        throws EItemNotFoundException{
        
        Optional<EItem> cheaperItem = items.stream()
            .filter(item -> item.getItemType() == eItemType)
            .min(Comparator.comparing(EItem::getPrice));
            
        if(!cheaperItem.isPresent()){
            throw new EItemNotFoundException();
        } 

        return cheaperItem.get();
    }

    public static double getTotal(List<EItem> items) {
        double total = 0;

        for (EItem item : items) {
            total += item.getPrice();
        }
        
        return total;
    }
}
