package com.aison.test.design.factory.simpleFactory;

public class EngineerFactory {
    public static JobContent getContent(String type){
        JobContent jobContent = null;
        if("java".equals(type)){
             jobContent = new JavaEngineer();
        }else if("C".equals(type)){
            jobContent = new CEngineer();
        }else {
            jobContent = new WebEngineer();
        }
        return jobContent;
    }
    public static void main(String[] args) {
        JobContent c = EngineerFactory.getContent("C");
        c.enjoy();
        c.study();
        c.work();
    }
}
