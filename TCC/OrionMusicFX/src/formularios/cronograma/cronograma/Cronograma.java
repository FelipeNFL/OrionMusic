package formularios.cronograma.cronograma;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.joda.time.DateTime;

import recursos.FolhasEstilo;
import entidades.Turma;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.LocalDateTimeRange;

public class Cronograma extends GridPane {
	private Agenda cronograma = new Agenda();
	private Map<String, Agenda.AppointmentGroup> relacaoStringGrupoCompromisso = new TreeMap<String, Agenda.AppointmentGroup>();

	public Cronograma() {
		ColumnConstraints coluna0 = new ColumnConstraints();
		coluna0.setPercentWidth(100);
		this.getColumnConstraints().add(coluna0);

		RowConstraints linha0 = new RowConstraints();
		linha0.setPercentHeight(100);
		this.getRowConstraints().add(linha0);

		this.add(cronograma, 0, 0);

		this.getStylesheets().add(FolhasEstilo.getCaminhoStyleForms());
		this.setId("principalCronograma");
		
		this.cronograma.setAllowDragging(false); //Evita que os comprimissos sejam arrastados
		this.cronograma.setAllowResize(false); //Evita que os compromissos tenham seus horários "redimensionados"
		
		this.cronograma.editAppointmentCallbackProperty().set(new Callback<Agenda.Appointment, Void>() {
			@Override
			public Void call(Appointment arg0) {
				return null;
			}
		}); // Evita que o menu de edição do compromisso seja mostrado (Ligar ele com o banco vai deixar o bagulho louco)
	}

	public void construirCronograma() {

		for (Agenda.AppointmentGroup grupoParaPercorrerLista : cronograma
				.appointmentGroups()) {
			relacaoStringGrupoCompromisso.put(
					grupoParaPercorrerLista.getDescription(),
					grupoParaPercorrerLista);
		}

		this.cronograma.newAppointmentCallbackProperty().set(
				new Callback<Agenda.LocalDateTimeRange, Agenda.Appointment>() {
					@Override
					public Appointment call(LocalDateTimeRange dateTimeRange) {
						return new Agenda.AppointmentImplLocal()
								.withStartLocalDateTime(
										dateTimeRange.getStartLocalDateTime())
								.withEndLocalDateTime(
										dateTimeRange.getEndLocalDateTime())
								.withSummary("new");
					}
				});

		List<Turma> turmas = null;

		try {
			turmas = Turma.consultar();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.cronograma.appointments().clear();
		
		DateTime domingo = null, segunda = null, terca = null, quarta = null, quinta = null, sexta = null, sabado = null;
		
		if(new DateTime().getDayOfWeek() == DayOfWeek.SUNDAY.getValue()){
			domingo = new DateTime();
			segunda = new DateTime().plusDays(1);
			terca = new DateTime().plusDays(2);
			quarta = new DateTime().plusDays(3);
			quinta = new DateTime().plusDays(4);
			sexta = new DateTime().plusDays(5);
			sabado = new DateTime().plusDays(6);
		}
		else if(new DateTime().getDayOfWeek() == DayOfWeek.MONDAY.getValue()){
			domingo = new DateTime().minusDays(1);
			segunda = new DateTime();
			terca = new DateTime().plusDays(1);
			quarta = new DateTime().plusDays(2);
			quinta = new DateTime().plusDays(3);
			sexta = new DateTime().plusDays(4);
			sabado = new DateTime().plusDays(5);
		}
		else if(new DateTime().getDayOfWeek() == DayOfWeek.TUESDAY.getValue()){
			domingo = new DateTime().minusDays(2);
			segunda = new DateTime().minusDays(1);
			terca = new DateTime();
			quarta = new DateTime().plusDays(1);
			quinta = new DateTime().plusDays(2);
			sexta = new DateTime().plusDays(3);
			sabado = new DateTime().plusDays(4);
		}
		else if(new DateTime().getDayOfWeek() == DayOfWeek.WEDNESDAY.getValue()){
			domingo = new DateTime().minusDays(3);
			segunda = new DateTime().minusDays(2);
			terca = new DateTime().minusDays(1);
			quarta = new DateTime();
			quinta = new DateTime().plusDays(1);
			sexta = new DateTime().plusDays(2);
			sabado = new DateTime().plusDays(3);
		}
		else if(new DateTime().getDayOfWeek() == DayOfWeek.THURSDAY.getValue()){
			domingo = new DateTime().minusDays(4);
			segunda = new DateTime().minusDays(3);
			terca = new DateTime().minusDays(2);
			quarta = new DateTime().minusDays(1);
			quinta = new DateTime();
			sexta = new DateTime().plusDays(1);
			sabado = new DateTime().plusDays(2);
		}
		else if(new DateTime().getDayOfWeek() == DayOfWeek.FRIDAY.getValue()){
			domingo = new DateTime().minusDays(5);
			segunda = new DateTime().minusDays(4);
			terca = new DateTime().minusDays(3);
			quarta = new DateTime().minusDays(2);
			quinta = new DateTime().minusDays(1);
			sexta = new DateTime();
			sabado = new DateTime().plusDays(1);
		}
		else if(new DateTime().getDayOfWeek() == DayOfWeek.SATURDAY.getValue()){
			domingo = new DateTime().minusDays(6);
			segunda = new DateTime().minusDays(5);
			terca = new DateTime().minusDays(4);
			quarta = new DateTime().minusDays(3);
			quinta = new DateTime().minusDays(2);
			sexta = new DateTime().minusDays(1);
			sabado = new DateTime();
		}
		
		if (turmas.isEmpty() == false) {
			for (Turma turma : turmas) {

				switch (turma.getDiaSemana()) {
				case "Segunda-Feira":
					this.adicionarCompromisso(segunda,
							Integer.parseInt(turma.getHoraInicial()),
							Integer.parseInt(turma.getMinutoInicial()),
							Integer.parseInt(turma.getHoraFinal()),
							Integer.parseInt(turma.getMinutoFinal()),
							turma.getDescricaoTurma());
					break;
				case "Terça-Feira":
					this.adicionarCompromisso(terca,
							Integer.parseInt(turma.getHoraInicial()),
							Integer.parseInt(turma.getMinutoInicial()),
							Integer.parseInt(turma.getHoraFinal()),
							Integer.parseInt(turma.getMinutoFinal()),
							turma.getDescricaoTurma());
					break;
				case "Quarta-Feira":
					this.adicionarCompromisso(quarta,
							Integer.parseInt(turma.getHoraInicial()),
							Integer.parseInt(turma.getMinutoInicial()),
							Integer.parseInt(turma.getHoraFinal()),
							Integer.parseInt(turma.getMinutoFinal()),
							turma.getDescricaoTurma());
					break;
				case "Quinta-Feira":
					this.adicionarCompromisso(quinta,
							Integer.parseInt(turma.getHoraInicial()),
							Integer.parseInt(turma.getMinutoInicial()),
							Integer.parseInt(turma.getHoraFinal()),
							Integer.parseInt(turma.getMinutoFinal()),
							turma.getDescricaoTurma());
					break;
				case "Sexta-Feira":
					this.adicionarCompromisso(sexta,
							Integer.parseInt(turma.getHoraInicial()),
							Integer.parseInt(turma.getMinutoInicial()),
							Integer.parseInt(turma.getHoraFinal()),
							Integer.parseInt(turma.getMinutoFinal()),
							turma.getDescricaoTurma());
					break;
				case "Sábado":
					this.adicionarCompromisso(sabado,
							Integer.parseInt(turma.getHoraInicial()),
							Integer.parseInt(turma.getMinutoInicial()),
							Integer.parseInt(turma.getHoraFinal()),
							Integer.parseInt(turma.getMinutoFinal()),
							turma.getDescricaoTurma());
					break;
				case "Domingo":
					this.adicionarCompromisso(domingo,
							Integer.parseInt(turma.getHoraInicial()),
							Integer.parseInt(turma.getMinutoInicial()),
							Integer.parseInt(turma.getHoraFinal()),
							Integer.parseInt(turma.getMinutoFinal()),
							turma.getDescricaoTurma());
					break;
				}
			}
		}
	}

	private void adicionarCompromisso(DateTime data, int horaInicial,
			int minutoInicial, int horaFinal, int minutoFinal, String descricao) {
		
		int corAleatoria = new Random().nextInt(24);
		String numCorFormatada = Integer.toString(corAleatoria);
		
		if(numCorFormatada.length() < 2){
			numCorFormatada = "0" + numCorFormatada;
		}
		
		this.cronograma.appointments().add(
				new Agenda.AppointmentImplLocal()
						.withStartLocalDateTime(
								LocalDateTime.of(data.getYear(),
										data.getMonthOfYear(), data.getDayOfMonth(),
										horaInicial, minutoInicial))
						.withEndLocalDateTime(
								LocalDateTime.of(data.getYear(),
										data.getMonthOfYear(), data.getDayOfMonth(),
										horaFinal, minutoFinal))
						.withSummary(descricao)
						.withAppointmentGroup(
								relacaoStringGrupoCompromisso.get("group"+numCorFormatada)));
		
		/* group01, group02, group3 ... São referentes aos grupos em que cada comprimisso se encaixa, cada grupo possui uma cor e isso será aproveitado para "embelezar" o cronograma */
	}
}