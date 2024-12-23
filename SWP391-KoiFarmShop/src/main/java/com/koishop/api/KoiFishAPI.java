package com.koishop.api;

import com.koishop.models.fish_model.*;
import com.koishop.service.KoiFishService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "https://deploy-fe-kappa.vercel.app"})
@SecurityRequirement(name = "api")
@RestController
@RequestMapping("/api/koi-fishes")
public class KoiFishAPI {

    @Autowired
    KoiFishService koiFishService;

    // Lấy tất cả KoiFish
    @GetMapping("list")
    public FishResponse getAllKoiFish(@RequestParam int page) {
        return koiFishService.getAllKoiFish(page, 4);
    }

    // Tạo KoiFish mới
    @PostMapping("/add")
    public ViewFish createKoiFish(@Valid @RequestBody ViewFish koiFish) {
        return koiFishService.createKoiFish(koiFish);
    }

    @PostMapping("/fish-consign")
    public int consign(@Valid @RequestBody FishForConsignment koiFish) {
        return koiFishService.customerFishConsign(koiFish);
    }

    // Cập nhật KoiFish theo ID
    @PutMapping("/{koiId}/update")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity updateKoiFish(@PathVariable Integer koiId, @Valid @RequestBody ViewFish koiFishDetails) {
        ViewFish defaultFish = koiFishService.updateKoiFish(koiId, koiFishDetails);
        return ResponseEntity.ok(defaultFish);
    }

    @GetMapping("/{koiId}/checkIsForSale")
    public boolean checkIsForSale(@PathVariable Integer koiId) {
        return koiFishService.getIsForSale(koiId);
    }

    @PutMapping("/{koiId}/updateIsForSale")
    @PreAuthorize("hasAuthority('Manager')")
    public boolean updateIsForSale(@PathVariable Integer koiId) {
        return koiFishService.updateIsForSale(koiId);
    }

    @GetMapping("/{koiFishName}/koiFish")
    public ResponseEntity getKoiFishByName(@PathVariable String koiFishName) {
        ViewFish koiFish = koiFishService.getKoiFishByName(koiFishName);
        return ResponseEntity.ok(koiFish);
    }

    @GetMapping("/koiFish/{id}")
    public ResponseEntity getKoiFishByID(@PathVariable int id) {
        ViewFish koiFish = koiFishService.getKoiFishById(id);
        return ResponseEntity.ok(koiFish);
    }

    @GetMapping("/{koiFishName}/search")
    public List<String> searchKoiFish(@PathVariable String koiFishName) {
        return koiFishService.searchKoiFishByName(koiFishName);
    }

    @GetMapping("/{breed}")
    public FishResponse getKoiFishByOrigin(@PathVariable String breed, @RequestParam int page) {
        return koiFishService.getKoiFishesByBreed(breed, page, 4);
    }

    @GetMapping("/koiFish/cart/{id}")
    public FishForCart getIdforCart(@PathVariable int id) {
        return koiFishService.getFishCart(id);
    }

    @GetMapping("listfish")
    @PreAuthorize("hasAuthority('Manager')")
    public List<ListFishForManager> ListFish() {return koiFishService.ListFish();}

    @GetMapping("/{koiId}/fishName")
    public String getFishName(@PathVariable Integer koiId) {return koiFishService.getFishName(koiId);}

    @PutMapping("/{koiId}/delete")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity<Void> deleteKoiFish(@PathVariable(value = "koiId") Integer koiId) {
        koiFishService.deleteKoiFish(koiId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{image}/upload_image")
    public byte uploadImage(@PathVariable String image) {
        return koiFishService.convertImage(image);
    }
}
