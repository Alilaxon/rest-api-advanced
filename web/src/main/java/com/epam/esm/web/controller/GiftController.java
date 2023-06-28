package com.epam.esm.web.controller;
import com.epam.esm.model.dto.GiftDTO;
import com.epam.esm.model.exception.NoSuchGiftException;
import com.epam.esm.persistance.entity.GiftCertificate;
import com.epam.esm.model.exception.GiftNameIsReservedException;
import com.epam.esm.model.exception.InvalidGiftDtoException;
import com.epam.esm.model.exception.InvalidTagException;
import com.epam.esm.model.service.GiftService;
import com.epam.esm.model.service.impl.GiftServiceImpl;
import com.epam.esm.web.utils.Linker;
import com.epam.esm.web.utils.Sorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping( "/gift")
public class GiftController {

    private static final Logger log = LogManager.getLogger(GiftController.class);

    private final GiftService giftCertificateService;

    @Autowired
    public GiftController(GiftServiceImpl giftCertificateService) {

        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/get-all")
    public List<GiftDTO> getAll() {

        log.info("get_all");

        return Linker.addLinkToGiftDTO(giftCertificateService.getAll());

    }

    @GetMapping("/getAllByTag")
    public List<GiftCertificate> getAllByTag
            (@RequestParam("tag") String tag ,
             @RequestParam("order") String order,
            @RequestParam(name = "page",required = false, defaultValue = "1")Long page){

        log.info("get all by tag = {}",tag);
        if (order.equals("desc")){
            log.info("sort all by desc");
           return Sorter.sorting(giftCertificateService.getAllByTag(tag,page));
        }
        log.info("sort all by asc");
        return giftCertificateService.getAllByTag(tag,page);
    }
    @GetMapping("/getAllByDescription")
    public List<GiftCertificate> getAllByDescription (
            @RequestParam("description")String description,
            @RequestParam("order") String order){

        log.info("get all by description = {}",description);

        if (order.equals("desc")){
            log.info("sort all by desc");
            return Sorter.sorting(giftCertificateService.getAllByDescription(description));
        }
        log.info("sort all by asc");
        return giftCertificateService.getAllByDescription(description);
    }


    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody GiftDTO giftDto)
            throws GiftNameIsReservedException, InvalidTagException, InvalidGiftDtoException {

        log.info("Gift '{}' will be create",giftDto.getName());
        Long id = giftCertificateService.create(giftDto).getId();

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    @GetMapping("/read/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftDTO getById (@PathVariable("id") Long id) throws NoSuchGiftException {

        log.info("Get Gift by id = '{}'",id);

        GiftDTO giftDTO = giftCertificateService.get(id);

        Link selfLink = linkTo(methodOn(GiftController.class).getById(
                giftDTO.getId())).withSelfRel();

        return giftDTO.add(selfLink);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Long> create (@PathVariable("id") Long id,
                                        @RequestBody GiftDTO giftDto){
        log.info("Update Gift by id = '{}'",id);
        Long resultId = giftCertificateService.update(id,giftDto).getId();

        return ResponseEntity.status(HttpStatus.CREATED).body(resultId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete (@PathVariable Long id){
        log.info("Delete Gift by id = '{}'",id);
        giftCertificateService.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
