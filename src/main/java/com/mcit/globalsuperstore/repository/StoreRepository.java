package com.mcit.globalsuperstore.repository;

import com.mcit.globalsuperstore.Item;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class StoreRepository {
    private List<Item> items = new ArrayList<>();
    public Item getItem(int index){
        return items.get(index);
    }
    public List<Item> getAllItems(){
        return items;
    }
    public void addItem(Item item){
        items.add(item);
    }
    public void updateItem(Item item, int index){
        items.set(index, item);
    }
}
