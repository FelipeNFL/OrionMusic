<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> 
<title>Eventos</title>
<link rel="stylesheet" href="arquivo_css.css">
<script src="LightBox/js/jquery-1.7.2.min.js"></script>
<script src="LightBox/js/lightbox.js"></script>
<link href="LightBox/css/lightbox.css" rel="stylesheet" />
<script language="JavaScript">
	function abrir(URL) {
	
		var width = 600;
		var height = 500;
	 
		var left = 99;
		var top = 99;
		window.open(URL,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
	}
</script>
</head>
<body>
<div id="div_main" align="center">
	<div id="banner">
		<!-- Banner -->
	</div>
	<div id="fundoMenu">
		<ul id="menu">
			<li id="menu"><a href="inicio.html" class="linksMenu">In&iacute;cio</a></li>
			<li id="menu"><a href="aulas.html" class="linksMenu">Aulas</a></li>
			<li id="menu"><a href="localizacao.html" class="linksMenu">Localiza&ccedil;&atilde;o</a></li>
			<li id="menu"><a href="contato.html" class="linksMenu">Contato</a></li>
			<li id="menu"><a href="eventos.php" class="linksMenu">Eventos</a></li>
		</ul>
	</div>
	<div>
		<br>
			<!-- <a href="noyer_macadamia_d.jpg" rel="lightbox">Teste</a> -->
			<?php 
				$arquivos = scandir('GALERIA');
			
				unset($arquivos[0]); //Exclui o "." que vem por padrão quando os arquivos são listados
				unset($arquivos[1]); //Exclui o ".." que vem por padrão quando os arquivos são listados
				
				foreach ($arquivos as $arquivo) {
					
					$extensao = substr(strrchr($arquivo, "."), 1);
					
					if($extensao == "jpg" || $extensao == "JPG" || $extensao == "jpeg" || $extensao == "JPEG"){
						echo "<a href='GALERIA/".$arquivo."' rel='lightbox'><img src='GALERIA/".$arquivo."' height='150' width='150'></a>&nbsp;&nbsp;&nbsp;";					
					}
					else {
						echo '<a href="javascript: abrir(\'mostrarVideo.php?arquivo='.$arquivo.'&tipo=video&extensao='.$extensao.'\');"><video width="200" height="200" id="caixa"><source src="'.$arquivo.'" type="'.$consulta['tipo'].$consulta['extensao'].'" ></a>';
					}
				}
			?>
		<br><br>
	</div>
</div>
</body>
</html>

