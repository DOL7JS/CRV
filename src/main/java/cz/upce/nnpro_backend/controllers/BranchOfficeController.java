package cz.upce.nnpro_backend.controllers;


import cz.upce.nnpro_backend.dtos.BranchOfficeDto;
import cz.upce.nnpro_backend.dtos.BranchOfficeIdUserIdDto;
import cz.upce.nnpro_backend.services.BranchOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branchOffice")
@CrossOrigin
public class BranchOfficeController {
    private final BranchOfficeService branchOfficeService;

    public BranchOfficeController(BranchOfficeService branchOfficeService) {
        this.branchOfficeService = branchOfficeService;
    }

    @GetMapping("/getOffice/{officeId}")
    public ResponseEntity<?> getOffice(@PathVariable Long officeId) {
        return ResponseEntity.ok(branchOfficeService.getOffice(officeId));
    }

    @PostMapping("/addOffice")
    public ResponseEntity<?> addOffice(@RequestBody BranchOfficeDto officeDto) {
        return ResponseEntity.ok(branchOfficeService.addOffice(officeDto));
    }

    @PostMapping("/addUserToOffice")
    public ResponseEntity<?> addUserToOffice(@RequestBody BranchOfficeIdUserIdDto branchOfficeIdUserIdDto) {
        return ResponseEntity.ok(branchOfficeService.addUserToOffice(branchOfficeIdUserIdDto));
    }

    @DeleteMapping("/removeOffice/{officeId}")
    public ResponseEntity<?> removeOffice(@PathVariable Long officeId) {
        return ResponseEntity.ok(branchOfficeService.removeOffice(officeId));
    }

    @PutMapping("/editOffice/{officeId}")
    public ResponseEntity<?> editOffice(@PathVariable Long officeId, @RequestBody BranchOfficeDto officeDto) {
        return ResponseEntity.ok(branchOfficeService.editOffice(officeId, officeDto));
    }

    @GetMapping("/exportData")
    public ResponseEntity<?> exportDataToJson() {
        return ResponseEntity.ok(branchOfficeService.getOffices());
    }

    @PutMapping("/importData")
    public ResponseEntity<?> importDataToJson() {
        return ResponseEntity.ok("");
    }

}
