import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CatClient {

    private static final String MINECRAFT_LAUNCH_COMMAND = "java -jar MinecraftLauncher.jar";
    private static JTextField usernameField;
    private static JCheckBox offlineModeCheckbox;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CatClient::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Cat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Welcome to Cat Client", JLabel.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:", JLabel.CENTER);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(usernameField);

        offlineModeCheckbox = new JCheckBox("Offline Mode");
        offlineModeCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(offlineModeCheckbox);

        JButton launchButton = new JButton("Launch Game");
        launchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        launchButton.addActionListener(e -> launchGame());
        panel.add(launchButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static void launchGame() {
        File minecraftLauncher = new File(MINECRAFT_LAUNCH_COMMAND.split(" ")[2]);
        if (minecraftLauncher.exists()) {
            try {
                List<String> launchCommand = new ArrayList<>();
                launchCommand.add("java");
                launchCommand.add("-jar");
                launchCommand.add("MinecraftLauncher.jar");

                if (isOfflineModeEnabled() && !getUsername().isEmpty()) {
                    launchCommand.add("--username");
                    launchCommand.add(getUsername());
                }

                ProcessBuilder processBuilder = new ProcessBuilder(launchCommand);
                processBuilder.start();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error launching Minecraft: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Minecraft launcher not found. Please download it first.",
                    "Launcher Missing", JOptionPane.WARNING_MESSAGE);
            downloadMinecraftLauncher();
        }
    }

    private static String getUsername() {
        return usernameField.getText().trim();
    }

    private static boolean isOfflineModeEnabled() {
        return offlineModeCheckbox.isSelected();
    }

    private static void downloadMinecraftLauncher() {
        // URL to download the Minecraft launcher (Replace this with the actual URL)
        String launcherUrl = "https://example.com/MinecraftLauncher.jar";

        // Target file to save the launcher
        File targetFile = new File("MinecraftLauncher.jar");

        try {
            URL url = new URL(launcherUrl);
            Files.copy(url.openStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(null, "Minecraft launcher downloaded successfully.", 
                                          "Download Complete", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error downloading Minecraft launcher: " + e.getMessage(),
                    "Download Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
