package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile);
}
