package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.Owner;
import cz.upce.nnpro_backend.dtos.OwnerDetailOutDto;
import cz.upce.nnpro_backend.dtos.OwnerDto;
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


    public Owner addOwner(OwnerDto ownerDto) {
        Owner newOwner = ConversionService.convertToOwner(ownerDto);
        return ownerRepository.save(newOwner);
    }

    public Owner editOwner(Long ownerId, OwnerDto ownerDto) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        Owner newOwner = ConversionService.convertToOwner(owner, ownerDto);
        return ownerRepository.save(newOwner);
    }

    public Owner removeOwner(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        ownerRepository.deleteById(ownerId);
        return owner;
    }


    public OwnerDetailOutDto getOwner(Long idOwner) {
        Owner owner = ownerRepository.findById(idOwner).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        //        PageRequest pr = PageRequest.of(page, pageSize, orderDirection.equals("DESC") ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending());
//        Page<Car> carPage = carOwnerRepository.findCarOwnerByOwner(owner, pr);
//        OwnerDetailOutDto ownerDetailOutDto = ConversionService.convertToOwnerDetailOutDto(owner, carPage);
        return ConversionService.convertToOwnerDetailOutDto(owner);
    }

    public List<OwnerDetailOutDto> getAllOwners() {
        List<Owner> ownerList = ownerRepository.findAll();
        List<OwnerDetailOutDto> ownerDetailOutDtos = new ArrayList<>();
        for(Owner owner:ownerList){
            ownerDetailOutDtos.add(ConversionService.convertToOwnerDetailOutDto(owner));
        }
        return ownerDetailOutDtos;
    }
}
