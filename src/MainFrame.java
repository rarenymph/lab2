import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
// Главный класс приложения, он же класс фрейма
public class MainFrame extends JFrame {
    // Размеры окна приложения в виде констант
    private static final int WIDTH = 800; // Увеличил размер окна для лучшего отображения
    private static final int HEIGHT = 600;
    // Текстовые поля для считывания значений переменных,
    // как компоненты, совместно используемые в различных методах
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;
    // Текстовое поле для отображения результата,
    // как компонент, совместно используемый в различных методах
    private JTextField textFieldResult;
    // Группа радио-кнопок для обеспечения уникальности выделения в группе
    private ButtonGroup radioButtons = new ButtonGroup();
    // Контейнер для отображения радио-кнопок
    private Box hboxFormulaType = Box.createHorizontalBox();
    private int formulaId = 1;
    // Панель для отображения изображения (если требуется)
    private JPanel imagePane;
    private Image formula1Image;
    private Image formula2Image;

    // Внутренняя память
    private double mem1 = 0.0, mem2 = 0.0, mem3 = 0.0;
    private double currentMemory = 0.0;
    private ButtonGroup memRadioButtons = new ButtonGroup();
    private int selectedMemory = 1;
    private JTextField mem1Field;
    private JTextField mem2Field;
    private JTextField mem3Field;

    // Формула №1 для рассчета
    public Double calculate1(Double x, Double y, Double z) {
        double p1 = Math.sin(Math.log(y) + Math.sin(Math.PI * y) * Math.sin(Math.PI * y));
        double p2 = Math.pow(x * x + Math.sin(z) + Math.exp(Math.cos(z)), 0.25);
        return p1 * p2;
    }

    // Формула №2 для рассчета
    public Double calculate2(Double x, Double y, Double z) {
        double part1 = Math.pow(Math.cos(Math.exp(x)) + Math.log(1 + y), 2);
        double part2 = Math.sqrt(Math.pow(Math.E, Math.cos(x)) + Math.pow(Math.sin(Math.PI * z), 2)
                + Math.sqrt(1 / x) + Math.cos(Math.pow(y, 2)));
        double result = Math.pow(part1 + part2, Math.sin(z));
        return result;
    }

    // Вспомогательный метод для добавления кнопок на панель
    private void addRadioButton(String buttonName, final int formulaId) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                MainFrame.this.formulaId = formulaId;
                if (imagePane != null) {
                    imagePane.repaint();
                }
            }
        });
        radioButtons.add(button);
        hboxFormulaType.add(button);
    }

    // Вспомогательный метод для добавления радио-кнопок памяти
    private void addMemoryRadioButton(String buttonName, final int memoryId) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                selectedMemory = memoryId;
            }
        });
        memRadioButtons.add(button);
        Box hboxMemoryButtons = Box.createHorizontalBox();
        hboxMemoryButtons.add(button);
        add(hboxMemoryButtons);
    }

    // Конструктор класса
    public MainFrame() {
        super("Вычисление формулы");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        // Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Загрузка изображений формул
        try {
            formula1Image = ImageIO.read(new File(".idea/images/formula1.bmp"));
            formula2Image = ImageIO.read(new File(".idea/images/formula2.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        imagePane = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (formulaId == 1 && formula1Image != null) {
                    g.drawImage(formula1Image, 0, 0, null);
                } else if (formulaId == 2 && formula2Image != null) {
                    g.drawImage(formula2Image, 0, 0, null);
                }
            }
        };
        imagePane.setPreferredSize(new java.awt.Dimension(200, 200));

        hboxFormulaType.add(Box.createHorizontalGlue());
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtons.setSelected(
                radioButtons.getElements().nextElement().getModel(), true);
        hboxFormulaType.add(Box.createHorizontalGlue());
        hboxFormulaType.setBorder(
                BorderFactory.createLineBorder(Color.YELLOW));

        // Создать область с полями ввода для X, Y и Z
        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("0", 10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("0", 10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("0", 10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());

        Box hboxVariables = Box.createHorizontalBox();
        hboxVariables.setBorder(
                BorderFactory.createLineBorder(Color.RED));
        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(labelForX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldX);
        hboxVariables.add(Box.createHorizontalStrut(100));
        hboxVariables.add(labelForY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldY);
        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(Box.createHorizontalStrut(100));
        hboxVariables.add(labelForZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldZ);
        hboxVariables.add(Box.createHorizontalGlue());

        // Создать область для вывода результата
        JLabel labelForResult = new JLabel("Результат:");
        textFieldResult = new JTextField("0", 10); // Инициализация textFieldResult
        textFieldResult.setEditable(false); // Запрещаем ввод с клавиатуры
        textFieldResult.setMaximumSize(
                textFieldResult.getPreferredSize());
        Box hboxResult = Box.createHorizontalBox();
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(textFieldResult);
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        // Создать область для кнопок
        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Double x = Double.parseDouble(textFieldX.getText());
                    Double y = Double.parseDouble(textFieldY.getText());
                    Double z = Double.parseDouble(textFieldZ.getText());
                    Double result;
                    if (formulaId == 1)
                        result = calculate1(x, y, z);
                    else
                        result = calculate2(x, y, z);
                    textFieldResult.setText(result.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldResult.setText("0");
            }
        });

        JButton buttonMC = new JButton("MC");
        buttonMC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (selectedMemory == 1) {
                    mem1 = 0.0;
                } else if (selectedMemory == 2) {
                    mem2 = 0.0;
                } else {
                    mem3 = 0.0;
                }
                updateMemoryFields();
            }
        });

        JButton buttonMPlus = new JButton("M+");
        buttonMPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Double result = Double.parseDouble(textFieldResult.getText());
                    if (selectedMemory == 1) {
                        mem1 += result;
                    } else if (selectedMemory == 2) {
                        mem2 += result;
                    } else {
                        mem3 += result;
                    }
                    updateMemoryFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonMC);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonMPlus);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.setBorder(
                BorderFactory.createLineBorder(Color.GREEN));

        // Создать панель для отображения текущих значений ячеек памяти
        JLabel labelForMem1 = new JLabel("Mem1:");
        mem1Field = new JTextField("0.0", 10);
        mem1Field.setEditable(false);
        mem1Field.setMaximumSize(mem1Field.getPreferredSize());

        JLabel labelForMem2 = new JLabel("Mem2:");
        mem2Field = new JTextField("0.0", 10);
        mem2Field.setEditable(false);
        mem2Field.setMaximumSize(mem2Field.getPreferredSize());

        JLabel labelForMem3 = new JLabel("Mem3:");
        mem3Field = new JTextField("0.0", 10);
        mem3Field.setEditable(false);
        mem3Field.setMaximumSize(mem3Field.getPreferredSize());

        Box hboxMemoryDisplay = Box.createHorizontalBox();
        hboxMemoryDisplay.add(Box.createHorizontalGlue());
        hboxMemoryDisplay.add(labelForMem1);
        hboxMemoryDisplay.add(Box.createHorizontalStrut(10));
        hboxMemoryDisplay.add(mem1Field);
        hboxMemoryDisplay.add(Box.createHorizontalStrut(30));
        hboxMemoryDisplay.add(labelForMem2);
        hboxMemoryDisplay.add(Box.createHorizontalStrut(10));
        hboxMemoryDisplay.add(mem2Field);
        hboxMemoryDisplay.add(Box.createHorizontalStrut(30));
        hboxMemoryDisplay.add(labelForMem3);
        hboxMemoryDisplay.add(Box.createHorizontalStrut(10));
        hboxMemoryDisplay.add(mem3Field);
        hboxMemoryDisplay.add(Box.createHorizontalGlue());
        hboxMemoryDisplay.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        // Создать панель для выбора ячейки памяти
        Box hboxMemoryButtons = Box.createHorizontalBox();
        hboxMemoryButtons.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

        addMemoryRadioButton("Переменная 1", 1);
        addMemoryRadioButton("Переменная 2", 2);
        addMemoryRadioButton("Переменная 3", 3);
        memRadioButtons.setSelected(
                memRadioButtons.getElements().nextElement().getModel(), true);

        hboxMemoryButtons.add(Box.createHorizontalGlue());

        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxFormulaType);
        contentBox.add(hboxVariables);
        contentBox.add(hboxResult);
        contentBox.add(hboxButtons);
        contentBox.add(hboxMemoryDisplay);
        contentBox.add(hboxMemoryButtons);
        contentBox.add(imagePane);
        contentBox.add(Box.createVerticalGlue());

        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    private void updateMemoryFields() {
        mem1Field.setText(Double.toString(mem1));
        mem2Field.setText(Double.toString(mem2));
        mem3Field.setText(Double.toString(mem3));
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
