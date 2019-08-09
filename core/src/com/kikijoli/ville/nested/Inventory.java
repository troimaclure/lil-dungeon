/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.nested;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author troïmaclure
 */
public class Inventory {

    public HashMap<String, Integer> products = new HashMap<String, Integer>();
    Entite entite;

    public Inventory(Entite entite) {
        this.entite = entite;
    }

    public void add(String productType, int qte) {
//        MessageManager.addIndicator(entite.getX() + entite.getWidth() / 4, (entite.getY() + entite.getHeight()), "+" + qte, TextureUtil.getTexture("sprite/" + productType + ".png"));
        if (!products.containsKey(productType)) {
            products.put(productType, 0);
        }
        products.replace(productType, products.get(productType) + qte);
    }

    public int remove(String productType, int qte) {
//        MessageManager.addIndicator(entite.getX() + entite.getWidth() / 4, (entite.getY() + entite.getHeight()), "-" + qte, TextureUtil.getTexture("sprite/" + productType + ".png"));
        if (products.getOrDefault(productType, 0) - qte >= 0) {
            products.replace(productType, products.get(productType) - qte);
            return qte;
        }
        int value = products.get(productType);
        products.replace(productType, 0);
        return value;
    }

    public ArrayList<String> getTypes(boolean notEmpty) {
        ArrayList<String> types = new ArrayList<>();
        products.keySet().stream().filter((string) -> (!notEmpty || products.get(string) > 0)).forEach((string) -> {
            types.add(string);
        });
        return types;
    }

    public void give(Inventory inventory, String type, int qte) {
        qte = this.remove(type, qte);
        inventory.add(type, qte);
    }

    public boolean isFull(int maxWeight) {
        return products.values().stream().mapToInt(Integer::intValue).sum() >= maxWeight;
    }

    public boolean isEmpty() {
        return products.values().stream().allMatch((p) -> p == 0);
    }

    public void addAll(Inventory inventory) {
        for (String string : inventory.products.keySet()) {
            products.put(string, products.getOrDefault(string, 0) + inventory.products.get(string));
        }
    }

    public void clear() {
        for (String string : products.keySet()) {
            products.replace(string, 0);
        }
    }
}
