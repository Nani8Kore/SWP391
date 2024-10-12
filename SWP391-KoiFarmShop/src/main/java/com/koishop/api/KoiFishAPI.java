package com.koishop.api;

import com.koishop.models.fish_model.*;
import com.koishop.service.KoiFishService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/")
@SecurityRequirement(name = "api")
@RestController
@RequestMapping("/api/koi-fishes")
public class KoiFishAPI {

    @Autowired
    KoiFishService koiFishService;

    // Lấy tất cả KoiFish
    @GetMapping("list")
    public FishResponse getAllKoiFish(@RequestParam int page) {
        return koiFishService.getAllKoiFish(page, 6);
    }

    // Tạo KoiFish mới
    @PostMapping("/add")
    public ViewFish createKoiFish(@Valid @RequestBody ViewFish koiFish) {
        return koiFishService.createKoiFish(koiFish);
    }

    // Cập nhật KoiFish theo ID
    @PutMapping("/{koiId}/{breed}/{origin}/update")
    public ResponseEntity updateKoiFish(@PathVariable Integer koiId, @PathVariable String breed, @PathVariable String origin, @RequestBody DefaultFish koiFishDetails) {
        ViewFish defaultFish = koiFishService.updateKoiFish(koiId, breed, origin, koiFishDetails);
        return ResponseEntity.ok(defaultFish);
    }
    @PutMapping("/{koiId}/updateIsForSale")
    public ResponseEntity updateIsForSale(@PathVariable Integer koiId) {
        koiFishService.updateIsForSale(koiId);
        return ResponseEntity.ok("Set successful");
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
        return koiFishService.getKoiFishesByBreed(breed, page, 6);
    }

    @GetMapping("/koiFish/cart/{id}")
    public FishForCart getIdforCart(@PathVariable int id) {
        return koiFishService.getFishCart(id);
    }

    @GetMapping("listfish")
    public List<FishForList> ListFish() {return koiFishService.ListFish();}

    // Xóa KoiFish theo ID
    @DeleteMapping("/{koiId}")
    public ResponseEntity<Void> deleteKoiFish(@PathVariable(value = "koiId") Integer koiId) {
        koiFishService.deleteKoiFish(koiId);
        return ResponseEntity.noContent().build();
    }


}