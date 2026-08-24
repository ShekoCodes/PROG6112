/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicare_hospital_system;

/**
 *
 * @author NNA Kopa
 */



public class Inpatient extends Patient 
{
    //Encapsulation
    private String wardNumber;
    private String bedNumber;

    public Inpatient(String patientID, String firstName, String lastName, int age, String gender, String medicalCondition, String wardNumber, String bedNumber) 
    {
       // Constructor chaining using super
       // This invokes the parent constructor with specific order
        super(patientID, firstName, lastName, gender, medicalCondition,age, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    // The getters and etters for the bed management 
    public String getWardNumber() 
    { 
        return wardNumber; 
    }
    public String getBedNumber() 
    { 
        return bedNumber; 
    }

    public void setWardNumber(String wardNumber) 
    { 
        this.wardNumber = wardNumber; 
    }
    public void setBedNumber(String bedNumber) 
    {
        this.bedNumber = bedNumber; 
    }

    // Overriding displayDetails to include subclass properties
    @Override
    public void displayDetails() 
    {
        super.displayDetails();
        System.out.println(" Ward: " + wardNumber + " | Bed: " + bedNumber);
    }
}