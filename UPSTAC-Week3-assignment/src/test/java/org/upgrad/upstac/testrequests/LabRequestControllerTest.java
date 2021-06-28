package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.LabRequestController;
import org.upgrad.upstac.testrequests.lab.TestStatus;
import org.upgrad.upstac.users.models.Gender;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class LabRequestControllerTest {


    @Autowired
    LabRequestController labRequestController;




    @Autowired
    TestRequestQueryService testRequestQueryService;


    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_update_the_request_status(){


        TestRequest testRequest = getTestRequestByStatus(RequestStatus.INITIATED);

        TestRequest result = labRequestController.assignForLabTest(testRequest.getRequestId());

        assertEquals(result.getStatus(), RequestStatus.LAB_TEST_IN_PROGRESS);
        assertEquals(result.getRequestId(), testRequest.getRequestId());
        assertNotNull(result.getLabResult());
        //Implement this method
        //Create another object of the TestRequest method and explicitly assign this object for Lab Test using assignForLabTest() method
        // from labRequestController class. Pass the request id of testRequest object.

        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'LAB_TEST_IN_PROGRESS'
        // make use of assertNotNull() method to make sure that the lab result of second object is not null
        // use getLabResult() method to get the lab result


    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().get();
    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_invalid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            labRequestController.assignForLabTest(InvalidRequestId);
        });

        assertThat(exception.getMessage(), containsString("Invalid ID"));

        //Implement this method
        // Create an object of ResponseStatusException . Use assertThrows() method and pass assignForLabTest() method
        // of labRequestController with InvalidRequestId as Id

        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_valid_test_request_id_should_update_the_request_status_and_update_test_request_details(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        CreateLabResult createLabResult = getCreateLabResult(testRequest);

        TestRequest result = labRequestController.updateLabTest(testRequest.getRequestId(),createLabResult);

        assertEquals(result.getRequestId(),testRequest.getRequestId());
        assertEquals(result.getStatus(),RequestStatus.LAB_TEST_COMPLETED);
        assertEquals(result.getLabResult(),testRequest.getLabResult());

        //Implement this method
        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        //Create another object of the TestRequest method and explicitly update the status of this object
        // to be 'LAB_TEST_IN_PROGRESS'. Make use of updateLabTest() method from labRequestController class (Pass the previously created two objects as parameters)

        //Use assertThat() methods to perform the following three comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'LAB_TEST_COMPLETED'
        // 3. the results of both the objects created should be same. Make use of getLabResult() method to get the results.
    }


    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_test_request_id_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);


        Long InvalidRequestId= -30L;

        CreateLabResult createLabResult = getCreateLabResult(testRequest);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            labRequestController.updateLabTest(InvalidRequestId,createLabResult);
        });

        assertThat(exception.getMessage(), containsString("Invalid ID"));

        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with a negative long value as Id and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

    }


    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_empty_status_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        CreateLabResult createLabResult = getCreateLabResult(testRequest);
        //Setting result status to null(invalid) so that app exception ConstraintViolationException will be thrown
        createLabResult.setResult(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            labRequestController.updateLabTest(testRequest.getRequestId(),createLabResult);
        });

        assertThat(exception.getMessage(), containsString("ConstraintViolationException"));

        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter
        // Set the result of the above created object to null.

        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with request Id of the testRequest object and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "ConstraintViolationException"
    }

    public CreateLabResult getCreateLabResult(TestRequest testRequest) {

        //Create an object of CreateLabResult and set all the values
        // Return the object
        CreateLabResult createLabResult = new CreateLabResult();
        createLabResult.setBloodPressure("102");
        createLabResult.setHeartBeat("97");
        createLabResult.setTemperature("98");
        createLabResult.setOxygenLevel("95");
        createLabResult.setComments("Should be left");
        createLabResult.setResult(TestStatus.NEGATIVE);
        return createLabResult;   // Replace this line with your code
    }

}