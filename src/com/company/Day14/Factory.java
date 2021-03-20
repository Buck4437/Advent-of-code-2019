package com.company.Day14;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Factory {

    private Hashtable<String, Long> inventory = new Hashtable<>();

    Hashtable<String, Recipe> recipes = new Hashtable<>();

    public Factory(ArrayList<String> recipes) {
        for (String recipe : recipes) {
            Recipe recipe2 = new Recipe(recipe);
            this.recipes.put(recipe2.getName(), recipe2);
        }
    }

    public void reset() {
        inventory = new Hashtable<>();
    }

    public long getItem(String item) {
        if (inventory.containsKey(item)) {
            return inventory.get(item);
        }
        return 0;
    }

    public long addItem(String item, long num) {
        inventory.put(item, getItem(item) + num);
        return getItem(item);
    }

    public void craftItem(String item, long amount) {
       Recipe recipe = recipes.get(item);
       long ratio = (long) Math.ceil((float) amount / recipe.getProduct().get(item));
       long productAmt = recipe.getProduct().get(item) * ratio;

//       System.out.printf("\nCrafting %s with ratio of %s\n", item, ratio);

       Enumeration<String> reactants = recipe.getReactants().keys();
       while (reactants.hasMoreElements()) {
           String name = reactants.nextElement();
           long reactantAmt = recipe.getReactants().get(name) * ratio;

//           System.out.printf("Checking if you have enough %s...\n", name);
//           System.out.printf("You have %s %s, you need %s %s \n", getItem(name), name, reactantAmt, name);

           if (getItem(name) < reactantAmt) {
               craftItem(name, reactantAmt - getItem(name));
           }

           addItem(name, -reactantAmt);
       }

       addItem(item, productAmt);
//       System.out.println("Your inventory: " +  inventory.toString() + "\n");
    }
}
