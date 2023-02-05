package br.com.junitexample.resource;

import br.com.junitexample.domain.DTO.UsersDTO;
import br.com.junitexample.domain.Users;
import br.com.junitexample.repositories.UsersRepository;
import br.com.junitexample.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    private Users user;
    private UsersDTO userDTO;


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
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser(){
        user = new Users(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UsersDTO(ID, NAME, EMAIL, PASSWORD);
    }
}