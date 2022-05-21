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
import it.unipd.mtss.business.exeption.DiscountValueException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.EItemType;
import it.unipd.mtss.model.User;

public class BillImpl implements Bill{
    public double getOrderPrice(List<EItem> itemsOrdered, User user) 
        throws BillException{
        
        double total = getTotal(itemsOrdered);
        double discount = 0;
        
        // > 5 processors (the cheaper one is discounted by 50%)
        if(numberOfEItem(itemsOrdered, EItemType.Processor) > 5){
            try{
                double cheaperProcessorPrice = 
                    lessExpensiveEItem(itemsOrdered, EItemType.Processor)
                    .getPrice();
                discount += applyDiscount(cheaperProcessorPrice, 0.5);
            }catch(EItemNotFoundException e){
                // unreachable block
            }catch(DiscountValueException e){
                // unreachable block
            }
        }

        return total - discount;
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

    public static int numberOfEItem(List<EItem> items, 
        EItemType eItemType){
            return (int)items.stream()
                .filter(item -> item.getItemType() == eItemType)
                .count();
    }

    public static double applyDiscount(double price, double discount)
        throws DiscountValueException{
        if(discount < 0){
            throw new DiscountValueException();
        }
        
        return price * discount;
    }
}
