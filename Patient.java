/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicare_hospital_system;

/**
 *
 * @author NNA Kopa
 */


public class Patient 
{
    //encapsulated private attributes
    private String patientID;
    private String firstName;
    private String lastName;
    private String gender;
    private String medicalCondition;
    private int age;
    private PatientCategory category;
    
    //Intialising all base feilds when a standard patient is registered also constructing the parameters
    public Patient (String patientID, String firstName,String lastName,String gender,String medicalCondition,int age, PatientCategory category)
    {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.age = age;
        this.category = category;
    }
    
    // Getter method will allow me to controll the read access to private fields
    public String getPatientID() 
    { 
        return patientID; 
    }
    public String getFirstName() 
    { 
        return firstName; 
    }
    public String getLastName() 
    { 
        return lastName; 
    }
    public int getAge() 
    { 
        return age; 
    }
    public String getGender() 
    { 
        return gender; 
    }
    public String getMedicalCondition() 
    { 
        return medicalCondition; 
    }
    public PatientCategory getCategory() 
    { 
        return category; 
    }
 
    // Setters for encapsulation  updating details
    public void setFirstName(String firstName) 
    { 
        this.firstName = firstName; 
    }
    public void setLastName(String lastName) 
    { 
        this.lastName = lastName; 
    }
    public void setAge(int age) 
    { 
        this.age = age; 
    }
    public void setMedicalCondition(String medicalCondition) 
    { 
        this.medicalCondition = medicalCondition; 
    }
    
    // This method can be overridden by other subclasses to add more information
    public void displayDetails() 
    {
        System.out.println("ID: " + patientID + " | Name: " + firstName + " " + lastName + " | Age: " + age + " | Gender: " + gender +  " | Condition: " + medicalCondition + " | Category: " + category);
    }
}
