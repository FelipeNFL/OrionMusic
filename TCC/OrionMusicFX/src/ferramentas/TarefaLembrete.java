package ferramentas;

import java.time.DayOfWeek;
import java.util.ArrayList;

import org.joda.time.DateTime;

import entidades.Lembrete;
import entidades.Matricula;
import entidades.Turma;
import javafx.concurrent.Task;

public class TarefaLembrete {
	
	public TarefaLembrete() {
		Task<ArrayList<Turma>> tarefaProcuraMatriculasComLembretes = new Task<ArrayList<Turma>>() {
			
			@Override
			protected ArrayList<Turma> call() throws Exception {
				
				DateTime dataDeHoje = new DateTime();
				ArrayList<Turma> turmas = new ArrayList<>();
				
				// Sempre colocar o nome do dia na consulta para um dia depois ("amanh�") da compara��o no if
				
				if(dataDeHoje.getDayOfWeek() == DayOfWeek.FRIDAY.getValue()){ //Sexta-Feira
					turmas.addAll(Turma.consultar("diaSemana = 'S�bado'"));
				}
				else if(dataDeHoje.getDayOfWeek() == DayOfWeek.MONDAY.getValue()){ // Segunda-Feira
					turmas.addAll(Turma.consultar("diaSemana = 'Ter�a-Feira'"));
				}
				else if(dataDeHoje.getDayOfWeek() == DayOfWeek.SATURDAY.getValue()){ //S�bado
					turmas.addAll(Turma.consultar("diaSemana = 'Domingo'"));
				}
				else if(dataDeHoje.getDayOfWeek() == DayOfWeek.SUNDAY.getValue()){ //Domingo
					turmas.addAll(Turma.consultar("diaSemana = 'Segunda-Feira'"));
				}
				else if(dataDeHoje.getDayOfWeek() == DayOfWeek.THURSDAY.getValue()){ //Quinta
					turmas.addAll(Turma.consultar("diaSemana = 'Sexta-Feira'"));
				}
				else if(dataDeHoje.getDayOfWeek() == DayOfWeek.TUESDAY.getValue()){ //Ter�a
					turmas.addAll(Turma.consultar("diaSemana = 'Quarta-Feira'"));
				}
				else if(dataDeHoje.getDayOfWeek() == DayOfWeek.WEDNESDAY.getValue()){ //Quarta
					turmas.addAll(Turma.consultar("diaSemana = 'Quinta-Feira'"));
				}
				
				return turmas;
			}
			
			@Override
			protected void succeeded() {
				for(Turma turma : getValue()){
					for (Matricula matricula : turma.getMatriculas()) {
						for (Lembrete lembrete : matricula.getLembretes()) {
							if(lembrete.isArquivoMorto() == false){
								Alerta.mostrarMensagemAtencao("Voc� tem o lembrete \""+lembrete.getAnotacoesGerais()+"\" para amanh� com o aluno \""+matricula.getNomeAluno()+"\"", "Lembrete");								
							}
						}
					}						
				}
			}
		};
		
		Thread tarefaLembrete = new Thread(tarefaProcuraMatriculasComLembretes);
		tarefaLembrete.setDaemon(true);
		tarefaLembrete.start();
	}
}
