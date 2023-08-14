package com.mcit.globalsuperstore.service;

import com.mcit.globalsuperstore.Constants;
import com.mcit.globalsuperstore.Item;
import com.mcit.globalsuperstore.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Service
public class StoreService {
    StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Item getItem(int index){
        return storeRepository.getItem(index);
    }
    public List<Item> getAllItems(){
        return storeRepository.getAllItems();
    }
    public void addItem(Item item){
        storeRepository.addItem(item);
    }
    public void updateItem(Item item, int index){
        storeRepository.updateItem(item, index);
    }
    public int getIndexOfId(String id){
        for(int i = 0; i<getAllItems().size(); i++){
            if(getItem(i).getId().equals(id))
                return i;
        }
        return Constants.NOT_FOUND;
    }
    public boolean withinFiveDays(Date newDate, Date oldDate){
        long diff = Math.abs(newDate.getTime() - oldDate.getTime());
        return (int)(TimeUnit.MILLISECONDS.toDays(diff)) <= 5;
    }
    public Item getItemById(String id){
        int index = getIndexOfId(id);
        return index == Constants.NOT_FOUND ? new Item() : getItem(index);
    }
    public String submitItem(Item item){
        int index = getIndexOfId(item.getId());
        String status = Constants.SUCCESS_STATUS;
        if (index == Constants.NOT_FOUND){
            if (item.getPrice() > item.getDiscount())
                addItem(item);
            else
                status = Constants.FAILED_STATUS;
        }
        else if(withinFiveDays(item.getDate(),getItem(index).getDate())) {
            if (item.getPrice() > item.getDiscount())
                updateItem(item, index);
            else
                status = Constants.FAILED_STATUS;
        }
        return status;
    }
}
