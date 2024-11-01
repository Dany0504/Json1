package org.example;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Tabla extends JFrame {
    private JTable tabla;
    private DefaultTableModel ModeloTabla;

    public Tabla() {
        setTitle("Car Sales");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ModeloTabla = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "Car", "Price", "State"}, 0);
        tabla = new JTable(ModeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        CargarArchivo();
    }

    private void CargarArchivo() {
        Gson gson = new Gson();
        List<Carro> carros = new ArrayList<>();

        try (JsonReader reader = new JsonReader(new FileReader("C:\\Users\\dany0\\Json\\src\\main\\java\\org\\example\\car_sales.json"))) {
            reader.beginArray();

            while (reader.hasNext()) {
                Carro carro = gson.fromJson(reader, Carro.class);
                carros.add(carro);
                ModeloTabla.addRow(new Object[]{
                        carro.id,
                        carro.first_name,
                        carro.last_name,
                        carro.car,
                        carro.price,
                        carro.state
                });
            }

            reader.endArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tabla app = new Tabla();
            app.setVisible(true);
        });
    }
}
