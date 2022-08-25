package com.aison.test.design.factory.factoryMethod;

public class WebEngineerFactory {
    public JobContent getEngineer(){
        return new WebEngineer();
    }
}
