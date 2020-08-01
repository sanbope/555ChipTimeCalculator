package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class View extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtResistor;
	private JTextField txtCapacitor;
	private JTextField txtTime;
	private JLabel lblResult, lblImage;
	private JComboBox<String> cbColor1, cbColor2, cbColor3;
	private JPanel pColor1, pColor2, pColor3;
	private JButton btnCopy;

	private void calculate()
	{
		String resistor, capacitor, time;
		double result;

		resistor = txtResistor.getText().replace(',', '.');
		capacitor = txtCapacitor.getText().replace(',', '.');
		time = txtTime.getText().replace(',', '.');

		int count = 0;

		if (resistor.isEmpty())
		{
			count++;
		} else
		{
			changeResistorColor(Integer.parseInt(txtResistor.getText()));
		}
		if (capacitor.isEmpty())
		{
			count++;
		}
		if (time.isEmpty())
		{
			count++;
		}

		if (count != 1) //with this 4 if we can know if the user has more than 1 data empty or if user give us the 3 data
		{
			JOptionPane.showMessageDialog(null, "Please give us 2 data.");
		} else
		{
			double ohm, uF, s;
			String unit = "";
			try
			{
				result = 0;
				if (resistor.isEmpty())
				{
					s = Double.parseDouble(time);
					uF = (Double.parseDouble(capacitor) / Math.pow(10, 6));
					result = s / uF;
					unit = "ohm";
					changeResistorColor((int) result);
				} else if (capacitor.isEmpty())
				{
					s = Double.parseDouble(time);
					ohm = Double.parseDouble(resistor);
					result = (s / ohm) * Math.pow(10, 6);
					unit = "uF";
				} else if (time.isEmpty())
				{
					ohm = Double.parseDouble(resistor);
					uF = (Double.parseDouble(capacitor) / Math.pow(10, 6));
					result = ohm * uF;
					unit = "s";
				}

				lblResult.setText(format(result) + "" + unit);
			} catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Please put just numbers.");
			}
		}
	}

	private String format(double result)
	{
		String resultText;

		if (("" + result).contains("E"))
		{
			resultText = "" + (new DecimalFormat("#.####################################").format(result));
		} else
		{
			resultText = ("" + result).replace('.', ',');
		}

		String r = resultText.split(",")[0];

		int count = 0;
		if (r.length() > 3)
		{
			for (int i = r.length() - 1; i >= 0; i--)
			{
				count++;
				if (count % 3 == 0 && i != 0)
				{
					r = r.substring(0, i) + "." + r.substring(i, r.length());
				}
			}
		}

		if (resultText.split(",")[1] != "0")
		{
			if (result >= 1 && resultText.split(",")[1].length() > 2)
			{
				r = r + "," + resultText.split(",")[1].substring(0, 2);
			} else
			{
				r = r + "," + resultText.split(",")[1];
			}
		}

		return r;
	}

	private String calculateResistor()
	{
		String value = "" + cbColor1.getSelectedIndex();
		value += cbColor2.getSelectedIndex();

		String zero = "" + (Math.pow(10, (cbColor3.getSelectedIndex())));

		value += zero.replace("1", "").replace(".0", "");
		
		return value;
	}

	private void changeResistorColor(int value)
	{
		String v = "" + value;

		int count = 0;
		for (int i = 0; i < v.length(); i++)
		{
			if (i > 1)
			{
				count++;
			}
		}
		v.replace("0", "");
		cbColor3.setSelectedIndex(count);

		count = 0;
		if (v.length() > 1)
		{
			cbColor1.setSelectedIndex(Integer.parseInt("" + v.charAt(0)));
			cbColor2.setSelectedIndex(Integer.parseInt("" + v.charAt(1)));
		} else
		{
			cbColor1.setSelectedIndex(0);
			cbColor2.setSelectedIndex(Integer.parseInt(v));
		}
	}

	private void changeColor(JComboBox<String> comboBox, JPanel panel)
	{
		switch (comboBox.getSelectedItem().toString().toLowerCase())
		{
			case "black":
				panel.setBackground(Color.BLACK);
				break;
			case "brown":
				panel.setBackground(new Color(150, 75, 0));
				break;
			case "red":
				panel.setBackground(Color.RED);
				break;
			case "orange":
				panel.setBackground(Color.ORANGE);
				break;
			case "yellow":
				panel.setBackground(Color.YELLOW);
				break;
			case "green":
				panel.setBackground(Color.GREEN);
				break;
			case "blue":
				panel.setBackground(Color.BLUE);
				break;
			case "violet":
				panel.setBackground(Color.MAGENTA);
				break;
			case "grey":
			case "gray":
				panel.setBackground(Color.GRAY);
				break;
			case "white":
				panel.setBackground(Color.WHITE);
				break;
		}

		contentPane.repaint();
		System.out.println(panel.getBackground());
	}

	/**
	 * Create the frame.
	 */
	public View()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Resistor:");
		lblNewLabel.setBounds(12, 13, 56, 16);
		contentPane.add(lblNewLabel);

		txtResistor = new JTextField();
		txtResistor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0)
			{
				if (arg0.getKeyChar() == '\n')
				{
					calculate();
				}
			}
		});
		txtResistor.setBounds(80, 10, 116, 22);
		contentPane.add(txtResistor);
		txtResistor.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Capacitor:");
		lblNewLabel_1.setBounds(12, 48, 69, 16);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Time:");
		lblNewLabel_2.setBounds(12, 83, 56, 16);
		contentPane.add(lblNewLabel_2);

		txtCapacitor = new JTextField();
		txtCapacitor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0)
			{
				if (arg0.getKeyChar() == '\n')
				{
					calculate();
				}
			}
		});
		txtCapacitor.setBounds(80, 45, 116, 22);
		contentPane.add(txtCapacitor);
		txtCapacitor.setColumns(10);

		txtTime = new JTextField();
		txtTime.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0)
			{
				if (arg0.getKeyChar() == '\n')
				{
					calculate();
				}
			}
		});
		txtTime.setBounds(80, 80, 116, 22);
		contentPane.add(txtTime);
		txtTime.setColumns(10);

		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				calculate();
			}
		});
		btnCalculate.setBounds(90, 115, 97, 25);
		contentPane.add(btnCalculate);

		lblResult = new JLabel("");
		lblResult.setFont(new Font("SansSerif", Font.PLAIN, 30));
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setBounds(12, 160, 699, 80);
		contentPane.add(lblResult);

		JLabel lblNewLabel_3 = new JLabel("Ohm");
		lblNewLabel_3.setBounds(208, 13, 56, 16);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("uF");
		lblNewLabel_4.setBounds(208, 48, 56, 16);
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("S");
		lblNewLabel_5.setBounds(208, 83, 56, 16);
		contentPane.add(lblNewLabel_5);

		lblImage = new JLabel();
		lblImage.setBounds(533, 10, 178, 54);
		ImageIcon imageIcon = new ImageIcon(View.class.getResource("/images/resistor.png"));
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);
		lblImage.setIcon(imageIcon);
		contentPane.add(lblImage);

		cbColor1 = new JComboBox<String>();
		cbColor1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0)
			{
				changeColor(cbColor1, pColor1);
				calculateResistor();
			}
		});
		cbColor1.setModel(new DefaultComboBoxModel<String>(new String[] { "Black", "Brown", "Red", "Orange", "Yellow",
				"Green", "Blue", "Violet", "Grey", "White" }));
		cbColor1.setMaximumRowCount(10);
		cbColor1.setBounds(257, 10, 80, 22);
		cbColor1.setFocusable(false);
		contentPane.add(cbColor1);

		cbColor2 = new JComboBox<String>();
		cbColor2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0)
			{
				changeColor(cbColor2, pColor2);
				calculateResistor();
			}
		});
		cbColor2.setModel(new DefaultComboBoxModel<String>(new String[] { "Black", "Brown", "Red", "Orange", "Yellow",
				"Green", "Blue", "Violet", "Grey", "White" }));
		cbColor2.setMaximumRowCount(10);
		cbColor2.setBounds(349, 10, 80, 22);
		cbColor2.setFocusable(false);
		contentPane.add(cbColor2);

		cbColor3 = new JComboBox<String>();
		cbColor3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				changeColor(cbColor3, pColor3);
				calculateResistor();
			}
		});
		cbColor3.setModel(new DefaultComboBoxModel<String>(new String[] { "Black", "Brown", "Red", "Orange", "Yellow",
				"Green", "Blue", "Violet", "Grey", "White" }));
		cbColor3.setMaximumRowCount(10);
		cbColor3.setBounds(441, 10, 80, 22);
		cbColor3.setFocusable(false);
		contentPane.add(cbColor3);

		pColor1 = new JPanel();
		pColor1.setBackground(Color.BLACK);
		pColor1.setBounds(570, 18, 16, 41);
		contentPane.add(pColor1);

		pColor2 = new JPanel();
		pColor2.setBackground(Color.BLACK);
		pColor2.setBounds(596, 23, 16, 32);
		contentPane.add(pColor2);

		pColor3 = new JPanel();
		pColor3.setBackground(Color.BLACK);
		pColor3.setBounds(614, 23, 16, 32);
		contentPane.add(pColor3);
		
		btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtResistor.setText(calculateResistor());
			}
		});
		btnCopy.setBounds(570, 72, 97, 25);
		contentPane.add(btnCopy);
	}
}
