package com.herodevs.nes.trial.spring.controller;

import com.herodevs.nes.trial.spring.model.Pet;
import com.herodevs.nes.trial.spring.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/helloPets")
    public String helloPets() {
        return "Woof! Meow! Blub! Tweet!";
    }

    @GetMapping("/allPets")
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @GetMapping("/petsByName")
    public List<Pet> getByName(String name) {
        return petRepository.findByName(name);
    }

    @GetMapping("/petsByType")
    public List<Pet> getByType(String type) {
        return petRepository.findByType(type);
    }

}
