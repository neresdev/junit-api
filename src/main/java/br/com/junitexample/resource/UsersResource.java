package br.com.junitexample.resource;

import br.com.junitexample.domain.DTO.UsersDTO;
import br.com.junitexample.domain.Users;
import br.com.junitexample.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UsersResource {

    public static final String ID = "/{id}";

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsersService usersService;

    @GetMapping(value = ID)
    public ResponseEntity<UsersDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(usersService.findById(id), UsersDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> findAll(){
        return ResponseEntity.ok()
            .body(usersService.findAll().stream().map(u -> mapper.map(u, UsersDTO.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UsersDTO> create(@RequestBody UsersDTO obj){
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path(ID)
                .buildAndExpand(usersService.create(obj).getId()).toUri()).build();
    }

    @PutMapping(value = ID)
    public ResponseEntity<UsersDTO> update(@PathVariable Integer id, @RequestBody UsersDTO obj){
        obj.setId(id);
        return ResponseEntity.ok().body(mapper.map(usersService.update(obj), UsersDTO.class));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UsersDTO> delete(@PathVariable Integer id){
        usersService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
