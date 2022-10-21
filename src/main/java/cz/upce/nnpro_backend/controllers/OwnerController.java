package cz.upce.nnpro_backend.controllers;

import cz.upce.nnpro_backend.dtos.CreateCarDto;
import cz.upce.nnpro_backend.dtos.OwnerDto;
import cz.upce.nnpro_backend.services.CarService;
import cz.upce.nnpro_backend.services.OwnerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
@CrossOrigin
@SecurityRequirement(name = "NNPRO_API")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/getOwner/{idOwner}")
    public ResponseEntity<?> getOwner(@PathVariable Long idOwner,
                                      @RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                      @RequestParam(defaultValue = "car.SPZ") String orderBy,
                                      @RequestParam(defaultValue = "ASC") String orderDirection) {
        return ResponseEntity.ok(ownerService.getOwner(idOwner, page, pageSize, orderBy, orderDirection));
    }
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PostMapping("/addOwner")
    public ResponseEntity<?> addOwner(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok(ownerService.addOwner(ownerDto));
    }
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @DeleteMapping("/removeOwner/{idOwner}")
    public ResponseEntity<?> removeOwner(@PathVariable Long idOwner) {
        return ResponseEntity.ok(ownerService.removeOwner(idOwner));
    }
    @PreAuthorize("hasRole('ROLE_Admin') || hasRole('ROLE_Okres')")
    @PutMapping("/editOwner/{idOwner}")
    public ResponseEntity<?> editOwner(@PathVariable Long idOwner, @RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok(ownerService.editOwner(idOwner, ownerDto));
    }
}
