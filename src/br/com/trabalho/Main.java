package br.com.trabalho;

import br.com.trabalho.view.TelaJogo;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Ponto de entrada do jogo Super Trunfo.
 */
public class Main {

    public static void main(String[] args) {
        // Usa o Look and Feel do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Mantém o L&F padrão se falhar
        }

        SwingUtilities.invokeLater(() -> {
            TelaJogo tela = new TelaJogo();
            tela.setVisible(true);
        });
    }
}
