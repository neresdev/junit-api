package br.com.junitexample.resource.exceptions;

import br.com.junitexample.service.exceptions.DataIntegratyViolationException;
import br.com.junitexample.service.exceptions.ObjectNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ResourceExceptionHandlerTest {

    public static final String OBJECT_NOT_FOUND_MESSAGE = "Objeto não encontrado";
    public static final String EMAIL_ALREADY_REGISTERED_MESSAGE = "E-mail já cadastrado";
    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .objectNotFound(new ObjectNotFoundException(OBJECT_NOT_FOUND_MESSAGE), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(OBJECT_NOT_FOUND_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());

    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandardError> response = exceptionHandler
                .dataIntegrityViolationException(new DataIntegratyViolationException(EMAIL_ALREADY_REGISTERED_MESSAGE), new MockHttpServletRequest());



        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(EMAIL_ALREADY_REGISTERED_MESSAGE, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertNotEquals("/user/2", response.getBody().getPath());
        assertNotEquals(LocalDateTime.now() , response.getBody().getTimestamp());
    }
}