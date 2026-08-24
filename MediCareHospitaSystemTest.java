/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.medicare_hospital_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * @author NNA Kopa
 */

public class MediCareHospitaSystemTest 
{ 
    private MediCare_Hospital_System system;

    @BeforeEach
    public void setUp() 
    {
        system = new MediCare_Hospital_System();
    }

    // 1. PATIENT MANAGEMENT & CRUD TESTS
   
    @Test
    public void testRegisterPatient_Success() 
    {
        
        Patient p = new Patient("P001", "John", "Doe", "Male", "Flu", 30, PatientCategory.OUTPATIENT);
        assertTrue(system.addPatient(p), "Patient should be registered successfully.");
        assertEquals(1, system.getPatients().size());
    }

    @Test
    public void testRegisterPatient_DuplicateID_Fails() 
    {
        Patient p1 = new Patient("P001", "John", "Doe", "Male", "Flu", 30, PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P001", "Jane", "Smith", "Female", "Fever", 25, PatientCategory.EMERGENCY);

        system.addPatient(p1);
        assertFalse(system.addPatient(p2), "System should prevent registering a duplicate Patient ID.");
        assertEquals(1, system.getPatients().size());
    }

    @Test
    public void testSearchPatient_FoundAndNotFound() 
    {
        Patient p = new Patient("P002", "Alice", "Brown", "Female", "Fracture", 45, PatientCategory.OUTPATIENT);
        system.addPatient(p);

        assertNotNull(system.searchPatient("P002"), "Patient should be found by ID.");
        assertNull(system.searchPatient("P999"), "Non-existent patient should return null.");
    }

    @Test
    public void testDeletePatient_RemovesRecord() 
    {
        Patient p = new Patient("P003", "Bob", "Green", "Male", "Checkup", 50, PatientCategory.OUTPATIENT);
        system.addPatient(p);

        assertTrue(system.deletePatient("P003"), "Patient deletion should return true.");
        assertNull(system.searchPatient("P003"), "Deleted patient should no longer exist in memory.");
    }
    
    // 2. BED MANAGEMENT & RESTRICTION TESTS

    @Test
    public void testAllocateBed_Inpatient_Success() 
    {
        // Inpatient constructor order: ID, First, Last, Age, Gender, Condition, Ward, Bed
        Inpatient inp = new Inpatient("P004", "Charlie", "Davis", 35, "Male", "Surgery", "General Ward", "Unassigned");
        system.addPatient(inp);

        assertTrue(system.allocateBed("P004", "B01"), "Bed allocation to an Inpatient should succeed.");
        assertEquals("B01", inp.getBedNumber());
    }

    @Test
    public void testAllocateBed_Outpatient_Fails() 
    {
        Patient out = new Patient("P005", "David", "Miller", "Male", "Routine", 28, PatientCategory.OUTPATIENT);
        system.addPatient(out);

        assertFalse(system.allocateBed("P005", "B02"), "Bed allocation to an Outpatient must fail.");
    }

    @Test
    public void testAllocateBed_AlreadyOccupiedBed_Fails() 
    {
        Inpatient inp1 = new Inpatient("P006", "Eva", "Wilson", 40, "Female", "Recovery", "General Ward", "Unassigned");
        Inpatient inp2 = new Inpatient("P007", "Frank", "Taylor", 60, "Male", "Observation", "General Ward", "Unassigned");
        
        system.addPatient(inp1);
        system.addPatient(inp2);

        system.allocateBed("P006", "B03");
        assertFalse(system.allocateBed("P007", "B03"), "Allocating an already occupied bed should fail.");
    }

    @Test
    public void testReleaseBed_Success() 
    {
        Inpatient inp = new Inpatient("P008", "Grace", "Anderson", 22, "Female", "Appendicitis", "General Ward", "Unassigned");
        system.addPatient(inp);
        system.allocateBed("P008", "B04");

        assertTrue(system.releaseBedByCode("B04"), "Releasing an occupied bed should return true.");
    }

    // 3. SORTING & DELETION WITH BED RELEASE

    @Test
    public void testDeleteInpatient_FreesAllocatedBed() 
    {
        Inpatient inp = new Inpatient("P009", "Henry", "Thomas", 55, "Male", "Pneumonia", "General Ward", "Unassigned");
        system.addPatient(inp);
        system.allocateBed("P009", "B05");

        system.deletePatient("P009");
        assertFalse(system.releaseBedByCode("B05"), "Bed should have been automatically released upon patient deletion.");
    }

    @Test
    public void testGetPatientsSortedBySurname() 
    {
        Patient p1 = new Patient("P010", "Zack", "Williams", "Male", "Flu", 30, PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P011", "Adam", "Adams", "Male", "Fever", 25, PatientCategory.OUTPATIENT);
        
        system.addPatient(p1);
        system.addPatient(p2);

        List<Patient> sorted = system.getPatientsSortedBySurname();
        assertEquals("Adams", sorted.get(0).getLastName(), "First element should be sorted alphabetically by surname.");
        assertEquals("Williams", sorted.get(1).getLastName());
    }
}