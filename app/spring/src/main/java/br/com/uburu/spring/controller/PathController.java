package br.com.uburu.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uburu.spring.document.Path;
import br.com.uburu.spring.service.PathService;

@RestController
@RequestMapping("/api/v1/path")
public class PathController {

    @Autowired
    private PathService service;

    @GetMapping
    public ResponseEntity<List<Path>> getAll() {
        List<Path> paths = service.getAll();
        return new ResponseEntity<List<Path>>(paths, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Path> getById(@PathVariable("id") long id) {
        Path path = service.findById(id);
        return new ResponseEntity<Path>(path, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Path> include(@RequestBody Path path) {
        if (path.getId() == 0) {
            service.save(path);
            return new ResponseEntity<Path>(path, HttpStatus.CREATED);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Path> update(
        @RequestBody Path path,
        @PathVariable("id") long id
    ) {
        Path entity = service.findById(id);

        if (entity != null) {
            entity.setPath(path.getPath());
            entity.setDate(path.getDate());
            service.save(entity);

            return new ResponseEntity<Path>(entity, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Path> delete(@PathVariable("id") long id) {
        Path path = service.findById(id);
        
        if (path == null) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);
        return ResponseEntity.accepted().build();
    }
    
}