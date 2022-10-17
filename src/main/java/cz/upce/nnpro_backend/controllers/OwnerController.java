package cz.upce.nnpro_backend.controllers;

import cz.upce.nnpro_backend.dtos.CreateCarDto;
import cz.upce.nnpro_backend.dtos.OwnerDto;
import cz.upce.nnpro_backend.services.CarService;
import cz.upce.nnpro_backend.services.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
@CrossOrigin
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/getOwner/{idOwner}")
    public ResponseEntity<?> getOwner(@PathVariable Long idOwner) {
        return ResponseEntity.ok(ownerService.getOwner(idOwner));
    }
    @PostMapping("/addOwner")
    public ResponseEntity<?> addOwner(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok(ownerService.addOwner(ownerDto));
    }

    @DeleteMapping("/removeOwner/{idOwner}")
    public ResponseEntity<?> removeOwner(@PathVariable Long idOwner) {
        return ResponseEntity.ok(ownerService.removeOwner(idOwner));
    }

    @PutMapping("/editOwner/{idOwner}")
    public ResponseEntity<?> editOwner(@PathVariable Long idOwner, @RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok(ownerService.editOwner(idOwner, ownerDto));
    }
}
