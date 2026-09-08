package betracing365;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main application window for 'Bet Racing 365' Sorting Desktop App.
 * Features a modern, styled Swing GUI mimicking a sports betting dashboard.
 */
public class BetRacing365App extends JFrame {

    // Theme Colors
    private static final Color BG_DARK = new Color(18, 26, 33);         // Deep Slate Background
    private static final Color CARD_DARK = new Color(28, 38, 48);       // Lighter Slate for Panels
    private static final Color BORDER_COLOR = new Color(44, 58, 73);    // Subtle borders
    private static final Color ACCENT_GOLD = new Color(230, 175, 46);    // Gold text/accents
    private static final Color ACCENT_GREEN = new Color(36, 172, 90);    // Green buttons/headers
    private static final Color ACCENT_RED = new Color(217, 83, 79);      // Reset button
    private static final Color TEXT_LIGHT = new Color(235, 240, 245);    // Primary text
    private static final Color TEXT_MUTED = new Color(150, 165, 180);    // Secondary/muted text
    private static final Color INPUT_BG = new Color(36, 48, 60);         // Textfield background

    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 12);

    // GUI Components
    private JTextField[] horseFields = new JTextField[10];
    private JTextField[] jockeyFields = new JTextField[10];
    
    private DefaultTableModel tableModel;
    private JTable resultTable;
    
    // Data list representing current state
    private List<HorseJockeyPair> currentPairs = new ArrayList<>();

    public BetRacing365App() {
        super("Bet Racing 365 - Horse & Jockey Sorting Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 580);
        setLocationRelativeTo(null);
        
        // Root Panel
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(BG_DARK);
        setContentPane(rootPanel);

        // Header Panel
        rootPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Center Split Panel (Inputs on Left, Results & Actions on Right)
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 15, 0));
        mainContent.setBackground(BG_DARK);
        mainContent.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        mainContent.add(createInputPanel());
        mainContent.add(createResultsPanel());
        
        rootPanel.add(mainContent, BorderLayout.CENTER);
    }

    /**
     * Create the header banner with sports-themed coloring.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ACCENT_GREEN);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("BET RACING 365");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("DSA - Bubble Sort Version 3  |  Max 10 Competitors");
        subtitleLabel.setFont(FONT_SUBTITLE);
        subtitleLabel.setForeground(new Color(210, 245, 220));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(subtitleLabel, BorderLayout.EAST);
        
        return headerPanel;
    }

    /**
     * Create the input side panel where the user enters the 10 horse/jockey names.
     */
    private JPanel createInputPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(CARD_DARK);
        container.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Panel Title
        JLabel title = new JLabel("ENTER HORSES & JOCKEYS");
        title.setFont(FONT_SUBTITLE);
        title.setForeground(ACCENT_GOLD);
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        container.add(title, BorderLayout.NORTH);

        // Form Fields Panel
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(CARD_DARK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 5, 4, 5);

        // Grid Headers
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.05;
        JLabel numHeader = new JLabel("#");
        numHeader.setFont(FONT_BOLD);
        numHeader.setForeground(TEXT_MUTED);
        fieldsPanel.add(numHeader, gbc);

        gbc.gridx = 1; gbc.weightx = 0.45;
        JLabel horseHeader = new JLabel("Horse Name");
        horseHeader.setFont(FONT_BOLD);
        horseHeader.setForeground(TEXT_MUTED);
        fieldsPanel.add(horseHeader, gbc);

        gbc.gridx = 2; gbc.weightx = 0.45;
        JLabel jockeyHeader = new JLabel("Jockey Name");
        jockeyHeader.setFont(FONT_BOLD);
        jockeyHeader.setForeground(TEXT_MUTED);
        fieldsPanel.add(jockeyHeader, gbc);

        // 10 Input Rows
        for (int i = 0; i < 10; i++) {
            gbc.gridy = i + 1;

            // Row Index Label
            gbc.gridx = 0; gbc.weightx = 0.05;
            JLabel lblIndex = new JLabel(String.valueOf(i + 1));
            lblIndex.setFont(FONT_BOLD);
            lblIndex.setForeground(ACCENT_GOLD);
            fieldsPanel.add(lblIndex, gbc);

            // Horse Field
            gbc.gridx = 1; gbc.weightx = 0.45;
            horseFields[i] = new JTextField();
            styleTextField(horseFields[i]);
            fieldsPanel.add(horseFields[i], gbc);

            // Jockey Field
            gbc.gridx = 2; gbc.weightx = 0.45;
            jockeyFields[i] = new JTextField();
            styleTextField(jockeyFields[i]);
            fieldsPanel.add(jockeyFields[i], gbc);
        }

        container.add(fieldsPanel, BorderLayout.CENTER);

        // Input Action Buttons at Bottom
        JPanel buttonRow = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonRow.setBackground(CARD_DARK);
        buttonRow.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton btnShuffle = new JButton("Shuffle Current Pairs");
        styleButton(btnShuffle, BORDER_COLOR);
        btnShuffle.addActionListener(e -> shuffleInputs());

        JButton btnClear = new JButton("Clear All Fields");
        styleButton(btnClear, ACCENT_RED);
        btnClear.addActionListener(e -> clearInputs());

        buttonRow.add(btnShuffle);
        buttonRow.add(btnClear);

        container.add(buttonRow, BorderLayout.SOUTH);

        return container;
    }

    /**
     * Create the results list/table and all sorting control buttons.
     */
    private JPanel createResultsPanel() {
        JPanel container = new JPanel(new BorderLayout(0, 15));
        container.setBackground(BG_DARK);

        // Controls Panel (Algorithm choice & Sort triggers)
        JPanel controlsCard = new JPanel(new GridBagLayout());
        controlsCard.setBackground(CARD_DARK);
        controlsCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        // Algorithm Selector Row
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel lblAlgo = new JLabel("Sorting Algorithm:");
        lblAlgo.setFont(FONT_BOLD);
        lblAlgo.setForeground(TEXT_LIGHT);
        controlsCard.add(lblAlgo, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        JLabel lblSelectedAlgo = new JLabel("Bubble Sort Version 3 (Optimized)");
        lblSelectedAlgo.setFont(FONT_BOLD);
        lblSelectedAlgo.setForeground(ACCENT_GOLD);
        controlsCard.add(lblSelectedAlgo, gbc);

        // Horses Sorting Controls
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel lblSortHorse = new JLabel("Sort by Horses:");
        lblSortHorse.setFont(FONT_BOLD);
        lblSortHorse.setForeground(TEXT_LIGHT);
        controlsCard.add(lblSortHorse, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        JPanel horseBtnContainer = new JPanel(new GridLayout(1, 2, 10, 0));
        horseBtnContainer.setBackground(CARD_DARK);
        JButton btnSortHorseAsc = new JButton("Ascending (A-Z)");
        styleButton(btnSortHorseAsc, ACCENT_GOLD);
        btnSortHorseAsc.addActionListener(e -> triggerSort(true, true));
        
        JButton btnSortHorseDesc = new JButton("Descending (Z-A)");
        styleButton(btnSortHorseDesc, ACCENT_GOLD);
        btnSortHorseDesc.addActionListener(e -> triggerSort(true, false));

        horseBtnContainer.add(btnSortHorseAsc);
        horseBtnContainer.add(btnSortHorseDesc);
        controlsCard.add(horseBtnContainer, gbc);

        // Jockeys Sorting Controls
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        JLabel lblSortJockey = new JLabel("Sort by Jockeys:");
        lblSortJockey.setFont(FONT_BOLD);
        lblSortJockey.setForeground(TEXT_LIGHT);
        controlsCard.add(lblSortJockey, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        JPanel jockeyBtnContainer = new JPanel(new GridLayout(1, 2, 10, 0));
        jockeyBtnContainer.setBackground(CARD_DARK);
        JButton btnSortJockeyAsc = new JButton("Ascending (A-Z)");
        styleButton(btnSortJockeyAsc, ACCENT_GOLD);
        btnSortJockeyAsc.addActionListener(e -> triggerSort(false, true));

        JButton btnSortJockeyDesc = new JButton("Descending (Z-A)");
        styleButton(btnSortJockeyDesc, ACCENT_GOLD);
        btnSortJockeyDesc.addActionListener(e -> triggerSort(false, false));

        jockeyBtnContainer.add(btnSortJockeyAsc);
        jockeyBtnContainer.add(btnSortJockeyDesc);
        controlsCard.add(jockeyBtnContainer, gbc);

        container.add(controlsCard, BorderLayout.NORTH);

        // Table Panel showing matched records
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_DARK);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel tblTitle = new JLabel("CURRENT RACING CARD / MATCHED PAIRS");
        tblTitle.setFont(FONT_SUBTITLE);
        tblTitle.setForeground(ACCENT_GOLD);
        tblTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        tableCard.add(tblTitle, BorderLayout.NORTH);

        // Table Config
        tableModel = new DefaultTableModel(new Object[]{"Rank", "Horse Name", "Assigned Jockey"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        resultTable.setBackground(INPUT_BG);
        resultTable.setForeground(TEXT_LIGHT);
        resultTable.setFont(FONT_BODY);
        resultTable.setRowHeight(25);
        resultTable.setGridColor(BORDER_COLOR);
        resultTable.setShowGrid(true);

        // Style Table Header
        JTableHeader header = resultTable.getTableHeader();
        header.setBackground(CARD_DARK);
        header.setForeground(ACCENT_GOLD);
        header.setFont(FONT_BOLD);
        header.setBorder(new LineBorder(BORDER_COLOR));

        // Custom Cell Renderers (alignment & color coding)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(INPUT_BG);
        centerRenderer.setForeground(ACCENT_GOLD);
        resultTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        DefaultTableCellRenderer textRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? INPUT_BG : CARD_DARK);
                c.setForeground(TEXT_LIGHT);
                return c;
            }
        };
        resultTable.getColumnModel().getColumn(1).setCellRenderer(textRenderer);
        resultTable.getColumnModel().getColumn(2).setCellRenderer(textRenderer);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.getViewport().setBackground(CARD_DARK);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR));
        tableCard.add(scrollPane, BorderLayout.CENTER);

        container.add(tableCard, BorderLayout.CENTER);

        return container;
    }

    // The bottom visual tracer log panel was removed per request.

    /**
     * UTILITY: Styles JTextFields to fit the premium dark theme.
     */
    private void styleTextField(JTextField field) {
        field.setBackground(INPUT_BG);
        field.setForeground(TEXT_LIGHT);
        field.setCaretColor(TEXT_LIGHT);
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(2, 5, 2, 5)
        ));
    }

    /**
     * UTILITY: Styles buttons with custom backgrounds and hover behaviors.
     */
    private void styleButton(JButton button, Color primaryColor) {
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(FONT_BOLD);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(primaryColor.darker(), 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Simple hover animation
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(primaryColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(primaryColor);
            }
        });
    }

    /**
     * ACTIONS: Collects user inputs, validates them, and updates currentPairs list.
     */
    private boolean validateAndCollectInputs() {
        currentPairs.clear();
        for (int i = 0; i < 10; i++) {
            String horse = horseFields[i].getText().trim();
            String jockey = jockeyFields[i].getText().trim();
            
            if (horse.isEmpty() && jockey.isEmpty()) {
                continue; // Skip blank rows
            }
            if (horse.isEmpty() || jockey.isEmpty()) {
                String missingField = horse.isEmpty() ? "Horse Name" : "Jockey Name";
                String presentField = horse.isEmpty() ? "Jockey Name" : "Horse Name";
                String value = horse.isEmpty() ? jockey : horse;
                
                JOptionPane.showMessageDialog(this,
                        String.format("Row %d is incomplete!\nYou entered the %s: \"%s\"\nBut you are missing the corresponding %s.\n\nEither leave the row completely empty, or provide both a Horse and a Jockey.", 
                                i + 1, presentField, value, missingField),
                        "Incomplete Competitor Pair",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            currentPairs.add(new HorseJockeyPair(horse, jockey));
        }
        
        if (currentPairs.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter at least one Horse-Jockey pair to perform sorting.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * ACTIONS: Triggers the chosen sorting algorithm and prints outcomes.
     */
    private void triggerSort(boolean sortByHorse, boolean ascending) {
        if (!validateAndCollectInputs()) {
            return;
        }

        // Convert list to array for direct algorithm manipulation
        HorseJockeyPair[] array = currentPairs.toArray(new HorseJockeyPair[0]);

        // Logger callback outputs to standard System.out
        System.out.println("[BET RACING 365 - DSA SORTING INITIALIZED]");
        System.out.println("Input Size: " + array.length + " entries");
        System.out.println("=========================================");

        Sorter.LogCallback callback = System.out::println;

        long startTime = System.nanoTime();

        Sorter.bubbleSortV3(array, sortByHorse, ascending, callback);

        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("=========================================");
        System.out.printf("[SUCCESS] Sorting completed in %.3f ms.%n", durationMs);

        // Write sorted data back to the UI table
        updateTable(array);
    }

    /**
     * UI: Rebuilds JTable view from the sorted array.
     */
    private void updateTable(HorseJockeyPair[] sortedArray) {
        tableModel.setRowCount(0);
        for (int i = 0; i < sortedArray.length; i++) {
            tableModel.addRow(new Object[]{
                    (i + 1),
                    sortedArray[i].getHorseName(),
                    sortedArray[i].getJockeyName()
            });
        }
    }

    /**
     * ACTIONS: Loads preset racing card data.
     */
    // Load Initial Sample Data was removed to ensure the user is the only one who can add data.

    /**
     * ACTIONS: Clears all textfields and table values.
     */
    private void clearInputs() {
        for (int i = 0; i < 10; i++) {
            horseFields[i].setText("");
            jockeyFields[i].setText("");
        }
        tableModel.setRowCount(0);
    }

    /**
     * ACTIONS: Shuffles the current values in input fields randomly.
     */
    private void shuffleInputs() {
        if (!validateAndCollectInputs()) {
            return;
        }
        
        List<String[]> temp = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String horse = horseFields[i].getText().trim();
            String jockey = jockeyFields[i].getText().trim();
            if (!horse.isEmpty() && !jockey.isEmpty()) {
                temp.add(new String[]{horse, jockey});
            }
        }
        
        if (temp.isEmpty()) {
            return;
        }

        Collections.shuffle(temp);
        
        // Clear first
        for (int i = 0; i < 10; i++) {
            if (i < temp.size()) {
                horseFields[i].setText(temp.get(i)[0]);
                jockeyFields[i].setText(temp.get(i)[1]);
            } else {
                horseFields[i].setText("");
                jockeyFields[i].setText("");
            }
        }

        System.out.println("Shuffled inputs to randomize testing state.");
        
        // Re-read and draw table
        validateAndCollectInputs();
        updateTable(currentPairs.toArray(new HorseJockeyPair[0]));
    }

    /**
     * Main entry point.
     */
    public static void main(String[] args) {
        // Run on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Use standard cross-platform look-and-feel to avoid native UI overriding colors
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                // Ignore and use default
            }
            new BetRacing365App().setVisible(true);
        });
    }
}
