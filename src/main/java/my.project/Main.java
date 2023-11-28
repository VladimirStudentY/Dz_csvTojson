package my.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "src/dataFiles/data.csv";

        List<Employee> list = parseCSV(columnMapping, fileName);
        System.out.println();
        String json = listToJson(list);
        System.out.println(json);
        jsonToFail(list);
        System.out.println();

    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(list);
    }

    private static void jsonToFail(List<Employee> list) {
        JSONObject obj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        //  obj.put(listToJson());
        try {
            FileWriter file = new FileWriter("src/dataFiles/data.json");
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> listPars = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy =
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();

            listPars = csv.parse();
            System.out.println();
            System.out.println("\t* ** * **\t");
            listPars.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listPars;
    }

}
