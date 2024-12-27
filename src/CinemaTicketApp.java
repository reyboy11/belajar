import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CinemaTicketApp {

    private JFrame frame;
    private JTextField txtName;
    private JComboBox<String> cmbCinema, cmbMovie;
    private JComboBox<String> cmbPrice;
    private JLabel lblStudio, lblStartTime, lblEndTime, lblDate;
    private ArrayList<JButton> seatButtons;
    private ArrayList<String> bookedSeats;
    private DefaultTableModel tableModel;
    private JTable ticketTable;

    public CinemaTicketApp() {
        frame = new JFrame("Pesan Tiket");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        bookedSeats = new ArrayList<>();

        // Panel atas untuk input data
        JPanel topPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Informasi Pemesanan"));

        // Input data pemesanan
        topPanel.add(new JLabel("Nama Pemesan:"));
        txtName = new JTextField();
        topPanel.add(txtName);

        topPanel.add(new JLabel("Bioskop Pilihan:"));
        cmbCinema = new JComboBox<>(new String[]{"XX1", "XX2", "XX3"});
        topPanel.add(cmbCinema);

        topPanel.add(new JLabel("Film:"));
        cmbMovie = new JComboBox<>(new String[]{"PEMBURU IBLIS", "FILM A", "FILM B"});
        topPanel.add(cmbMovie);

        topPanel.add(new JLabel("Studio:"));
        lblStudio = new JLabel("Studio 1");
        topPanel.add(lblStudio);

        topPanel.add(new JLabel("Harga Tiket:"));
        cmbPrice = new JComboBox<>(new String[]{"30000", "50000", "70000"});
        topPanel.add(cmbPrice);

        topPanel.add(new JLabel("Waktu Mulai:"));
        lblStartTime = new JLabel("13:00");
        topPanel.add(lblStartTime);

        topPanel.add(new JLabel("Waktu Selesai:"));
        lblEndTime = new JLabel("15:30");
        topPanel.add(lblEndTime);

        topPanel.add(new JLabel("Tanggal:"));
        lblDate = new JLabel("2024-12-21");
        topPanel.add(lblDate);

        frame.add(topPanel, BorderLayout.NORTH);

        // Panel tengah untuk tata letak kursi
        JPanel seatPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        seatPanel.setBorder(BorderFactory.createTitledBorder("Pilih Kursi"));
        seatButtons = new ArrayList<>();

        String[] seatLabels = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4"};
        for (String label : seatLabels) {
            JButton seatButton = new JButton(label);
            seatButtons.add(seatButton);
            seatPanel.add(seatButton);

            // Tambahkan action listener untuk setiap tombol kursi
            seatButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (bookedSeats.contains(label)) {
                        JOptionPane.showMessageDialog(frame, "Kursi " + label + " sudah dipesan.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        bookedSeats.add(label);
                        seatButton.setEnabled(false);
                        JOptionPane.showMessageDialog(frame, "Kursi " + label + " berhasil dipilih.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
        }

        frame.add(seatPanel, BorderLayout.CENTER);

        // Panel bawah untuk tombol aksi
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Aksi"));

        JButton btnPesan = new JButton("Pesan");
        btnPesan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText().trim();

                if (name.isEmpty() || bookedSeats.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nama pemesan dan kursi harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    for (String seat : bookedSeats) {
                        tableModel.addRow(new Object[]{name, cmbCinema.getSelectedItem(), cmbMovie.getSelectedItem(), lblStudio.getText(), seat, lblDate.getText(), cmbPrice.getSelectedItem()});
                    }
                    JOptionPane.showMessageDialog(frame, "Tiket berhasil dipesan untuk " + name + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
                    resetForm();
                }
            }
        });
        bottomPanel.add(btnPesan);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
        bottomPanel.add(btnRefresh);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Panel tabel untuk menampilkan data tiket
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Data Pemesanan"));
        tableModel = new DefaultTableModel(new String[]{"Nama Pemesan", "Bioskop", "Film", "Studio", "Kursi", "Tanggal", "Harga"}, 0);
        ticketTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(tablePanel, BorderLayout.EAST);

        // Panel layar
        JPanel screenPanel = new JPanel();
        screenPanel.setBorder(BorderFactory.createTitledBorder("Layar"));
        screenPanel.add(new JLabel("Layar"));
        frame.add(screenPanel, BorderLayout.PAGE_END);

        frame.setVisible(true);
    }

    private void resetForm() {
        txtName.setText("");
        bookedSeats.clear();
        for (JButton button : seatButtons) {
            button.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CinemaTicketApp::new);
    }
}
