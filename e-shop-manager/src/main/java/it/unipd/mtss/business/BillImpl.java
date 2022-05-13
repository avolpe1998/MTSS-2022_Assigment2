package it.unipd.mtss.business;

import java.util.List;

import it.unipd.mtss.business.exeption.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.User;

public class BillImpl implements Bill{
    public double getOrderPrice(List<EItem> itemsOrdered, User user) throws BillException{
        return 0;
    }
}
