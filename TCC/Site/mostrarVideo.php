<?php
?>
<html>
	<head>
		<title>V&iacute;deo</title>
	</head>
	<body bgcolor="#000000">
		<div align="center">
			<video width="500" height="400" controls autoplay>
				<source src="<?=$_GET['arquivo']?>" type="<?=$_GET['tipo']?><?=$_GET['extensao']?>">
			</video>
			<br>
			<a href="javascript: window.close()"><p font-color="#FFFFFF">Voltar</p></a>
		</div>
	</body>
</html>