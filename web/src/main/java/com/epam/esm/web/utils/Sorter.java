package com.epam.esm.web.utils;

import com.epam.esm.persistance.entity.GiftCertificate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sorter {

    private static final Logger log = LogManager.getLogger(Sorter.class);

    public static List<GiftCertificate> sorting(List<GiftCertificate> list){


        for (GiftCertificate gift: list) {
           log.info("Gift name = {}",gift.getName());
        }
        List<GiftCertificate> sortedList = list.stream()
                .sorted(Comparator.comparing(GiftCertificate::getName).reversed())
                .collect(Collectors.toList());

        for (GiftCertificate gift: sortedList) {
            log.info("Sorted Gift name = {}",gift.getName());
        }

        return sortedList;
    }
}
