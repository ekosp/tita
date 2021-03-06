/**
   Copyright 2009 TiTA Project, Vienna University of Technology
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE\-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package at.ac.tuwien.ifs.tita.business.csv;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import at.ac.tuwien.ifs.tita.business.service.time.IEffortService;
import at.ac.tuwien.ifs.tita.entity.Effort;
import at.ac.tuwien.ifs.tita.entity.TiTATask;
import at.ac.tuwien.ifs.tita.entity.TiTAUser;

/**
 * The implementation of the import reader using a csv reader.
 * 
 * @author Christoph
 * 
 */
public class CSVReader implements IImportReader {

    private static final Long C_ONE_HOUR = 3600000L;

    private IEffortService effortService;

    public CSVReader(IEffortService effortService) {
        this.effortService = effortService;
    }

    /**
     * Returns the first data row or the headers of the csv file.
     * 
     * @param path - file path
     * @return the first data row or the headers
     * @throws IOException io
     */
    public String[] getFirstDataSourceOrHeader(String path) throws IOException {
        ICsvBeanReader inFile = new CsvBeanReader(new FileReader(path), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

        return inFile.getCSVHeader(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Effort> importEffortData(String path, String[] header, CellProcessor[] processors, TiTATask task,
            TiTAUser user) throws IOException, PersistenceException, IllegalArgumentException {
        ICsvBeanReader inFile = new CsvBeanReader(new FileReader(path), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

        List<Effort> listOfImportedEfforts = new ArrayList<Effort>();

        // String[] headers = inFile.getCSVHeader(true);

        try {
            EffortBean effortBean;
            while ((effortBean = inFile.read(EffortBean.class, header, processors)) != null) {

                // Add one hour, because the csv converter does not work correct
                Long start = effortBean.getStartTime().getTime();
                Long ende = effortBean.getEndTime().getTime();
                Long duration = effortBean.getDurationAsLong() + C_ONE_HOUR;

                // Use this lines, if the csv lib change
                // String start1 = TiTATimeConverter.getDuration2String(start);
                // String ende1 = TiTATimeConverter.getDuration2String(ende);
                // String duration1 =
                // TiTATimeConverter.getDuration2String(duration);

                if (start + duration > ende) {
                    throw new IllegalArgumentException("The values are the not valid.");
                } else {

                    Effort effort = new Effort(task, null, effortBean.getDate(), start, ende, duration, effortBean
                            .getDescription(), false, user);
                    effortService.saveEffort(effort);
                    task.getTitaEfforts().add(effort);

                    listOfImportedEfforts.add(effort);
                }
            }
        } finally {
            inFile.close();
        }

        return listOfImportedEfforts;
    }

}
