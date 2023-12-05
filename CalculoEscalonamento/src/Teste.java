public class Teste {
    public static void main(String[] args) {
        /*
         * ------Aqui definimos nossas tarefas e os cores aos quais elas pertencem-----
         * Nome | Prioridade | Período (ms) | Deadline (ms) | Tempo de Computação (ms) | Core
         */
        String[][] tarefas = {
                {"Detecção de Presença da Planta", "1", "10", "10", "2", "1"},
                {"Detecção de Nível de Água do Reservatório", "4", "300", "300", "50", "1"},
                {"Detecção de Umidade", "2", "50", "50", "10", "1"},
                {"Detecção do Grau de Luminosidade", "3", "100", "100", "20", "1"},
                {"Recebimento dos dados (Servidor/ESP)", "3", "100", "100", "30", "2"},
                {"Envio dos dados (ESP/Servidor)", "3", "100", "100", "30", "2"}
        };

        // Número de núcleos (cores)
        int numCores = 2;

        // Número total de tarefas
        int numTarefas = tarefas.length;

        // Arrays para armazenar os períodos das tarefas
        int[] periodos = new int[numTarefas];

        for (int i = 0; i < numTarefas; i++) {
            periodos[i] = tarefas[i][2].equals("-") ? 0 : Integer.parseInt(tarefas[i][2]);
        }

        double limiteTeorico = numTarefas * (Math.pow(2, 1.0 / numTarefas) - 1);

        // Verificação de escalonabilidade para cada tarefa
        for (String[] tarefa : tarefas) {
            String nomeTarefa = tarefa[0];
            int core = Integer.parseInt(tarefa[5]);
            int periodo = tarefa[2].equals("-") ? 0 : Integer.parseInt(tarefa[2]);
            double prioridade = 1.0 / periodo; // Cálculo da prioridade inversa ao período

            double somaPrioridades = 0.0;

            // Cálculo da soma das prioridades inversas de todas as tarefas para o núcleo específico
            for (int i = 0; i < numTarefas; i++) {
                int outroPeriodo = periodos[i];
                if (i + 1 == core) {
                    somaPrioridades += 1.0 / outroPeriodo;
                }
            }

            // Verificação de escalonabilidade de acordo com Liu e Layland (Rate-Monotonic) para o núcleo específico
            if (somaPrioridades <= limiteTeorico) {
                System.out.println("Tarefa " + nomeTarefa + "\n \t é escalonável para o Core " + core +
                        " de acordo com Liu e Layland (Rate-Monotonic).");
            } else {
                System.out.println("Tarefa " + nomeTarefa + "\n \t pode não ser escalonável para o Core " + core +
                        " de acordo com Liu e Layland (Rate-Monotonic).");
            }
        }
    }
}
