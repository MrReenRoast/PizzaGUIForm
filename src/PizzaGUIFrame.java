import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private ButtonGroup crustGroup;  // Move this to the class level
    private JComboBox<String> sizeComboBox;
    private JCheckBox topping1, topping2, topping3, topping4, topping5, topping6;
    private JTextArea receiptArea;
    private JButton orderButton, clearButton, quitButton;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crust panel with radio buttons
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Choose Crust"));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep Dish");
        crustGroup = new ButtonGroup();  // Initialize the group here
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        // Size panel with JComboBox
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Choose Size"));
        String[] sizes = {"Small ($8)", "Medium ($12)", "Large ($16)", "Super ($20)"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        // Toppings panel with checkboxes
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Choose Toppings"));
        toppingsPanel.setLayout(new GridLayout(3, 2));  // Arrange toppings nicely
        topping1 = new JCheckBox("Pepperoni ($1)");
        topping2 = new JCheckBox("Mushrooms ($1)");
        topping3 = new JCheckBox("Onions ($1)");
        topping4 = new JCheckBox("Sausage ($1)");
        topping5 = new JCheckBox("Bacon ($1)");
        topping6 = new JCheckBox("Extra Cheese ($1)");
        toppingsPanel.add(topping1);
        toppingsPanel.add(topping2);
        toppingsPanel.add(topping3);
        toppingsPanel.add(topping4);
        toppingsPanel.add(topping5);
        toppingsPanel.add(topping6);

        // Receipt area with JScrollPane
        JPanel receiptPanel = new JPanel();
        receiptPanel.setBorder(BorderFactory.createTitledBorder("Order Receipt"));
        receiptArea = new JTextArea(10, 40);  // Initial size is fine for text area rows and columns
        receiptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setPreferredSize(new Dimension(300, 625));  // Set preferred size here
        receiptPanel.add(scrollPane);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add panels to frame
        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.WEST);
        add(toppingsPanel, BorderLayout.CENTER);
        add(receiptPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Listeners
        orderButton.addActionListener(new OrderButtonListener());
        clearButton.addActionListener(new ClearButtonListener());
        quitButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Confirm Quit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    // Action Listeners
    private class OrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String crust = "";
            if (thinCrust.isSelected()) crust = "Thin";
            if (regularCrust.isSelected()) crust = "Regular";
            if (deepDishCrust.isSelected()) crust = "Deep Dish";

            if (crust.isEmpty() || sizeComboBox.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(PizzaGUIFrame.this, "Please select a crust and size.");
                return;
            }

            // Get size and base price
            String size = sizeComboBox.getSelectedItem().toString();
            double basePrice = 8 + sizeComboBox.getSelectedIndex() * 4;

            // Get toppings
            StringBuilder toppings = new StringBuilder();
            int toppingCount = 0;
            JCheckBox[] toppingBoxes = {topping1, topping2, topping3, topping4, topping5, topping6};
            for (JCheckBox box : toppingBoxes) {
                if (box.isSelected()) {
                    toppings.append(box.getText()).append("\n");
                    toppingCount++;
                }
            }

            if (toppingCount == 0) {
                JOptionPane.showMessageDialog(PizzaGUIFrame.this, "Please select at least one topping.");
                return;
            }

            double toppingsCost = toppingCount;
            double subTotal = basePrice + toppingsCost;
            double tax = subTotal * 0.07;
            double total = subTotal + tax;

            // Display order receipt
            receiptArea.setText("=========================================\n");
            receiptArea.append("Type of Crust: " + crust + " & Size: " + size + "\n");
            receiptArea.append("Price: $" + String.format("%.2f", basePrice) + "\n");
            receiptArea.append("Toppings:\n" + toppings);
            receiptArea.append("Sub-total: $" + String.format("%.2f", subTotal) + "\n");
            receiptArea.append("Tax: $" + String.format("%.2f", tax) + "\n");
            receiptArea.append("-----------------------------------------\n");
            receiptArea.append("Total: $" + String.format("%.2f", total) + "\n");
            receiptArea.append("=========================================\n");
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            crustGroup.clearSelection();  // Now accessible
            sizeComboBox.setSelectedIndex(-1);
            topping1.setSelected(false);
            topping2.setSelected(false);
            topping3.setSelected(false);
            topping4.setSelected(false);
            topping5.setSelected(false);
            topping6.setSelected(false);
            receiptArea.setText("");
        }
    }
}
