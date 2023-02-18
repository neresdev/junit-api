package br.com.junitexample.resource;

import br.com.junitexample.domain.DTO.UsersDTO;
import br.com.junitexample.domain.Users;
import br.com.junitexample.repositories.UsersRepository;
import br.com.junitexample.service.impl.UserServiceImpl;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class UsersResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Deivao";
    public static final String EMAIL = "davidneres4554@gmail.com";
    public static final String PASSWORD = "123";

    public static final String NOT_FOUND_MESSAGE = "Objeto naoo encontrado";
    public static final String DATA_INTEGRATY_VIOLATION_EXCEPTION_MESSAGE = "Objeto nao encontrado";
    public static final String EMAIL_ALREADY_REGISTRED_MESSAGE = "E-mail já cadastrado no sistema";
    public static final int INDEX = 0;


    @Mock // instancia "fake"
    private UsersRepository repository;

    @Mock
    private ModelMapper mapper;

    private Users user = new Users();

    private UsersDTO userDTO = new UsersDTO();


    @InjectMocks
    private UsersResource resource;

    @Mock
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(),any())).thenReturn(userDTO);

        ResponseEntity<UsersDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsersDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
        assertEquals(NAME, response.getBody().getName());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        when(service.findAll()).thenReturn(Collections.singletonList(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UsersDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UsersDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(user);

        ResponseEntity<UsersDTO> response = resource.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(service.update(userDTO)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UsersDTO> response = resource.update(ID, userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsersDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete(anyInt());

        ResponseEntity<UsersDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        verify(service, times(1)).delete(anyInt());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private void startUser(){
        user = new Users(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UsersDTO(ID, NAME, EMAIL, PASSWORD);
    }
}