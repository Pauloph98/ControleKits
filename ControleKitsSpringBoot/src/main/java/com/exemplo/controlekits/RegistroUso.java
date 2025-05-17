package com.exemplo.controlekits;

import java.time.LocalDateTime;

// Classe que registra o uso de um kit por um professor (retirada e devolução)
public class RegistroUso {
    private KitAula kit; // Kit utilizado
    private Professor professor; // Professor que utilizou
    private LocalDateTime dataHoraRetirada; // Data/hora da retirada
    private LocalDateTime dataHoraDevolucao; // Data/hora da devolução (pode ser nula)

    // Construtor: registra automaticamente a hora da retirada
    public RegistroUso(KitAula kit, Professor professor) {
        this.kit = kit;
        this.professor = professor;
        this.dataHoraRetirada = LocalDateTime.now();
    }

    // Registra a data e hora da devolução
    public void registrarDevolucao() {
        this.dataHoraDevolucao = LocalDateTime.now();
    }

    // Getter(obter o valor de um atributo) para data da devolução
    public LocalDateTime getDataHoraDevolucao() {
        return dataHoraDevolucao;
    }

    // Getter(obter o valor de um atributo) para acessar o kit vinculado ao registro
    public KitAula getKit() {
        return kit;
    }

    // Representação textual do registro, incluindo retirada e devolução (ou pendente)
    public String toString() {
        return professor + " retirou " + kit + " em " + dataHoraRetirada +
                (dataHoraDevolucao == null ? " [pendente]" : " e devolveu em " + dataHoraDevolucao);
    }
}
