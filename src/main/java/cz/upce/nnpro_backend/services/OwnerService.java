package cz.upce.nnpro_backend.services;

import cz.upce.nnpro_backend.Entities.Car;
import cz.upce.nnpro_backend.Entities.CarOwner;
import cz.upce.nnpro_backend.Entities.Owner;
import cz.upce.nnpro_backend.dtos.OwnerDetailOutDto;
import cz.upce.nnpro_backend.dtos.OwnerDto;
import cz.upce.nnpro_backend.repositories.CarOwnerRepository;
import cz.upce.nnpro_backend.repositories.OwnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.NoSuchElementException;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final CarOwnerRepository carOwnerRepository;

    public OwnerService(OwnerRepository ownerRepository, CarOwnerRepository carOwnerRepository) {
        this.ownerRepository = ownerRepository;
        this.carOwnerRepository = carOwnerRepository;
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


    public OwnerDetailOutDto getOwner(Long idOwner, Integer page, Integer pageSize, String orderBy, String orderDirection) {
        Owner owner = ownerRepository.findById(idOwner).orElseThrow(() -> new NoSuchElementException("Owner not found!"));
        PageRequest pr = PageRequest.of(page, pageSize, orderDirection.equals("DESC") ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending());
        Page<Car> carPage = carOwnerRepository.findCarOwnerByOwner(owner, pr);
        OwnerDetailOutDto ownerDetailOutDto = ConversionService.convertToOwnerDetailOutDto(owner, carPage);
        return ownerDetailOutDto;
    }
}
