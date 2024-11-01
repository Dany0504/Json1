package org.example;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Reporte {
    public static void main(String[] args) {
        Gson gson = new Gson();
        List<Carro> carros = new ArrayList<>();

        try (JsonReader reader = new JsonReader(new FileReader("C:\\Users\\dany0\\Json\\src\\main\\java\\org\\example\\car_sales.json"))) {
            reader.beginArray();

            while (reader.hasNext()) {
                Carro carro = gson.fromJson(reader, Carro.class);
                carros.add(carro);
            }

            reader.endArray();

            List<String> marcas = new ArrayList<>();
            for (Carro carro : carros) {
                if (!marcas.contains(carro.car)) {
                    marcas.add(carro.car);
                }
            }

            for (String marca : marcas) {
                double totalPrecio = 0;
                int conteo = 0;

                for (Carro carro : carros) {
                    if (carro.car.equals(marca)) {
                        double precioNumerico = Double.parseDouble(carro.price.replace("$", "").replace(",", ""));
                        totalPrecio += precioNumerico;
                        conteo++;
                    }
                }

                if (conteo > 0) {
                    double promedio = totalPrecio / conteo;
                    System.out.printf("Promedio de %s: %.2f%n", marca, promedio);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
