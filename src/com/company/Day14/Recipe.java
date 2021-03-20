package com.company.Day14;

import java.util.Hashtable;

public class Recipe {

    private final Hashtable<String, Integer> reactants = new Hashtable<>();
    private final Hashtable<String, Integer> product = new Hashtable<>();
    private final String name;

    public Recipe(String recipe) {
        String[] reactants = recipe.split(" => ")[0].split(", ");
        for (String reactant : reactants) {
            this.reactants.put(reactant.split(" ")[1], Integer.parseInt(reactant.split(" ")[0]));
        }
        String products = recipe.split(" => ")[1];
        product.put(products.split(" ")[1], Integer.parseInt(products.split(" ")[0]));
        name = products.split(" ")[1];
    }

    public Hashtable<String, Integer> getReactants() {
        return reactants;
    }

    public Hashtable<String, Integer> getProduct() {
        return product;
    }

    public String getName() {
        return name;
    }
}
