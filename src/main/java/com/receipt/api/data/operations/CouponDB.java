package com.receipt.api.data.operations;

import com.receipt.api.models.Coupon;
import com.receipt.api.models.Item;
import com.receipt.api.utils.ReadFileData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CouponDB {


    @Value("${coupons.data.file}")
    private String couponsFilePath;


    public List<Coupon> getCouponsFromJSONData() throws IOException {

        final List<Coupon> coupons = new ArrayList<>();

        String fileData = ReadFileData.getFileDataAsString(couponsFilePath);

        JSONObject obj = new JSONObject(fileData);
        JSONArray jsonArray = obj.getJSONArray("coupons");

        for (int i = 0; i < jsonArray.length(); ++i) {
            coupons.add(getCoupon(jsonArray, i));
        }

        return coupons;
    }

    private static Coupon getCoupon(JSONArray jsonArray, int i) {
        final JSONObject jsonObj = jsonArray.getJSONObject(i);

        Coupon coupon = new Coupon();
        coupon.setCouponName(jsonObj.getString("couponName"));
        coupon.setAppliedSku(jsonObj.getInt("appliedSku"));
        coupon.setDiscountPrice(jsonObj.getBigDecimal("discountPrice"));

        return coupon;
    }


}
