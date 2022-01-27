package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/json")
    public Map<String, String> createPet(@RequestBody Pet pet){
        petModel.add(pet, newId.getAndIncrement());
        Map<String, String> result = new HashMap<>();
        if(petModel.getAll().size() == 1){
            result.put("result", "The first pet has been created!");
        }
        else
            result.put("result", "The pet has been created!");
        return result;
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll(){
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id){
        return petModel.getFromList(id.get("id"));
    }

    @PutMapping(value = "/updatePet", consumes = "application/json", produces = "application/json")
    public Map<String, String> updatePet(@RequestBody Map<String, Object> request){
        int id = (int) request.get("id");
        String name = request.get("name").toString();
        String type = request.get("type").toString();
        int age = (int) request.get("age");

        petModel.updatePet(id, name, type, age);

        Map<String, String> result = new HashMap<>();
        result.put("result", "The pet was updated!");
        return result;
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/json")
    public Map<String, String> deletePet(@RequestBody Map<String, Integer> request){
        int id = (int) request.get("id");
        
        petModel.deletePet(id);

        Map<String, String> result = new HashMap<>();
        result.put("result", "The pet was deleted!");
        return result;
    }

}
