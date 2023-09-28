package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.entities.Owner;
import cz.upce.nnpro_backend.dtos.OwnerOutDto;
import cz.upce.nnpro_backend.dtos.OwnerInDto;
import cz.upce.nnpro_backend.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    public Owner addOwner(OwnerInDto ownerInDto) {
        Owner newOwner = ConversionService.convertToOwner(ownerInDto);
        return ownerRepository.save(newOwner);
    }

    public Owner editOwner(Long ownerId, OwnerInDto ownerInDto) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        Owner newOwner = ConversionService.convertToOwner(owner, ownerInDto);
        return ownerRepository.save(newOwner);
    }

    public Owner removeOwner(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        ownerRepository.deleteById(ownerId);
        return owner;
    }


    public OwnerOutDto getOwner(Long idOwner) {
        Owner owner = ownerRepository.findById(idOwner).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        return ConversionService.convertToOwnerDetailOutDto(owner);
    }

    public List<OwnerOutDto> getAllOwners() {
        List<Owner> ownerList = ownerRepository.findAll();
        List<OwnerOutDto> ownerOutDtos = new ArrayList<>();
        for(Owner owner:ownerList){
            ownerOutDtos.add(ConversionService.convertToOwnerDetailOutDto(owner));
        }
        return ownerOutDtos;
    }
}
