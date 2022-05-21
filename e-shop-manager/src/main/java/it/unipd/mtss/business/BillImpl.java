////////////////////////////////////////////////////////////////////
// Andrea Volpe 2021904
// Riccardo Contin 1225416
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import it.unipd.mtss.business.exeption.BillException;
import it.unipd.mtss.business.exeption.EItemNotFoundException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;

public class BillImpl implements Bill{
    public double getOrderPrice(List<EItem> itemsOrdered, User user) 
        throws BillException{
        return 0;
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

    public static int numberOfEItem(List<EItem> items, 
        EItemType eItemType){
            return (int)items.stream()
                .filter(item -> item.getItemType() == eItemType)
                .count();
    }
}
