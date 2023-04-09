package com.receipt.api.data.operations;

import com.receipt.api.models.Item;
import com.receipt.api.utils.ReadFileData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartDB {


    @Value("${cart.data.file}")
    private String cartFilePath;


    public List<Item> getItemsFromJSONData() throws IOException {

        final List<Item> items = new ArrayList<>();

        String fileData = ReadFileData.getFileDataAsString(cartFilePath);

        JSONObject obj = new JSONObject(fileData);
        JSONArray jsonArray = obj.getJSONArray("items");

        for (int i = 0; i < jsonArray.length(); ++i) {
            items.add(getItem(jsonArray, i));
        }

        return items;
    }

    private static Item getItem(JSONArray jsonArray, int i) {
        final JSONObject jsonObj = jsonArray.getJSONObject(i);

        Item item = new Item();

        item.setItemName(jsonObj.getString("itemName"));
        item.setSku(jsonObj.getInt("sku"));
        item.setTaxable(jsonObj.getBoolean("isTaxable"));
        item.setOwnBrand(jsonObj.getBoolean("ownBrand"));
        item.setPrice(jsonObj.getBigDecimal("price"));

        return item;
    }


}
