package ru.kolomoets.cryptanalyzer.view;

import ru.kolomoets.cryptanalyzer.controller.MainController;

import javax.swing.*;
import java.awt.*;

public class GraphicalUI {

    // Создаём экземпляр MainController, чтобы использовать его методы
    private final MainController controller = new MainController();

    // Метод, который создаёт и показывает окно приложения
    public void createAndShow() {
        // Создаём главное окно с заголовком
        JFrame frame = new JFrame("CryptAnalyzer");
        // При закрытии окна программа полностью завершается
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Устанавливаем размер окна
        frame.setSize(600, 300);
        // Центрируем окно на экране
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        // Массив режимов работы, который будет в выпадающем списке (ComboBox)
        String[] modes = {
                "Выберите режим", "Шифрование", "Дешифрование", "Brute Force", "Статистический анализ"
        };
        // Создаём выпадающий список с режимами работы
        JComboBox<String> modeBox = new JComboBox<>(modes);
        // Поля для ввода текста: входной файл, выходной файл и ключ
        JTextField inputField = new JTextField();
        JTextField outputField = new JTextField();
        JTextField keyField = new JTextField();
        // Кнопка для запуска операции
        JButton runButton = new JButton("Запустить");

        // Настраиваем шрифты для меток и кнопок
        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // Создаём и настраиваем метку для режима
        JLabel modeLabel = new JLabel("Режим:");
        modeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        modeLabel.setFont(labelFont);
        panel.add(modeLabel);
        panel.add(modeBox);

        // Метка и поле для ввода пути входного файла
        JLabel inputLabel = new JLabel("Входной файл:");
        inputLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        inputLabel.setFont(labelFont);
        panel.add(inputLabel);
        panel.add(inputField);


        JLabel outputLabel = new JLabel("Выходной файл:");
        outputLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        outputLabel.setFont(labelFont);
        panel.add(outputLabel);
        panel.add(outputField);

        // Метка и поле для ввода ключа
        JLabel keyLabel = new JLabel("Ключ:");
        keyLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        keyLabel.setFont(labelFont);
        panel.add(keyLabel);
        panel.add(keyField);
        // Настраиваем шрифт кнопки
        runButton.setFont(buttonFont);
        panel.add(new JLabel());
        panel.add(runButton);

        // Ключ по умолчанию недоступен (только для некоторых режимов)
        keyField.setEnabled(false);  // по умолчанию неактивно

        // Добавляем слушатель на выпадающий список,
        // который включает или отключает поле ключа в зависимости от выбранного режима
        modeBox.addActionListener(e -> {
            String selected = (String) modeBox.getSelectedItem();
            if ("Шифрование".equals(selected) || "Дешифрование".equals(selected)) {
                keyField.setEnabled(true);  // включаем поле
            } else {
                keyField.setEnabled(false); // выключаем поле
                keyField.setText("");       // очищаем поле
            }
        });

        // Добавляем панель в окно
        frame.add(panel);
        // Делаем окно видимым
        frame.setVisible(true);

        // Обработчик нажатия на кнопку "Запустить"
        runButton.addActionListener(e -> {
            // Получаем выбранный режим и введённые пользователем данные
            String mode = (String) modeBox.getSelectedItem();
            String input = inputField.getText().trim();
            String output = outputField.getText().trim();
            String keyText = keyField.getText().trim();

            try {
                switch (mode) {
                    // В зависимости от выбранного режима вызываем соответствующий метод контроллера
                    case "Шифрование" -> {
                        int key = Integer.parseInt(keyText);
                        controller.encrypt(input, output, key);
                        showMessage("Успешно зашифровано!");
                    }
                    case "Дешифрование" -> {
                        int key = Integer.parseInt(keyText);
                        controller.decrypt(input, output, key);
                        showMessage("Успешно дешифровано!");
                    }
                    case "Brute Force" -> {
                        controller.bruteForce(input, output);
                        showMessage("Brute Force завершён!");
                    }
                    case "Статистический анализ" -> {
                        controller.statisticalAnalyze(input, output);
                        showMessage("Анализ завершён!");
                    }
                    default -> showMessage("Выберите режим работы.");
                }
            } catch (NumberFormatException ex) {
                showMessage("Ключ должен быть целым числом");
            } catch (Exception ex) {
                showMessage("Ошибка: " + ex.getMessage());
            }
        });
    }

    // Вспомогательный метод, который показывает сообщение в всплывающем окне
    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
}