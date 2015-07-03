<?php
	require_once 'FILES/class.phpmailer.php';
	require_once 'FILES/class.smtp.php';
	
	$email = new PHPMailer();
	
	$email->IsSMTP();
	$email->IsHTML();
	
	$email->Host = "smtp.mx1.hostinger.com.br";
	$email->Username = "contato@orionmusic.esy.es";
	$email->Password = "142536";
	$email->Port = "2525";
	
	$email->From = $_POST['email'];
	$email->FromName = $_POST['nome'];
	
	$email->AddAddress("contato@orionmusic.esy.es");
	
	$email->Subject = "Contato";

	$body = $_POST['mensagem'] + "<br>Telefone: " + $_POST['telefone'] + "<br>Celular: " + $_POST['celular'];

	$email->MsgHTML($body);
	
	if($email->Send()){
		echo '<script>alert("Mensagem enviada com sucesso!");</script>';
		echo '<meta http-equiv="refresh" content="0;url=index.php">';
	}
	else {
		echo '<script>alert("Erro ao enviar mensagem! '.$email->ErrorInfo.'")</script>';
		echo '<script>history.back();</script>';
	}