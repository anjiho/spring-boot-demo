package com.example.demo.controller;

import com.example.demo.dto.DeliveryDto;
import com.example.demo.dto.PriceSumDto;
import com.example.demo.jpa.DeliveryResultJpa;
import com.example.demo.jpa.repository.DeliveryResultJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "/delivery")
@RestController
public class DeliveryController {

    @Autowired
    private DeliveryResultJpaRepository deliveryResultJpaRepository;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> saveDelivery(@RequestParam(value = "customerName") String customerName,
                                               @RequestParam(value = "deliveryPrice") int deliveryPrice,
                                               @RequestParam(value = "isAccept", defaultValue = "false") boolean isAccept,
                                               @RequestParam(value = "deliveryDate") String deliveryDate,
                                               @RequestParam(value = "bank", required = false) String bank
                                               ) {

        if ( !StringUtils.isEmpty(customerName) ) {
            DeliveryResultJpa resultJpa = new DeliveryResultJpa().builder()
                    .customerName(customerName)
                    .deliveryPrice(deliveryPrice)
                    .isAccept(isAccept)
                    .deliveryDate(deliveryDate)
                    .createdDate(new Date())
                    .bank(bank)
                    .build();

            deliveryResultJpaRepository.save(resultJpa);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/list/{date}")
    public ResponseEntity<List<DeliveryResultJpa>> getDeliveryList(@PathVariable(value = "date") String date) {
        return ResponseEntity.ok(deliveryResultJpaRepository.findAllByDeliveryDateOrderByIdAsc(date));
    }

    @GetMapping(value = "/list/between")
    public ResponseEntity<List<DeliveryResultJpa>> getDeliveryListBetweenDate(@RequestParam(value = "startDate") String startDate,
                                                                              @RequestParam(value = "endDate") String endDate,
                                                                              @RequestParam(value = "isAccept", required = false) Boolean isAccept) {
        //전체선택
        if (isAccept == null) {
            return ResponseEntity.ok(deliveryResultJpaRepository.findAllByDeliveryDateBetweenOrderByIdDesc(startDate, endDate));
        }
        return ResponseEntity.ok(deliveryResultJpaRepository.findAllByDeliveryDateBetweenAndIsAcceptOrderByIdDesc(startDate, endDate, isAccept));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeliveryResultJpa> getDeliveryDetail(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(deliveryResultJpaRepository.findById(id).orElseGet(DeliveryResultJpa::new));
    }

//    @GetMapping(value = "/price/sum/{date}")
//    public ResponseEntity<PriceSumDto> getDeliveryPriceSum(@PathVariable(value = "date") String date) {
//        PriceSumDto sumDto = new PriceSumDto();
//        List<DeliveryResultJpa> resultList = deliveryResultJpaRepository.findAllByDeliveryDateOrderByIdAsc(date);
//        if ( !resultList.isEmpty() ) {
//            //수금 가격 합
//            int acceptSum = resultList.stream().filter(x -> x.isAccept()).mapToInt(DeliveryResultJpa::getDeliveryPrice).sum();
//            //미수금 가격 합
//            int notAcceptSum = resultList.stream().filter(x -> !x.isAccept()).mapToInt(DeliveryResultJpa::getDeliveryPrice).sum();
//
//            sumDto.setAcceptPriceSum(acceptSum);
//            sumDto.setNotAcceptPriceSum(notAcceptSum);
//        }
//        return ResponseEntity.ok(sumDto);
//    }

    @GetMapping(value = "/price/sum/between")
    public ResponseEntity<PriceSumDto> getDeliveryPriceSumBetweenDate(@RequestParam(value = "startDate") String startDate,
                                                                      @RequestParam(value = "endDate") String endDate) {
        PriceSumDto sumDto = new PriceSumDto();
        List<DeliveryResultJpa> resultList = deliveryResultJpaRepository.findAllByDeliveryDateBetweenOrderByIdDesc(startDate, endDate);
        if ( !resultList.isEmpty() ) {
            //수금 가격 합
            int acceptSum = resultList.stream().filter(x -> x.isAccept()).mapToInt(DeliveryResultJpa::getDeliveryPrice).sum();
            //미수금 가격 합
            int notAcceptSum = resultList.stream().filter(x -> !x.isAccept()).mapToInt(DeliveryResultJpa::getDeliveryPrice).sum();

            sumDto.setAcceptPriceSum(acceptSum);
            sumDto.setNotAcceptPriceSum(notAcceptSum);
        }
        return ResponseEntity.ok(sumDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateDelivery(@PathVariable(value = "id") long id,
                                                 @RequestBody DeliveryDto deliveryDto) {

        if ( id > 0 ) {
            DeliveryResultJpa resultJpa = new DeliveryResultJpa().builder()
                    .id(id)
                    .customerName(deliveryDto.getCustomerName())
                    .deliveryPrice(deliveryDto.getDeliveryPrice())
                    .isAccept(Boolean.parseBoolean(deliveryDto.getAccept()))
                    .deliveryDate(deliveryDto.getDeliveryDate())
                    .createdDate(new Date())
                    .bank(deliveryDto.getBank())
                    .build();

            deliveryResultJpaRepository.save(resultJpa);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteDelivery(@PathVariable(value = "id") long id) {
        deliveryResultJpaRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public static void main(String[] args) {
        char c1 = '1';
        char c2 = '2';
        System.out.print(c1 + c2);

    }

}
