package com.cabinvoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
/*In InvoiceGeneratorTest check a given Distance And Time The Invoice Generator should Return
 * The Total Fare Fore Journey.
 * AssertEquals Check Whether Actual And Expected Value Is Equal Or Not
 * If both are equal Test Case Pass.
 * Otherwise Testcase Fail.*/

public class InvoiceServiceTest {
    InvoiceGenerator invoiceGenerator = null;
    /**
     * @BeforeEach is used to signal that the annotated method should be executed before each @Test method in the current test class.
     */
    @BeforeEach
    void setUp() {
        invoiceGenerator = new InvoiceGenerator();
    }

    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare() {
        double distance = 2.0;
        int time = 5;
        double fare = invoiceGenerator.calculateFare(distance, time);
        Assertions.assertEquals(25, fare, 0.0);
    }

    //Test Case For Minimum Fare.
    @Test
    public void givenLessDistanceAndTime_ShouldReturnMinimumFare() {
        double distance = 0.1;
        int time = 1;
        double fare = invoiceGenerator.calculateFare(distance, time);
        Assertions.assertEquals(5, fare, 0.0);
    }

    /*
     * @Description-Checking for given Multiple Ride Should Return The Total Fare
     * Here that check for number of rides Array is created for that.
     */
    @Test
    public void givenMultipleRides_ShouldReturnInvoiceSummary() {
        Ride[] rides = {new Ride(2.0, 5),
                new Ride(0.1, 1)};
        InvoiceSummary summary = invoiceGenerator.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenRideRepository_whenGiveUserId_ShouldReturnInvoice() {
        HashMap<Integer, Ride[]> RideRepository = new HashMap<>();
        Ride[] rides1 = {new Ride(2.0, 5),
                new Ride(0.1, 1)};
        Ride[] rides2 = {new Ride(10.0, 2),
                new Ride(5.0, 1)};
        Ride[] rides3 = {new Ride(7.0, 3),
                new Ride(5.0, 1)};
        Ride[] rides4 = {new Ride(4.0, 3),
                new Ride(5.0, 4)};
        RideRepository.put(1, rides1);
        RideRepository.put(2, rides1);
        RideRepository.put(3, rides1);
        RideRepository.put(4, rides1);
        int userId = 1;
        RideRepository rideRepository = new RideRepository(RideRepository, userId);
        InvoiceSummary summary = rideRepository.calculateFare();
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenMultipleRides_whenGiveWithCategories_ShouldReturnInvoiceAccordingToType() {
        Ride[] rides = {new Ride(2.0, 5),
                new Ride(5.0, 8)};
        String category = "Premium";
        if (category == "Premium") {
            PremiumInvoice premiumInvoice = new PremiumInvoice();
            InvoiceSummary summary = premiumInvoice.calculateFare(rides);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 131.0);
            Assertions.assertEquals(expectedInvoiceSummary, summary);
        } else {
            InvoiceSummary summary = invoiceGenerator.calculateFare(rides);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
            Assertions.assertEquals(expectedInvoiceSummary, summary);
        }
    }
}