package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.Owner;
import cz.upce.nnpro_backend.dtos.OwnerDto;
import cz.upce.nnpro_backend.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    public Owner addOwner(OwnerDto ownerDto) {
        Owner newOwner = ConversionService.convertToOwner(ownerDto);
        Owner owner = ownerRepository.save(newOwner);
        return owner;
    }

    public Owner editOwner(Long ownerId, OwnerDto ownerDto) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        Owner newOwner = ConversionService.convertToOwner(owner, ownerDto);
        Owner save = ownerRepository.save(newOwner);
        return save;
    }

    public Owner removeOwner(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        ownerRepository.deleteById(ownerId);
        return owner;
    }


    public Owner getOwner(Long idOwner) {
        Owner owner = ownerRepository.findById(idOwner).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        return owner;
    }
}
