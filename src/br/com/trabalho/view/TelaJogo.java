package br.com.trabalho.view;

import br.com.trabalho.controller.JogoController;
import br.com.trabalho.model.Carta;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaJogo extends JFrame {

    private static final Color BG = new Color(32, 36, 46);
    private static final Color BG_CARD = new Color(44, 50, 63);
    private static final Color BG_TABLE = new Color(38, 43, 55);
    private static final Color ACCENT = new Color(255, 193, 7);
    private static final Color VOCE_COR = new Color(66, 165, 245);
    private static final Color MAQ_COR = new Color(239, 83, 80);
    private static final Color WIN = new Color(76, 175, 80);
    private static final Color LOSE = new Color(239, 83, 80);
    private static final Color TXT = new Color(224, 224, 224);
    private static final Color TXT_DIM = new Color(130, 130, 140);
    private static final Color DISABLED = new Color(55, 60, 72);

    private static final Font F_TITLE = new Font("SansSerif", Font.BOLD, 24);
    private static final Font F_SUB = new Font("SansSerif", Font.BOLD, 14);
    private static final Font F_NORMAL = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font F_ICON = new Font("SansSerif", Font.BOLD, 18);
    private static final Font F_SCORE = new Font("SansSerif", Font.BOLD, 20);
    private static final Font F_CARD_NAME = new Font("SansSerif", Font.BOLD, 15);
    private static final Font F_SMALL = new Font("SansSerif", Font.PLAIN, 11);

    private JogoController controller;
    private Map<String, ImageIcon> imagensCartas = new HashMap<>();
    private Map<String, ImageIcon> miniaturas = new HashMap<>();

    private List<JButton> botoesJogador = new ArrayList<>();
    private List<JButton> botoesMaquina = new ArrayList<>();
    private JButton btnJogar;

    private JPanel painelDetalheJogador, painelDetalheMaquina;
    private JLabel[] lblAttr = new JLabel[5];
    private JLabel[] lblValJ = new JLabel[5];
    private JLabel[] lblIcoJ = new JLabel[5];
    private JLabel[] lblIcoM = new JLabel[5];
    private JLabel[] lblValM = new JLabel[5];

    private JLabel lblRodVoce, lblRodMaq, lblGeralVoce, lblGeralMaq, lblVencedor;
    private Carta cartaSelecionada;
    private String basePath;

    public TelaJogo() {
        controller = new JogoController();
        basePath = detectarBasePath();
        configurar();
        montar();
        iniciarJogo();
    }

    private String detectarBasePath() {
        String[] tentativas = { "resources/images/", "../resources/images/" };
        for (String t : tentativas) {
            if (new File(t + "I1.png").exists()) return t;
        }
        String userDir = System.getProperty("user.dir");
        File f = new File(userDir, "resources/images/I1.png");
        if (f.exists()) return userDir + "/resources/images/";
        return "resources/images/";
    }

    private void carregarImagens() {
        String[] codigos = {"I1","I2","I3","I4","I5","I6","I7","I8","I9","I10","I11"};
        for (String cod : codigos) {
            try {
                File arq = new File(basePath + cod + ".png");
                if (arq.exists()) {
                    BufferedImage img = ImageIO.read(arq);
                    imagensCartas.put(cod, new ImageIcon(img.getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
                    miniaturas.put(cod, new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
                }
            } catch (Exception e) { /* sem imagem */ }
        }
    }

    private void configurar() {
        setTitle("Super Trunfo 2026 \u2014 Instrumentos Musicais");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(960, 700);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        carregarImagens();
    }

    private void montar() {
        setLayout(new BorderLayout(0, 0));
        add(painelTopo(), BorderLayout.NORTH);
        add(painelCentral(), BorderLayout.CENTER);
        add(painelPlacar(), BorderLayout.SOUTH);
    }

    // ==================== TOPO ====================

    private JPanel painelTopo() {
        JPanel p = new JPanel(new BorderLayout(10, 0));
        p.setBackground(new Color(24, 28, 38));
        p.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel centro = new JPanel(new GridLayout(2, 1, 0, 4));
        centro.setOpaque(false);
        JLabel t = new JLabel("Super Trunfo", SwingConstants.CENTER);
        t.setFont(F_TITLE); t.setForeground(ACCENT);
        centro.add(t);

        btnJogar = botao("JOGAR", ACCENT, Color.BLACK);
        btnJogar.setEnabled(false);
        btnJogar.addActionListener(e -> executarJogada());
        JPanel pb = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pb.setOpaque(false); pb.add(btnJogar);
        centro.add(pb);

        JPanel esq = painelMiniaturas("Suas Cartas", VOCE_COR, botoesJogador, true);
        JPanel dir = painelMiniaturas("Cartas da M\u00e1quina", MAQ_COR, botoesMaquina, false);

        p.add(esq, BorderLayout.WEST);
        p.add(centro, BorderLayout.CENTER);
        p.add(dir, BorderLayout.EAST);
        return p;
    }

    private JPanel painelMiniaturas(String titulo, Color cor, List<JButton> lista, boolean ativo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(cor); lbl.setFont(F_SMALL);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(lbl); p.add(Box.createVerticalStrut(4));
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
        row.setOpaque(false);
        for (int i = 0; i < 5; i++) {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(52, 52));
            b.setFont(F_SMALL); b.setFocusPainted(false);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.setBackground(BG_CARD); b.setForeground(TXT);
            b.setBorder(BorderFactory.createLineBorder(new Color(70, 75, 90), 1));
            if (!ativo) { b.setEnabled(false); b.setBackground(DISABLED); }
            lista.add(b); row.add(b);
        }
        p.add(row);
        return p;
    }

    // ==================== CENTRO ====================

    private JPanel painelCentral() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(BG);
        p.setBorder(BorderFactory.createEmptyBorder(12, 15, 8, 15));
        GridBagConstraints g = new GridBagConstraints();
        g.gridy = 0; g.fill = GridBagConstraints.BOTH; g.weighty = 1.0;

        painelDetalheJogador = cartaPanel("Voc\u00ea", VOCE_COR);
        g.gridx = 0; g.weightx = 0.28; g.insets = new Insets(0,0,0,8);
        p.add(painelDetalheJogador, g);

        g.gridx = 1; g.weightx = 0.44; g.insets = new Insets(0,0,0,0);
        p.add(tabelaComparacao(), g);

        painelDetalheMaquina = cartaPanel("M\u00e1quina", MAQ_COR);
        g.gridx = 2; g.weightx = 0.28; g.insets = new Insets(0,8,0,0);
        p.add(painelDetalheMaquina, g);

        return p;
    }

    private JPanel cartaPanel(String titulo, Color cor) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBackground(BG_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 65, 80), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(F_SUB); lbl.setForeground(cor);
        p.add(lbl, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(BG_CARD); info.setName("info");
        p.add(info, BorderLayout.CENTER);
        return p;
    }

    private JPanel tabelaComparacao() {
        JPanel t = new JPanel(new GridBagLayout());
        t.setBackground(BG_TABLE);
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 65, 80), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 8, 7, 8); g.gridy = 0;

        g.gridx = 0; t.add(cab("Atributo"), g);
        g.gridx = 1; t.add(cab("Voc\u00ea"), g);
        g.gridx = 2; t.add(cab(""), g);
        g.gridx = 3; t.add(cab(""), g);
        g.gridx = 4; t.add(cab("M\u00e1quina"), g);

        for (int i = 0; i < 5; i++) {
            g.gridy = i + 1;
            lblAttr[i] = lbl(Carta.NOMES_ATRIBUTOS[i], F_NORMAL, TXT);
            g.gridx = 0; g.anchor = GridBagConstraints.WEST; t.add(lblAttr[i], g);

            lblValJ[i] = lbl("-", F_SUB, VOCE_COR);
            g.gridx = 1; g.anchor = GridBagConstraints.CENTER; t.add(lblValJ[i], g);

            lblIcoJ[i] = lbl(" ", F_ICON, TXT);
            g.gridx = 2; t.add(lblIcoJ[i], g);

            lblIcoM[i] = lbl(" ", F_ICON, TXT);
            g.gridx = 3; t.add(lblIcoM[i], g);

            lblValM[i] = lbl("-", F_SUB, MAQ_COR);
            g.gridx = 4; t.add(lblValM[i], g);
        }
        return t;
    }

    // ==================== PLACAR ====================

    private JPanel painelPlacar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(28, 32, 42));
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(60, 65, 80)),
            BorderFactory.createEmptyBorder(10, 20, 12, 20)));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 10, 3, 10);

        g.gridy = 0;
        g.gridx = 0; p.add(lbl("Pontua\u00e7\u00e3o da Rodada:", F_NORMAL, TXT_DIM), g);
        g.gridx = 1; p.add(lbl("Voc\u00ea", F_SUB, VOCE_COR), g);
        lblRodVoce = lbl("0", F_SCORE, VOCE_COR);
        g.gridx = 2; p.add(lblRodVoce, g);
        lblRodMaq = lbl("0", F_SCORE, MAQ_COR);
        g.gridx = 3; p.add(lblRodMaq, g);
        g.gridx = 4; p.add(lbl("M\u00e1quina", F_SUB, MAQ_COR), g);

        g.gridy = 1;
        g.gridx = 0; p.add(lbl("Pontua\u00e7\u00e3o Geral:", F_NORMAL, TXT_DIM), g);
        g.gridx = 1; p.add(lbl("Voc\u00ea", F_SUB, VOCE_COR), g);
        lblGeralVoce = lbl("0", F_SCORE, VOCE_COR);
        g.gridx = 2; p.add(lblGeralVoce, g);
        lblGeralMaq = lbl("0", F_SCORE, MAQ_COR);
        g.gridx = 3; p.add(lblGeralMaq, g);
        g.gridx = 4; p.add(lbl("M\u00e1quina", F_SUB, MAQ_COR), g);

        g.gridy = 2;
        g.gridx = 0; p.add(lbl("Vencedor:", F_SUB, ACCENT), g);
        lblVencedor = lbl("", F_SCORE, ACCENT);
        g.gridx = 1; g.gridwidth = 4; g.fill = GridBagConstraints.HORIZONTAL;
        p.add(lblVencedor, g);

        return p;
    }

    // ==================== HELPERS ====================

    /** Extrai apenas o número do código da carta (ex: "I1" -> "1", "I10" -> "10") */
    private String extrairNumero(String codigo) {
        return codigo.replaceAll("[^0-9]", "");
    }

    private JLabel lbl(String txt, Font f, Color c) {
        JLabel l = new JLabel(txt); l.setFont(f); l.setForeground(c); return l;
    }

    private JLabel cab(String txt) {
        JLabel l = new JLabel(txt, SwingConstants.CENTER);
        l.setFont(F_SUB); l.setForeground(ACCENT); return l;
    }

    private JButton botao(String txt, Color bg, Color fg) {
        JButton b = new JButton(txt);
        b.setFont(F_SUB); b.setBackground(bg); b.setForeground(fg);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(6, 20, 6, 20));
        return b;
    }

    private JPanel getInfo(JPanel card) {
        for (Component c : card.getComponents())
            if (c instanceof JPanel && "info".equals(c.getName())) return (JPanel) c;
        return null;
    }

    // ==================== LÓGICA ====================

    private void iniciarJogo() {
        try { controller.iniciarJogo(); } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao conectar ao banco.\n" + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cartaSelecionada = null;

        List<Carta> cj = controller.getJogador().getCartas();
        for (int i = 0; i < botoesJogador.size(); i++) {
            JButton b = botoesJogador.get(i);
            if (i < cj.size()) {
                Carta c = cj.get(i);
                ImageIcon mini = miniaturas.get(c.getCodigo());
                if (mini != null) { b.setIcon(mini); b.setText(""); }
                else b.setText(extrairNumero(c.getCodigo()));
                b.setToolTipText(c.getNome());
                b.setEnabled(true); b.setBackground(BG_CARD);
                b.setBorder(BorderFactory.createLineBorder(new Color(70, 75, 90), 1));
                for (var al : b.getActionListeners()) b.removeActionListener(al);
                final Carta fc = c; final JButton fb = b;
                b.addActionListener(e -> selecionar(fc, fb));
            }
        }
        List<Carta> cm = controller.getMaquina().getCartas();
        for (int i = 0; i < botoesMaquina.size(); i++) {
            JButton b = botoesMaquina.get(i);
            if (i < cm.size()) {
                b.setText(extrairNumero(cm.get(i).getCodigo()));
                b.setEnabled(false); b.setBackground(DISABLED);
            }
        }
        lblRodVoce.setText("0"); lblRodMaq.setText("0");
        lblGeralVoce.setText("0"); lblGeralMaq.setText("0");
        lblVencedor.setText("");
        limparComp(); limparCarta(painelDetalheJogador); limparCarta(painelDetalheMaquina);
        btnJogar.setEnabled(false);
    }

    private void selecionar(Carta c, JButton btn) {
        cartaSelecionada = c;
        for (JButton b : botoesJogador) {
            if (b.isEnabled()) {
                b.setBackground(BG_CARD);
                b.setBorder(BorderFactory.createLineBorder(new Color(70, 75, 90), 1));
            }
        }
        btn.setBackground(new Color(50, 70, 100));
        btn.setBorder(BorderFactory.createLineBorder(VOCE_COR, 2));
        preencherCarta(painelDetalheJogador, c);
        limparCarta(painelDetalheMaquina); limparComp();
        btnJogar.setEnabled(true);
    }

    private void executarJogada() {
        if (cartaSelecionada == null) return;
        if (!controller.jogarRodada(cartaSelecionada)) return;

        Carta cm = controller.getCartaAtualMaquina();
        boolean[] res = controller.getJogadorVenceuAtributo();
        preencherCarta(painelDetalheMaquina, cm);

        for (int i = 0; i < 5; i++) {
            lblValJ[i].setText(cartaSelecionada.getValorFormatado(i));
            lblValM[i].setText(cm.getValorFormatado(i));
            double vj = cartaSelecionada.getValorAtributo(i);
            double vm = cm.getValorAtributo(i);
            if (vj > vm) {
                lblIcoJ[i].setText("\u2714"); lblIcoJ[i].setForeground(WIN);
                lblIcoM[i].setText("\u2718"); lblIcoM[i].setForeground(LOSE);
            } else if (vm > vj) {
                lblIcoJ[i].setText("\u2718"); lblIcoJ[i].setForeground(LOSE);
                lblIcoM[i].setText("\u2714"); lblIcoM[i].setForeground(WIN);
            } else {
                lblIcoJ[i].setText("="); lblIcoJ[i].setForeground(TXT_DIM);
                lblIcoM[i].setText("="); lblIcoM[i].setForeground(TXT_DIM);
            }
        }

        lblRodVoce.setText(String.valueOf(controller.getPontosRodadaJogador()));
        lblRodMaq.setText(String.valueOf(controller.getPontosRodadaMaquina()));
        lblGeralVoce.setText(String.valueOf(controller.getJogador().getPontosGeral()));
        lblGeralMaq.setText(String.valueOf(controller.getMaquina().getPontosGeral()));

        for (JButton b : botoesJogador)
            if (b.getToolTipText() != null && b.getToolTipText().equals(cartaSelecionada.getNome()))
                { b.setEnabled(false); b.setBackground(DISABLED); b.setBorder(BorderFactory.createLineBorder(DISABLED, 1)); }
        for (JButton b : botoesMaquina)
            if (b.getText().equals(extrairNumero(cm.getCodigo())))
                b.setBackground(new Color(80, 50, 50));

        btnJogar.setEnabled(false);
        cartaSelecionada = null;

        if (controller.isJogoAcabou()) {
            lblVencedor.setText(controller.getVencedorFinal());
            int op = JOptionPane.showConfirmDialog(this,
                controller.getVencedorFinal() + "\n\nJogar novamente?",
                "Fim de Jogo", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) iniciarJogo();
        }
    }

    private void preencherCarta(JPanel card, Carta c) {
        JPanel info = getInfo(card);
        if (info == null) return;
        info.removeAll();

        // Imagem
        ImageIcon img = imagensCartas.get(c.getCodigo());
        if (img != null) {
            JLabel lblImg = new JLabel(img, SwingConstants.CENTER);
            lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);
            info.add(lblImg);
            info.add(Box.createVerticalStrut(6));
        }

        JLabel cod = new JLabel("Carta " + extrairNumero(c.getCodigo()), SwingConstants.CENTER);
        cod.setFont(new Font("Serif", Font.BOLD, 18));
        cod.setForeground(ACCENT); cod.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(cod);

        JLabel cat = new JLabel("Instrumentos Musicais", SwingConstants.CENTER);
        cat.setFont(F_SMALL); cat.setForeground(TXT_DIM);
        cat.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(cat); info.add(Box.createVerticalStrut(4));

        JLabel nome = new JLabel(c.getNome(), SwingConstants.CENTER);
        nome.setFont(F_CARD_NAME); nome.setForeground(TXT);
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(nome); info.add(Box.createVerticalStrut(8));

        for (int i = 0; i < 5; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 1));
            row.setBackground(BG_CARD);
            row.setMaximumSize(new Dimension(200, 18));
            JLabel la = new JLabel(Carta.NOMES_ATRIBUTOS[i] + ":");
            la.setFont(F_SMALL); la.setForeground(TXT_DIM);
            JLabel lv = new JLabel(c.getValorFormatado(i));
            lv.setFont(new Font("SansSerif", Font.BOLD, 11)); lv.setForeground(TXT);
            row.add(la); row.add(lv);
            info.add(row);
        }
        info.revalidate(); info.repaint();
    }

    private void limparCarta(JPanel card) {
        JPanel info = getInfo(card);
        if (info == null) return;
        info.removeAll();
        JLabel l = new JLabel("Selecione uma carta", SwingConstants.CENTER);
        l.setFont(F_NORMAL); l.setForeground(TXT_DIM);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.add(Box.createVerticalGlue()); info.add(l); info.add(Box.createVerticalGlue());
        info.revalidate(); info.repaint();
    }

    private void limparComp() {
        for (int i = 0; i < 5; i++) {
            lblValJ[i].setText("-"); lblValM[i].setText("-");
            lblIcoJ[i].setText(" "); lblIcoM[i].setText(" ");
        }
    }
}
