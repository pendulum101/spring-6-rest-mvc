package com.scormican.spring6restmvc.services;

import com.scormican.spring6restmvc.model.BeerCSVRecord;
import java.io.File;
import java.util.List;

public interface BeerCsvService {

    public List<BeerCSVRecord> convertCSV(File file);
}
