package io.ggroup.demo.controller;

import io.ggroup.demo.dto.MyyntiraporttiDTO;
import io.ggroup.demo.repository.MyyntiraporttiRepository;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/myyntiraportti")
@Tag(name = "Myyntiraportti API", description = "Endpoint for fetching myyntiraportti")
public class MyyntiraporttiController {

    private final MyyntiraporttiRepository repository;

    public MyyntiraporttiController(MyyntiraporttiRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<MyyntiraporttiDTO> raportti() {
        return repository.haeMyyntiraportti();
    }
}