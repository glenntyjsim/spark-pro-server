package com.example.login.controller;

import com.example.login.model.Package;
import com.example.login.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageRepository packageRepository;

    @GetMapping("/get-packages")
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    @GetMapping("/get-package/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable Long id) {
        Optional<Package> pkg = packageRepository.findById(id);
        if (pkg.isPresent()) {
            return ResponseEntity.ok(pkg.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create-package")
    public Package createPackage(@RequestBody Package pkg) {
        return packageRepository.save(pkg);
    }

    @PutMapping("/update-package/{id}")
    public ResponseEntity<Package> updatePackage(@PathVariable Long id, @RequestBody Package pkg) {
        Optional<Package> existingOpt = packageRepository.findById(id);
        if (existingOpt.isPresent()) {
            Package existing = existingOpt.get();
            existing.setServiceId(pkg.getServiceId());
            existing.setName(pkg.getName());
            existing.setFeature(pkg.getFeature());
            existing.setPrice(pkg.getPrice());
            return ResponseEntity.ok(packageRepository.save(existing));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-package/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        if (packageRepository.existsById(id)) {
            packageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
