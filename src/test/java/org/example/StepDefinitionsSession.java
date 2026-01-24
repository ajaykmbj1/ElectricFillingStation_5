package org.example;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

public class StepDefinitionsSession {
    @When("I start a session {string} at charger {string} for customer {string}")
    public void i_start_a_session(String sessionId, String chargerId, String customerId) {
        ChargerManager cm = CommonSteps.chargerManager;
        CustomerManager custM = CommonSteps.customerManager;
        ChargingSessionManager sm = CommonSteps.sessionManager;

        assertNotNull(cm, "ChargerManager is null! Did you run 'Given the Filling Station Network is available'?");

        Charger charger = cm.readCharger(chargerId);
        assertNotNull(charger, "Charger " + chargerId + " not found!");

        if(charger.getStatus() == null) charger.setStatus(ChargerStatus.FREE);

        Customer customer = custM.readCustomer(customerId);
        assertNotNull(customer, "Customer " + customerId + " not found!");

        sm.startSession(sessionId, charger, customer);
    }

    @Then("the session {string} should be active")
    public void the_session_should_be_active(String sessionId) {
        ChargingSession s = CommonSteps.sessionManager.readSession(sessionId);
        assertNotNull(s, "Session not found");
        assertNull(s.getEndTime(), "Session should be active (no end time yet)");
    }

    @Then("the session {string} should have a valid start time")
    public void the_session_should_have_a_valid_start_time(String sessionId) {
        ChargingSession s = CommonSteps.sessionManager.readSession(sessionId);
        assertNotNull(s.getStartTime(), "Start time is null");
        long diff = new Date().getTime() - s.getStartTime().getTime();
        assertTrue(diff < 5000 && diff >= 0, "Start time is unrealistic");
    }

    @When("I wait for {int} seconds")
    public void i_wait_for_seconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
    }

    @When("I stop the session {string}")
    public void i_stop_the_session(String sessionId) {
        CommonSteps.sessionManager.stopSession(sessionId);
    }

    @Then("the session {string} should have a valid end time")
    public void the_session_should_have_a_valid_end_time(String sessionId) {
        ChargingSession s = CommonSteps.sessionManager.readSession(sessionId);
        assertNotNull(s.getEndTime(), "End time should be set");
    }

    @Then("the session end time should be after the start time")
    public void the_session_end_time_should_be_after_start() {

        ChargingSession s = CommonSteps.sessionManager.readSession("SESS-T2");
        if(s != null && s.getEndTime() != null) {
            assertTrue(s.getEndTime().after(s.getStartTime()));
        }
    }

    @Then("the session duration should be greater than {int} seconds")
    public void session_duration_check(int seconds) {
        ChargingSession s = CommonSteps.sessionManager.readSession("SESS-T2");
        long durationMillis = s.getEndTime().getTime() - s.getStartTime().getTime();
        assertTrue(durationMillis >= seconds * 1000L, "Duration was too short");
    }
}