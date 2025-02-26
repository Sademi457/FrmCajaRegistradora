import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmCajaRegistradora extends JFrame {
    JComboBox<String> cmbDenominaciones;
    JTextField txtCantidad, txtDevolver;
    JButton btnAgregar, btnDevolver;
    JTable table;
    String[] denominaciones = {"100000", "50000", "20000", "10000", "2000", "1000", "500", "200", "50"};
    int[] existencias = new int[denominaciones.length];
    String[] columnNames = {"Cantidad", "Tipo", "Valor"};
    Object[][] data = new Object[denominaciones.length][3];

    public FrmCajaRegistradora() {
        
        setTitle("Devolveradora de Dinero");
        setSize(450, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblMostrarValor = new JLabel("Mostrar valor:");
        lblMostrarValor.setBounds(20, 20, 100, 25);
        getContentPane().add(lblMostrarValor);

        cmbDenominaciones = new JComboBox<>(denominaciones);
        cmbDenominaciones.setBounds(130, 20, 100, 25);
        getContentPane().add(cmbDenominaciones);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(240, 20, 100, 25);
        getContentPane().add(txtCantidad);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(20, 60, 100, 25);
        getContentPane().add(btnAgregar);

        JLabel lblDevolver = new JLabel("Devolver:");
        lblDevolver.setBounds(20, 100, 100, 25);
        getContentPane().add(lblDevolver);

        txtDevolver = new JTextField();
        txtDevolver.setBounds(130, 100, 100, 25);
        getContentPane().add(txtDevolver);

        btnDevolver = new JButton("Devolver");
        btnDevolver.setBounds(240, 100, 100, 25);
        getContentPane().add(btnDevolver);

        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 150, 400, 200);
        getContentPane().add(scrollPane);

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = cmbDenominaciones.getSelectedIndex();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                existencias[index] = cantidad;
                txtCantidad.setText("");
            }
        });

        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cantidadDevolver = Integer.parseInt(txtDevolver.getText());
                if (!calcularCambio(cantidadDevolver)) {
                    JOptionPane.showMessageDialog(null, "No hay existencia suficiente", "Error", JOptionPane.ERROR_MESSAGE);
                }
                table.repaint();
            }
        });
    }

    private boolean calcularCambio(int cantidad) {
        Object[][] nuevaData = new Object[denominaciones.length][3];
        int dataIndex = 0;

        for (int i = 0; i < denominaciones.length; i++) {
            int valor = Integer.parseInt(denominaciones[i]);
            int cantidadBilletes = Math.min(cantidad / valor, existencias[i]);

            if (cantidadBilletes > 0) {
                nuevaData[dataIndex][0] = cantidadBilletes;
                nuevaData[dataIndex][1] = (valor >= 1000) ? "Billete" : "Moneda";
                nuevaData[dataIndex][2] = valor;
                dataIndex++;
            }

            cantidad -= cantidadBilletes * valor;
            existencias[i] -= cantidadBilletes;
        }

        if (cantidad > 0) {
            return false;
        }

        data = new Object[dataIndex][3];
        System.arraycopy(nuevaData, 0, data, 0, dataIndex);
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        return true;
    }

    public static void main(String[] args) {
        new FrmCajaRegistradora().setVisible(true);  // Ahora SÍ puedes hacer esto 🎉
    }
}