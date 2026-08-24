/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.medicare_hospital_system;


/**
 * 
 * @author Nkahosheko Nhlamulo Akane Kopa
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;

public class MediCare_Hospital_System 
{
    // This is the main controller class 
    // This handles the system initialisation, the console menu navigation, business logic rules 
    
    // In memory collection to store registered patients dynamically
    private List<Patient> patients = new ArrayList<>();
    
    // This part is mainly for the 2D arrays that represent a 4*5 ward
    private String[][] wardBeds = new String [4][5];
    Scanner scanner = new Scanner (System.in);
    
    // This initialses the bed matirx automatically
    public MediCare_Hospital_System()
    {
        initalisedBeds();
    }
    
    public static void main(String[] args) 
    {
        MediCare_Hospital_System system = new MediCare_Hospital_System();
        system.runMenu();
    }
    
    // This populates the 4*5 2D grid with the default codes
    
    private void initalisedBeds()
    {
        int count = 1;
        for (int row = 0; row < 4; row++)
        {
            for (int column = 0; column < 5; column++)
            {
                wardBeds[row][column] = String.format("B%02d", count++);
            }
        }
    }
    
    // This menu displayes the console menu loop and processes option selection with exception handling 
    public void runMenu()
    {
        boolean exit = false;
        while (!exit)
        {
            System.out.println("MEDICARE HOSPITAL ADMITTANCE SYSTEM");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient Details");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients (Sorted)");
            System.out.println("6. Allocate Hospital Bed (Inpatients Only)");
            System.out.println("7. Release Hospital Bed");
            System.out.println("8. View Ward Layout & Occupancy Report");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");
            
            try 
            {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) 
                {
                    case 1 : registerPatientFromConsole();
                    case 2 : searchPatientFromConsole();
                    case 3 : updatePatientFromConsole();
                    case 4 : deletePatientFromConsole();
                    case 5 : displaySortedPatients();
                    case 6 : allocateBedFromConsole();
                    case 7 : releaseBedFromConsole();
                    case 8 : displayWardReport();
                    case 9 : 
                    {
                        exit = true;
                        System.out.println("Exiting system. Goodbye!");
                    }
                    
                    default : System.out.println("Invalid choice. Please enter 1-9.");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Error: Please enter a valid numerical menu option.");
            }
        }    
    }
    
    // This method adds a new patient record while enforcing unique ID restrictions
    public boolean addPatient (Patient patient)
    {
        for (Patient p: patients)
        {
            if (p.getPatientID().equalsIgnoreCase(patient.getPatientID()))
            {
                //This will then prevent duplicate IDs
                return false;
            }
        }
        patients.add(patient);
        return true;
    }
    
    // Searches for a patient ID 
    public Patient searchPatient (String patientID)
    {
        for (Patient p : patients)
        {
            if (p.getPatientID().equalsIgnoreCase(patientID))
            {
                return p;
            }
        }
        return null;
    }
    
    // Deletes a patient and automatically and releases their allocated bed if they are an inpatient
    public boolean deletePatient (String patientID)
    {
        Patient p = searchPatient(patientID);
        if (p != null)
        {
            //This automatically releases the bed if an inpatient is deleted with a bed assigned
            if ( p instanceof Inpatient ip && !ip.getBedNumber().equals("Unassigned."))
            {
                releaseBedByCode (ip.getBedNumber());
            }
            patients.remove(p);
            return true;
        }
        return false;
    }
    
    // Enforces the business rules
    public boolean allocateBed (String patientID, String bedCode)
    {
        Patient patient = searchPatient(patientID);
        if (patient == null)
        {
            System.out.println("Error: Patient ID not found.");
            return false;
        }
        
        // Only inpatients are allowed which is the validation rule
        if (!(patient instanceof Inpatient inpatient))
        {
            System.out.println("Error: Only INPATIENTS can be allocated a bed.");
            return false;
        }
        
        // Search 2D grid for available bed
        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 5; c++)
            {
               if (wardBeds[r][c].equalsIgnoreCase(bedCode))
               {
                   wardBeds[r][c] = bedCode + " [OCCUPIED: " + patientID + "]";
                    inpatient.setBedNumber(bedCode);
                    return true;
               }
            }
        }
        
       System.out.println ("Error: Bed code not found or already occupied.");
        return false; 
    }
    
    // This releases an occupied bed in the 2D array and resets it to its default label
    public boolean releaseBedByCode(String bedCode)
    {
        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 5; c++)
            {
                if (wardBeds[r][c].toUpperCase().contains(bedCode.toUpperCase()) && wardBeds[r][c].contains("OCCUPIED"))
                {
                    wardBeds[r][c] = String.format("B%02d", (r * 5) + c + 1);
                    return true;
                }
            }
        }
        return false;
    }
    
    // Returns a new list containing patients sorted alphabetically by the last name
    public List <Patient> getPatientsSortedBySurname()
    {
        List<Patient> sortedList = new ArrayList<>(patients);
        sortedList.sort(Comparator.comparing(Patient::getLastName));
        return sortedList;
    }
    
    // this is the interaction helper method 
    private void registerPatientFromConsole()
    {
        System.out.print("Enter Patient ID: ");
        String ID = scanner.nextLine();
        
        if (searchPatient(ID) != null)
        {
            System.out.println("Error: A patient with this ID already exists.");
            return;
        }
        
        System.out.print("First Name: "); 
        String FN = scanner.nextLine();
        
        System.out.print("Last Name: "); 
        String LN = scanner.nextLine();
        
        System.out.print("Age: "); 
        int age = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Gender: "); 
        String gender = scanner.nextLine();
        
        System.out.print("Medical Condition: "); 
        String condition = scanner.nextLine();
        
        System.out.println("Select Patient Category: 1. INPATIENT  2. OUTPATIENT  3. EMERGENCY");
        int catChoice = Integer.parseInt(scanner.nextLine());
        
        Patient newPatient;
        if (catChoice == 1)
        {
            newPatient = new Inpatient(ID,FN,LN, age, gender, condition, "General Ward", "Unassigned");
        } 
        else if (catChoice == 2) 
        {
            newPatient = new Patient(ID, FN, LN, gender, condition,age, PatientCategory.OUTPATIENT);
        } 
        else 
        {
            newPatient = new Patient(ID, FN, LN, gender, condition,age, PatientCategory.EMERGENCY);
        }
        
        if (addPatient(newPatient))
        {
            System.out.println("Patient registered successfully!");
        }
    }
    
    private void updatePatientFromConsole()
    {
        System.out.print("Enter Patient ID to update: ");
        Patient p = searchPatient(scanner.nextLine());
        if (p != null) 
        {
            System.out.print("New First Name: "); 
            p.setFirstName(scanner.nextLine());
            
            System.out.print("New Last Name: "); 
            p.setLastName(scanner.nextLine());
            
            System.out.print("New Age: "); 
            p.setAge(Integer.parseInt(scanner.nextLine()));
            
            System.out.print("New Condition: "); 
            p.setMedicalCondition(scanner.nextLine());
            
            System.out.println("Patient updated successfully.");
        } 
        else 
        {
            System.out.println("Patient not found.");
        }
    }
    
    private void searchPatientFromConsole()
    {
        System.out.print("Enter Patient ID: ");
        Patient p = searchPatient(scanner.nextLine());
        
        if (p != null)
        {
            p.displayDetails();
        }
        else
        {
            System.out.println("Patient record not found.");
        }
    }
    
    private void deletePatientFromConsole()
    {
        System.out.print("Enter Patient ID to delete: ");
        
        if (deletePatient(scanner.nextLine())) 
        {
            System.out.println("Patient record deleted.");
        } 
        else 
        {
            System.out.println("Patient not found.");
        }
    }
    
    private void displaySortedPatients()
    {
        List<Patient> sorted = getPatientsSortedBySurname();
        if (sorted.isEmpty()) 
        {
            System.out.println("No registered patients found.");
            return;
        }
        System.out.println("\n Registered Patients (Sorted by Surname)");
        
        for (Patient p : sorted) 
        {
            p.displayDetails();
        }
    }
    
    private void allocateBedFromConsole()
    {
        System.out.print("Enter Inpatient ID: ");
        String ID = scanner.nextLine();
        
        System.out.print("Enter Bed Number to allocate (e.g., B01, B05): ");
        String bedCode = scanner.nextLine();

        if (allocateBed(ID, bedCode)) 
        {
            System.out.println("Bed " + bedCode + " successfully allocated to Patient " + ID);
        }
    }
    
    private void releaseBedFromConsole()
    {
        System.out.print("Enter Bed Number to release (e.g., B01): ");
        String bedCode = scanner.nextLine();
        
        if (releaseBedByCode(bedCode)) 
        {
            System.out.println("Bed " + bedCode + " released.");
        } 
        else 
        {
            System.out.println("Bed not found or was not occupied.");
        }
    }
    
    private void displayWardReport()
    {
        int occupied = 0;
        System.out.println("\n Ward Bed Layout (4x5)");
        for (int r = 0; r < 4; r++) 
        {
            for (int c = 0; c < 5; c++) 
            {
                System.out.print("[" + wardBeds[r][c] + "]\t");
                if (wardBeds[r][c].contains("OCCUPIED")) occupied++;
            }
            System.out.println();
        }

        double percentage = (occupied / 20.0) * 100;
        System.out.println("\n Occupancy Report");
        System.out.println("Total Registered Patients: " + patients.size());
        System.out.println("Occupied Beds: " + occupied + " / 20");
        System.out.println("Available Beds: " + (20 - occupied));
        System.out.printf("Occupancy Rate: %.2f%%\n", percentage);
    }
    
    public List<Patient> getPatients() 
    {
        return patients; 
    }
}
